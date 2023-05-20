package com.atguigu.minio;

import io.minio.MinioClient;
import io.minio.PutObjectOptions;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

//开启minio配置类支持
@EnableConfigurationProperties(MinioProperties.class)
@Component
public class MinioUploader {
    @Autowired
   private MinioProperties minioProperties;
    @Autowired
    private MinioClient minioClient;
    @Bean
    public MinioClient minioClient() throws Exception {
        MinioClient minioClient = new MinioClient(minioProperties.getEndpoint(), minioProperties.getAccessKey(), minioProperties.getSecretKey());
        boolean bucketExists = minioClient.bucketExists(minioProperties.getBucketName());
        if (bucketExists){
            System.out.println("the bucket is already exists");
        }else {
            minioClient.makeBucket(minioProperties.getBucketName());
        }
        return minioClient;
    }
    public String uploadFile(MultipartFile file) throws Exception {
         String prefix = UUID.randomUUID().toString().replace("-","");
         String originalFilename = file.getOriginalFilename();
         String suffix = FilenameUtils.getExtension(originalFilename);
         String fileName = prefix + "." + suffix;
        InputStream inputStream = file.getInputStream();
        //文件可用大小,上传多少文件内容,-1全部上传
        PutObjectOptions options = new PutObjectOptions(inputStream.available(), -1);
        //使用putObject上传文件到存储桶中
        //参数String bucketName, String objectName, InputStream stream, PutObjectOptions options
        minioClient.putObject(minioProperties.getBucketName(),fileName,inputStream,options);
        String retUrl = minioProperties.getEndpoint() + "/" + minioProperties.getBucketName() + "/" + fileName;
        System.out.println("上传成功");
        return retUrl;
    }
}
