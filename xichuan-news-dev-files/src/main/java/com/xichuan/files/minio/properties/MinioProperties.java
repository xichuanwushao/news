package com.xichuan.files.minio.properties;

import lombok.Getter;
import lombok.Setter;

/**
 * @author : wangjun
 * @date : 20:29 2022/3/11
 */
@Getter
@Setter
public class MinioProperties {
    //用户名
    private String accessKey;
    //密码
    private String secretKey;
    //控制台地址
    private String consoleUrl;
    //api端口
    private int apiPort;
    //默认bucket
    private String defaultBucket;

    private String bucketName;

    private String objectName;

    private String fileUrl;


}
