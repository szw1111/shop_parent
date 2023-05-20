package com.atguigu.controller;

import com.alibaba.fastjson.JSON;
import com.atguigu.ProductFeignClient;
import com.atguigu.entity.BaseCategoryView;
import com.atguigu.entity.ProductSalePropertyKey;
import com.atguigu.entity.SkuInfo;
import com.atguigu.result.RetVal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Controller
public class WebDetailController {
    //http://item.gmall.com/24.html
    @Autowired
    private ProductFeignClient productFeignClient;
    @GetMapping("{skuId}.html")
    public String skuDetail(@PathVariable Long skuId, Model model){
        //a.根据skuId查询商品的基本信息
        SkuInfo skuInfo = productFeignClient.getSkuInfo(skuId);
        model.addAttribute("skuInfo",skuInfo);
        //b.根据三级分类id获取商品的分类信息 select * from base_category_view a where a.category3_id =61
        Long category3Id = skuInfo.getCategory3Id();
        BaseCategoryView categoryView = productFeignClient.getCategoryView(category3Id);
        model.addAttribute("categoryView",categoryView);
        //c.根据skuId查询商品的实时价格
        BigDecimal skuPrice = productFeignClient.getSkuPrice(skuId);
        model.addAttribute("price",skuPrice);
        //d.销售属性组合id与skuId的对应关系
        Map<Object, Object> salePropertyIdAndSkuIdMapping = productFeignClient.getSalePropertyIdAndSkuIdMapping(skuInfo.getProductId());
        model.addAttribute("salePropertyValueIdJson", JSON.toJSONString(salePropertyIdAndSkuIdMapping));
        //e.获取该SKU对应的销售属性(一份)和所有的销售属性(全份)
        List<ProductSalePropertyKey> spuSalePropertyList = productFeignClient.getSpuSalePropertyAndSelected(skuInfo.getProductId(), skuId);
        model.addAttribute("spuSalePropertyList",spuSalePropertyList);
        return "detail/index";
    }


}
