package com.xichuan.files.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.xichuan.files.minio.model.FilePojo;
import com.xichuan.files.resource.FileResource;
import com.xichuan.files.service.UploaderService;
import com.xichuan.files.template.MinioTemplate;
import com.xichuan.vommon.util.AliyunResource;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;

@Service
public class UploaderServiceImpl implements UploaderService {

    @Resource
    private MinioTemplate minioTemplate;

    @Autowired
    public FastFileStorageClient fastFileStorageClient;

    @Autowired
    public FileResource fileResource;
    @Autowired
    public AliyunResource aliyunResource;


    @Autowired
    public Sid sid;

    @Override
    public String uploadFdfs(MultipartFile file, String fileExtName) throws Exception {
        InputStream inputStream = file.getInputStream();


        StorePath storePath = fastFileStorageClient.uploadFile(inputStream,
                                        file.getSize(),
                                        fileExtName,
                                        null);
        inputStream.close();

        return storePath.getFullPath();
    }

    @Override
    public String uploadOSS(MultipartFile file,
                            String userId,
                            String fileExtName) throws Exception {

        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = fileResource.getEndpoint();
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = aliyunResource.getAccessKeyID();
        String accessKeySecret = aliyunResource.getAccessKeySecret();

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint,
                                                    accessKeyId,
                                                    accessKeySecret);
//        images/abc/10010/dog.png

        String fileName = sid.nextShort();
        String myObjectName = fileResource.getObjectName()
                + "/" + userId + "/" + fileName + "." + fileExtName;

        // 上传网络流。
        InputStream inputStream = file.getInputStream();
        ossClient.putObject(fileResource.getBucketName(),
                            myObjectName,
                            inputStream);

        // 关闭OSSClient。
        ossClient.shutdown();

        return myObjectName;
    }

    @Override
    public String uploadMinio(MultipartFile file) throws Exception {
        FilePojo filePojo = minioTemplate.upload(file);
        return filePojo.getUrl();
    }


}
