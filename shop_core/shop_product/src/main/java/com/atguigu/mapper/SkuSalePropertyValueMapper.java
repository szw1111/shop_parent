package com.atguigu.mapper;

import com.atguigu.entity.SkuSalePropertyValue;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * sku销售属性值 Mapper 接口
 * </p>
 *
 * @author zw
 * @since 2023-05-18
 */
public interface SkuSalePropertyValueMapper extends BaseMapper<SkuSalePropertyValue> {

    List<Map> getSalePropertyIdAndSkuIdMapping(Long productId);
}
