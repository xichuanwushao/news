package com.xichuan.files.minio;

import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;

/**
 * @author : wuxiao
 * @date : 14:08 2022/3/17
 */
public interface MinioBuilder {

    MinioClient build(String var1, String var2, String var3);

    MinioClient build(String var1, String var2, String var3, String var4);

    public MinioClient build(String var1, int var2, String var3, String var4,boolean var5) throws InvalidPortException, InvalidEndpointException;
}
