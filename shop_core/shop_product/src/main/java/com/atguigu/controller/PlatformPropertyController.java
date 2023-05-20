package com.atguigu.controller;


import com.atguigu.entity.PlatformPropertyKey;
import com.atguigu.entity.PlatformPropertyValue;
import com.atguigu.result.RetVal;
import com.atguigu.service.PlatformPropertyKeyService;
import com.atguigu.service.PlatformPropertyValueService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 属性表 前端控制器
 * </p>
 *
 * @author zw
 * @since 2023-05-15
 */
@RestController
@RequestMapping("/product")
@Api(tags = "平台属性相关接口")
//@CrossOrigin
public class PlatformPropertyController {
    @Autowired
    private PlatformPropertyKeyService propertyKeyService;
    @Autowired
    private PlatformPropertyValueService propertyValueService;

      //根据分类id查询平台属性 http://127.0.0.1:8000/product/getPlatformPropertyByCategoryId/2/13/0
    @ApiOperation("根据分类id查询平台属性")
    @GetMapping("getPlatformPropertyByCategoryId/{category1Id}/{category2Id}/{category3Id}")
    public RetVal getPlatformPropertyByCategoryId(@PathVariable Long category1Id,
                                                  @PathVariable Long category2Id,
                                                  @PathVariable Long category3Id){
      List<PlatformPropertyKey> propertyKeyList =  propertyKeyService.getPlatformPropertyByCategoryId(category1Id,category2Id,category3Id);
      return RetVal.ok(propertyKeyList);
    }

    //根据属性keyId查询属性值http://127.0.0.1:8000/product/getPropertyValueByPropertyKeyId/4
    @ApiOperation("根据属性keyId查询属性值")
    @GetMapping("getPropertyValueByPropertyKeyId/{propertyKeyId}")
    public RetVal getPropertyValueByPropertyKeyId(@PathVariable Long propertyKeyId){
        LambdaQueryWrapper<PlatformPropertyValue> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PlatformPropertyValue::getPropertyKeyId,propertyKeyId);
        List<PlatformPropertyValue> propertyValueList = propertyValueService.list(wrapper);
        return RetVal.ok(propertyValueList);
    }

    //保存或修改平台属性http://127.0.0.1:8000/product/savePlatformProperty
    @ApiOperation("保存或修改平台属性")
    @PostMapping("savePlatformProperty")
    public RetVal savePlatformProperty(@RequestBody PlatformPropertyKey platformPropertyKey){
           propertyKeyService.savePlatformProperty(platformPropertyKey);
           return RetVal.ok();
    }


}

