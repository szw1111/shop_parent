package com.atguigu.controller;


import com.atguigu.entity.BaseCategory1;
import com.atguigu.entity.BaseCategory2;
import com.atguigu.entity.BaseCategory3;
import com.atguigu.result.RetVal;
import com.atguigu.service.BaseCategory1Service;
import com.atguigu.service.BaseCategory2Service;
import com.atguigu.service.BaseCategory3Service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 一级分类表 前端控制器
 * </p>
 *
 * @author zw
 * @since 2023-05-15
 */
@RestController
//@CrossOrigin
@RequestMapping("/product")
@Api(tags = "商品分类层级相关接口")
public class CategoryController {
     @Autowired
    private BaseCategory1Service baseCategory1Service;
    @Autowired
    private BaseCategory2Service baseCategory2Service;
    @Autowired
    private BaseCategory3Service baseCategory3Service;
     //查看商品一级分类http://127.0.0.1:8000/product/getCategory1
    @ApiOperation("一级分类")
    @GetMapping("getCategory1")
    public RetVal getCategory1(){
        List<BaseCategory1> category1List = baseCategory1Service.list(null);
        return RetVal.ok(category1List);
    }

    //查看商品二级分类http://127.0.0.1:8000/product/getCategory2/category1Id
    @ApiOperation("二级分类")
    @GetMapping("/getCategory2/{category1Id}")
    public RetVal getCategory2(@PathVariable Long category1Id){
        LambdaQueryWrapper<BaseCategory2> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BaseCategory2::getCategory1Id,category1Id);
        List<BaseCategory2> category2List = baseCategory2Service.list(wrapper);
        return RetVal.ok(category2List);
    }

    //查看商品三级分类http://127.0.0.1:8000/product/getCategory3/category2Id
    @ApiOperation("三级分类")
    @GetMapping("/getCategory3/{category2Id}")
    public RetVal getCategory3(@PathVariable Long category2Id){
        LambdaQueryWrapper<BaseCategory3> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BaseCategory3::getCategory2Id,category2Id);
        List<BaseCategory3> category3List = baseCategory3Service.list(wrapper);
        return RetVal.ok(category3List);
    }
}

