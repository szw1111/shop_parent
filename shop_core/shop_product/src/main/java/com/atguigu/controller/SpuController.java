package com.atguigu.controller;


import com.atguigu.entity.BaseSaleProperty;
import com.atguigu.entity.ProductSpu;
import com.atguigu.result.RetVal;
import com.atguigu.service.BaseSalePropertyService;
import com.atguigu.service.ProductSpuService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 商品表 前端控制器
 * </p>
 *
 * @author zw
 * @since 2023-05-18
 */
@RestController
@RequestMapping("/product")
@Api(tags = "spu相关接口")
public class SpuController {
    @Autowired
    private ProductSpuService spuService;
    @Autowired
    private BaseSalePropertyService salePropertyService;
    //根据分类id查询spu列表http://127.0.0.1/product/queryProductSpuByPage/1/10/61
    @ApiOperation("根据分类id查询spu列表")
    @GetMapping("queryProductSpuByPage/{pageNum}/{pageSize}/{category3Id}")
    public RetVal queryProductSpuByPage(@PathVariable Long pageNum,
                                        @PathVariable Long pageSize,
                                        @PathVariable Long category3Id){
        IPage<ProductSpu> page = new Page<>(pageNum,pageSize);
        LambdaQueryWrapper<ProductSpu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProductSpu::getCategory3Id,category3Id);
        spuService.page(page,wrapper);
        return RetVal.ok(page);
    }
    //http://127.0.0.1/product/queryAllSaleProperty
    @ApiOperation("查询所有销售属性")
    @GetMapping("queryAllSaleProperty")
    public RetVal queryAllSaleProperty(){
        List<BaseSaleProperty> propertyList = salePropertyService.list(null);
        return RetVal.ok(propertyList);
    }
    //http://127.0.0.1/product/saveProductSpu
    @ApiOperation("添加产品spu")
    @PostMapping("saveProductSpu")
    public RetVal saveProductSpu(@RequestBody ProductSpu productSpu){
        spuService.saveProductSpu(productSpu);
        return RetVal.ok();
    }

}

