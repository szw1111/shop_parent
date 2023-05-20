package com.atguigu.service.impl;

import com.atguigu.constant.RedisConst;
import com.atguigu.entity.SkuImage;
import com.atguigu.entity.SkuInfo;
import com.atguigu.entity.SkuPlatformPropertyValue;
import com.atguigu.entity.SkuSalePropertyValue;
import com.atguigu.mapper.SkuInfoMapper;
import com.atguigu.service.SkuImageService;
import com.atguigu.service.SkuInfoService;
import com.atguigu.service.SkuPlatformPropertyValueService;
import com.atguigu.service.SkuSalePropertyValueService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 库存单元表 服务实现类
 * </p>
 *
 * @author zw
 * @since 2023-05-18
 */
@Service
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoMapper, SkuInfo> implements SkuInfoService {
    @Autowired
     private SkuPlatformPropertyValueService skuPlatformValueService;
    @Autowired
    private SkuImageService skuImageService;
    @Autowired
    private SkuSalePropertyValueService skuSaleValueService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public void saveSkuInfo(SkuInfo skuInfo) {
       //保存sku基本信息
        baseMapper.insert(skuInfo);
        //保存sku平台信息
        Long skuId = skuInfo.getId();
        Long productId = skuInfo.getProductId();
        List<SkuPlatformPropertyValue> skuPlatformValueList = skuInfo.getSkuPlatformPropertyValueList();
        if (!CollectionUtils.isEmpty(skuPlatformValueList)){
            for (SkuPlatformPropertyValue skuPlatformValue : skuPlatformValueList) {
                skuPlatformValue.setSkuId(skuId);
            }
            skuPlatformValueService.saveBatch(skuPlatformValueList);
        }
        //保存sku图片信息
        List<SkuImage> skuImageList = skuInfo.getSkuImageList();
        if (!CollectionUtils.isEmpty(skuImageList)){
            for (SkuImage skuImage : skuImageList) {
                skuImage.setSkuId(skuId);
            }
            skuImageService.saveBatch(skuImageList);
        }
        //保存sku销售信息
        List<SkuSalePropertyValue> skuSaleValueList = skuInfo.getSkuSalePropertyValueList();
        if (!CollectionUtils.isEmpty(skuSaleValueList)){
            for (SkuSalePropertyValue skuSaleValue : skuSaleValueList) {
                skuSaleValue.setSkuId(skuId);
                skuSaleValue.setProductId(productId);
            }
            skuSaleValueService.saveBatch(skuSaleValueList);
        }
    }

    @Override
    public SkuInfo getSkuInfo(Long skuId) {
        return getSkuInfoFromRedis(skuId);
    }
    private SkuInfo getSkuInfoFromRedis(Long skuId) {
        //sku:24:info
        String cacheKey= RedisConst.SKUKEY_PREFIX+skuId+RedisConst.SKUKEY_SUFFIX;
        //redis采用utf-8编码
        //redisTemplate.setKeySerializer(new StringRedisSerializer());
        //redisTemplate.setValueSerializer();
        SkuInfo skuInfoRedis = (SkuInfo)redisTemplate.opsForValue().get(cacheKey);
        if(skuInfoRedis==null){
            SkuInfo skuInfoDb = getSkuInfoFromDb(skuId);
            //把数据放入到redis中
            redisTemplate.opsForValue().set(cacheKey,skuInfoDb,RedisConst.SKUKEY_TIMEOUT, TimeUnit.SECONDS);
            return skuInfoDb;
        }
        return skuInfoRedis;
    }

    private SkuInfo getSkuInfoFromDb(Long skuId) {
        //1.商品的基本信息
        SkuInfo skuInfo = getById(skuId);
        //2.商品的图片信息
        if(skuInfo!=null){
            LambdaQueryWrapper<SkuImage> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SkuImage::getSkuId, skuId);
            List<SkuImage> skuImageList = skuImageService.list(wrapper);
            skuInfo.setSkuImageList(skuImageList);
        }
        return skuInfo;
    }
}
