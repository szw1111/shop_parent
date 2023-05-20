package com.atguigu.controller;


import com.atguigu.entity.BaseBrand;
import com.atguigu.minio.MinioUploader;
import com.atguigu.result.RetVal;
import com.atguigu.service.BaseBrandService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FilenameUtils;
import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 品牌表 前端控制器
 * </p>
 *
 * @author zw
 * @since 2023-05-17
 */
@RestController
@RequestMapping("/product/brand")
@Api(tags = "品牌相关接口")
public class BrandController {
    @Autowired
    private BaseBrandService baseBrandService;
    @Autowired
    private MinioUploader minioUploader;
    //分页显示品牌信息http://127.0.0.1/product/brand/queryBrandByPage/1/10
    @ApiOperation("分页显示品牌信息")
    @GetMapping("queryBrandByPage/{pageNumber}/{pageSize}")
    public RetVal queryBrandByPage(@PathVariable Long pageNumber,@PathVariable Long pageSize){
        IPage<BaseBrand> page =  new Page<>(pageNumber,pageSize);
        baseBrandService.page(page,null);
        return RetVal.ok(page);
    }
    //http://127.0.0.1/product/brand
    @ApiOperation("增加品牌")
    @PostMapping
    public RetVal saveBrand(@RequestBody BaseBrand baseBrand){
        baseBrandService.save(baseBrand);
        return RetVal.ok();
    }
    //http://127.0.0.1/product/brand/4
    @ApiOperation("删除品牌")
    @DeleteMapping("{brandId}")
    public RetVal removeBrand(@PathVariable Long brandId){
        baseBrandService.removeById(brandId);
        return RetVal.ok();
    }
    //http://127.0.0.1/product/brand/4
    @ApiOperation("根据id查询品牌信息")
    @GetMapping("{brandId}")
    public RetVal getById(@PathVariable Long brandId){
        BaseBrand baseBrand = baseBrandService.getById(brandId);
        return RetVal.ok(baseBrand);
    }
    //http://127.0.0.1/product/brand
    @ApiOperation("更新品牌信息")
    @PutMapping
    public RetVal update(@RequestBody BaseBrand baseBrand){
        baseBrandService.updateById(baseBrand);
        return RetVal.ok();
    }
    @ApiOperation("查询所有的品牌")
    @GetMapping("getAllBrand")
    public RetVal getAllBrand() {
        List<BaseBrand> brandList = baseBrandService.list(null);
        return RetVal.ok(brandList);
    }

    //文件上传http://api.gmall.com/product/brand/fileUpload
    @PostMapping("fileUpload1")
    public RetVal fileUpload1(MultipartFile file) throws Exception {
        //需要一个配置文件告诉fastdfs在哪里
        String configFilePath = this.getClass().getResource("/tracker.conf").getFile();
        //初始化
        ClientGlobal.init(configFilePath);
        //创建trackerClient客户端
        TrackerClient trackerClient = new TrackerClient();
        //用trackerClient获取连接
        TrackerServer trackerServer = trackerClient.getConnection();
        //创建storageClient
        StorageClient1 storageClient1 = new StorageClient1(trackerServer,null);
        //对文件进行上传
        String originalFilename = file.getOriginalFilename();
        String extension = FilenameUtils.getExtension(originalFilename);
        String path = storageClient1.upload_appender_file1(file.getBytes(),extension,null);
        //http://192.168.175.110:8888/group1/M00/00/01/wKivbmRhbquEepg-AAAAALL02XI516.jpg
        String url = "192.168.175.110:8888/" + path;
        return RetVal.ok(url);
    }
    @ApiOperation("文件上传")
    @PostMapping("fileUpload")
    public RetVal fileUpload(MultipartFile file) throws Exception {
        String retUrl = minioUploader.uploadFile(file);
        return RetVal.ok(retUrl);
    }
}

