package com.xichuan.files.minio;

import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;

/**
 * @author : wuxiao
 * @date : 14:05 2022/3/17
 */
public class MinioClientBuilder implements MinioBuilder {


    @Override
    public MinioClient build(String var1, String var2, String var3) {
        return null;
    }

    @Override
    public MinioClient build(String var1, String var2, String var3, String var4) {
        return null;
    }

    public MinioClient build(String var1, int var2, String var3, String var4,boolean var5) throws InvalidPortException, InvalidEndpointException {
        return  new MinioClient(var1, var2, var3, var4, var5);
    }
}
