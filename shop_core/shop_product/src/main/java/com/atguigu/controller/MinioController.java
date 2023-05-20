package com.atguigu.controller;

import com.atguigu.result.RetVal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/product")
public class MinioController {
    @PostMapping("minioUpload1")
    public RetVal minioUpload1(@RequestPart("avatar") MultipartFile avatar,
                               @RequestPart("life") MultipartFile life,
                               @RequestPart("work") MultipartFile work){
        return RetVal.ok();
    }
    @PostMapping("minioUpload2")
    public RetVal minioUpload2(@RequestPart("avatar") MultipartFile avatar,
                               @RequestPart("life") MultipartFile[] life){
        return RetVal.ok();
    }
    @PostMapping("minioUpload3")
    public RetVal minioUpload3(@RequestPart("avatar") MultipartFile avatar,
                               @RequestPart("lifeInfo") String lifeInfo){
        return RetVal.ok();
    }
    @PostMapping("minioUpload")
    public RetVal minioUpload(@RequestPart("avatar") MultipartFile[] avatar){
        return RetVal.ok();
    }
}
