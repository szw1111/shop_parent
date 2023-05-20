package com.atguigu.service.impl;

import com.atguigu.entity.ProductImage;
import com.atguigu.entity.ProductSalePropertyKey;
import com.atguigu.entity.ProductSalePropertyValue;
import com.atguigu.entity.ProductSpu;
import com.atguigu.mapper.ProductSpuMapper;
import com.atguigu.service.ProductImageService;
import com.atguigu.service.ProductSalePropertyKeyService;
import com.atguigu.service.ProductSalePropertyValueService;
import com.atguigu.service.ProductSpuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author zw
 * @since 2023-05-18
 */
@Service
public class ProductSpuServiceImpl extends ServiceImpl<ProductSpuMapper, ProductSpu> implements ProductSpuService {
    @Autowired
   private ProductImageService imageService;
    @Autowired
    private ProductSalePropertyKeyService propertyKeyService;
    @Autowired
    private ProductSalePropertyValueService propertyValueService;
    @Override
    public void saveProductSpu(ProductSpu productSpu) {
        //添加spu基本信息
        baseMapper.insert(productSpu);
        //添加spu图片信息
        //获取spuId
        Long spuId = productSpu.getId();
        List<ProductImage> imageList = productSpu.getProductImageList();
        if (!CollectionUtils.isEmpty(imageList)){
            for (ProductImage image : imageList) {
                image.setProductId(spuId);
            }
            imageService.saveBatch(imageList);
        }
        //添加销售属性key
        List<ProductSalePropertyKey> propertyKeyList = productSpu.getSalePropertyKeyList();
        if (!CollectionUtils.isEmpty(propertyKeyList)){
            for (ProductSalePropertyKey propertyKey : propertyKeyList) {
                propertyKey.setProductId(spuId);
                //添加销售属性value
                List<ProductSalePropertyValue> propertyValueList = propertyKey.getSalePropertyValueList();
                if (!CollectionUtils.isEmpty(propertyKeyList)){
                    for (ProductSalePropertyValue propertyValue : propertyValueList) {
                        propertyValue.setProductId(spuId);
                        propertyValue.setSalePropertyKeyName(propertyKey.getSalePropertyKeyName());
                    }
                    propertyValueService.saveBatch(propertyValueList);
                }
            }
            propertyKeyService.saveBatch(propertyKeyList);
        }
    }
}
