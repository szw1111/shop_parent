package com.atguigu.service.impl;

import com.atguigu.entity.PlatformPropertyKey;
import com.atguigu.entity.PlatformPropertyValue;
import com.atguigu.mapper.PlatformPropertyKeyMapper;
import com.atguigu.service.PlatformPropertyKeyService;
import com.atguigu.service.PlatformPropertyValueService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 * 属性表 服务实现类
 * </p>
 *
 * @author zw
 * @since 2023-05-15
 */
@Service
public class PlatformPropertyKeyServiceImpl extends ServiceImpl<PlatformPropertyKeyMapper, PlatformPropertyKey> implements PlatformPropertyKeyService {
    @Autowired
    private PlatformPropertyValueService propertyValueService;
    @Override
    public List<PlatformPropertyKey> getPlatformPropertyByCategoryId(Long category1Id, Long category2Id, Long category3Id) {
     /*   //根据分类id查询平台属性名称
        List<PlatformPropertyKey> propertyKeyList = baseMapper.getPlatformPropertyKeyByCategoryId(category1Id,category2Id,category3Id);
        //根据平台属性名称查询平台属性值
        if(!CollectionUtils.isEmpty(propertyKeyList)){
            for (PlatformPropertyKey propertyKey : propertyKeyList) {
                LambdaQueryWrapper<PlatformPropertyValue> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(PlatformPropertyValue::getPropertyKeyId,propertyKey.getId());
                List<PlatformPropertyValue> propertyValueList = propertyValueService.list(wrapper);
                propertyKey.setPropertyValueList(propertyValueList);
            }
        }
        return propertyKeyList;*/
        return baseMapper.getPlatformPropertyByCategoryId(category1Id,category2Id,category3Id);
    }
    @Transactional //事务
    @Override
    public void savePlatformProperty(PlatformPropertyKey platformPropertyKey) {
        //判断是修改还是添加平台属性
        if (platformPropertyKey.getId()!=null){
            baseMapper.updateById(platformPropertyKey);
            //删除原有的平台属性集合
            QueryWrapper<PlatformPropertyValue> wrapper = new QueryWrapper<>();
            wrapper.eq("property_key_id",platformPropertyKey.getId());
            propertyValueService.remove(wrapper);
        }else {
            //保存平台属性的key
            this.save(platformPropertyKey);
        }
        //保存平台属性的value
        List<PlatformPropertyValue> propertyValueList = platformPropertyKey.getPropertyValueList();
        if (!CollectionUtils.isEmpty(propertyValueList)){
            for (PlatformPropertyValue propertyValue : propertyValueList) {
                propertyValue.setPropertyKeyId(platformPropertyKey.getId());
            }
            propertyValueService.saveBatch(propertyValueList);
        }
    }
}
