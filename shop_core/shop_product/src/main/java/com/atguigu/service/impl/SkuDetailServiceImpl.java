package com.atguigu.service.impl;

import com.atguigu.mapper.SkuSalePropertyValueMapper;
import com.atguigu.service.SkuDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SkuDetailServiceImpl implements SkuDetailService {
    @Autowired
    private SkuSalePropertyValueMapper salePropertyValueMapper;
    @Override
    public Map<Object, Object> getSalePropertyIdAndSkuIdMapping(Long productId) {
        Map<Object, Object> salePropertyRetMap = new HashMap<>();
        List<Map> retMapList =  salePropertyValueMapper.getSalePropertyIdAndSkuIdMapping(productId);
        for (Map retMap : retMapList) {
            salePropertyRetMap.put(retMap.get("sale_property_value_id"),retMap.get("sku_id"));
        }
        return salePropertyRetMap;
    }
}
