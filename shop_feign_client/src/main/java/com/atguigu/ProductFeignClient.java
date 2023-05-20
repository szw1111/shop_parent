package com.atguigu;

import com.atguigu.entity.BaseCategoryView;
import com.atguigu.entity.ProductSalePropertyKey;
import com.atguigu.entity.SkuInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@FeignClient(value = "shop-product")
public interface ProductFeignClient {
    @GetMapping("sku/getSkuInfo/{skuId}")
    SkuInfo getSkuInfo(@PathVariable(value = "skuId") Long skuId);

    @GetMapping("sku/getCategoryView/{category3Id}")
    BaseCategoryView getCategoryView(@PathVariable(value = "category3Id") Long category3Id);

    @GetMapping("sku/getSkuPrice/{skuId}")
    BigDecimal getSkuPrice(@PathVariable(value = "skuId") Long skuId);

    @GetMapping("sku/getSalePropertyIdAndSkuIdMapping/{productId}")
    Map<Object, Object> getSalePropertyIdAndSkuIdMapping(@PathVariable(value = "productId") Long productId);

    @GetMapping("sku/getSpuSalePropertyAndSelected/{productId}/{skuId}")
    List<ProductSalePropertyKey> getSpuSalePropertyAndSelected(@PathVariable(value = "productId") Long productId,
                                                               @PathVariable(value = "skuId") Long skuId);
}