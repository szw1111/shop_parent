package com.atguigu.controller;


import com.atguigu.entity.ProductImage;
import com.atguigu.entity.ProductSalePropertyKey;
import com.atguigu.entity.SkuInfo;
import com.atguigu.mapper.ProductSalePropertyKeyMapper;
import com.atguigu.result.RetVal;
import com.atguigu.service.ProductImageService;
import com.atguigu.service.SkuInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 库存单元表 前端控制器
 * </p>
 *
 * @author zw
 * @since 2023-05-18
 */
@Api(tags = "sku相关接口")
@RestController
@RequestMapping("/product")
public class SkuController {
    //http://127.0.0.1/product/querySalePropertyByProductId/16
    @Autowired
    private ProductSalePropertyKeyMapper propertyKeyMapper;
    @Autowired
    private ProductImageService imageService;
    @Autowired
    private SkuInfoService skuInfoService;
    @ApiOperation("根据productId查询销售属性")
    @GetMapping("querySalePropertyByProductId/{spuId}")
    public RetVal querySalePropertyByProductId(@PathVariable Long spuId){
        List<ProductSalePropertyKey> propertyKeyList = propertyKeyMapper.querySalePropertyByProductId(spuId);
        return RetVal.ok(propertyKeyList);
    }
    //http://127.0.0.1/product/queryProductImageByProductId/12
    @ApiOperation("根据productId查询产品图片")
    @GetMapping("queryProductImageByProductId/{spuId}")
    public RetVal queryProductImageByProductId(@PathVariable Long spuId){
        LambdaQueryWrapper<ProductImage> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProductImage::getProductId,spuId);
        List<ProductImage> imageList = imageService.list(wrapper);
        return RetVal.ok(imageList);
    }
    //http://127.0.0.1/product/saveSkuInfo
    @ApiOperation("保存sku信息")
    @PostMapping("saveSkuInfo")
    public RetVal saveSkuInfo(@RequestBody SkuInfo skuInfo){
         skuInfoService.saveSkuInfo(skuInfo);
         return RetVal.ok();
    }
    //http://127.0.0.1/product/querySkuInfoByPage/1/10
    @ApiOperation("分页查询sku信息")
    @GetMapping("querySkuInfoByPage/{pageNum}/{pageSize}")
    public RetVal querySkuInfoByPage(@PathVariable Long pageNum,@PathVariable Long pageSize){
        IPage<SkuInfo> page = new Page<>(pageNum,pageSize);
        skuInfoService.page(page,null);
        return RetVal.ok(page);
    }
    //下架http://127.0.0.1/product/offSale/24
    @ApiOperation("商品下架")
    @GetMapping("offSale/{skuId}")
    public RetVal offSale(@PathVariable Long skuId){
        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setId(skuId);
        skuInfo.setIsSale(0);
        skuInfoService.updateById(skuInfo);
        return RetVal.ok();
    }

    @ApiOperation("商品上架")
    @GetMapping("onSale/{skuId}")
    public RetVal onSale(@PathVariable Long skuId){
        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setId(skuId);
        skuInfo.setIsSale(1);
        skuInfoService.updateById(skuInfo);
        return RetVal.ok();
    }
}

