package com.xichuan.files.minio.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author : wuxiao
 * @date : 20:26 2022/3/11
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "fs.files-server")
public class FsServerProperties {
    /**
     * 自动化配置
     * type：oss or local or minio
     */
    private String type = "minio";
    /**
     * minio配置
     */
    MinioProperties minio = new MinioProperties();


}
