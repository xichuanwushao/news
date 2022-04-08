package com.xichuan.files.minio.util;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.StreamProgress;
import io.minio.MinioClient;
import io.minio.ObjectStat;
import io.minio.PutObjectOptions;
import io.minio.Result;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import io.minio.messages.Upload;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : wangjun
 * @date : 12:14 2022/3/16
 */
public class MinioUtils {

    // 类初始化时,会立即加载该对象，线程安全,调用效率高
    private static MinioUtils instance = new MinioUtils();
    MinioClient minioClient;

    private MinioUtils() {

    }

    public static MinioUtils getInstance() {
        return instance;
    }

    /**
     * 初使化
     * @param consoleUrl   控制台URL
     * @param apiPort  api端口
     * @param username  控制台登录用户名
     * @param password  控制台登录密码
     * @param secure   是否https
     */
    public void init(MinioClient minioClient,String consoleUrl, int apiPort, String username, String password, boolean secure) {
        try {
            minioClient = new MinioClient(consoleUrl, apiPort, username, password, secure);
        } catch (InvalidEndpointException e) {
            e.printStackTrace();
        } catch (InvalidPortException e) {
            e.printStackTrace();
        }
    }

    /**
     * 检测Bucket是否存在
     * @param bucketName
     * @return
     */
    public boolean isExistBucket(MinioClient minioClient,String bucketName) {
        boolean isExist = false;
        try {
            isExist = minioClient.bucketExists(bucketName);
        } catch (Exception e) {
            isExist = false;
            e.printStackTrace();
        }
        return isExist;
    }

    /**
     * 创建Bucket
     * @param bucketName
     * @return
     */
    public boolean createBucket(MinioClient minioClient,String bucketName) {
        boolean isSuccess = false;
        try {
            if (!isExistBucket(minioClient,bucketName)) {
                minioClient.makeBucket(bucketName);
                isSuccess = true;
            } else {
                isSuccess = false;
                System.out.println(bucketName+"已存在！");
            }
        } catch (Exception e) {
            isSuccess = false;
            e.printStackTrace();
        }
        return isSuccess;
    }

    /**
     * 删除指定的Bucket
     * @param bucketName
     * @return
     */
    public boolean deleteBucket(MinioClient minioClient,String bucketName) {
        boolean isDelete = false;
        try {
            minioClient.removeBucket(bucketName);
            isDelete = true;
        } catch (Exception e) {
            isDelete = false;
            System.out.println("错误消息==="+e.getMessage());
        }
        return isDelete;
    }

    /**
     * 列出所有Bucket
     * @return
     */
    public List<Bucket> listAllBucket(MinioClient minioClient) {
        List<Bucket> list = new ArrayList<>();
        try {
            list = minioClient.listBuckets();
            for (Bucket bucket : list) {
                System.out.println("name====="+bucket.name());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取指定bucket下的对象列表
     * @param bucketName
     * @param objectPrefix
     * @param isRecursive
     * @return
     */
    public List<Item> listObject(MinioClient minioClient,String bucketName, String objectPrefix, boolean isRecursive) {
        List<Item> itemList = new ArrayList<>();
        try {
            Iterable<Result<Item>> myObjects = minioClient.listObjects(bucketName, objectPrefix, isRecursive);
            for (Result<Item> result : myObjects) {
                Item item = result.get();
                itemList.add(item);
                System.out.println(item.lastModified() + ", " + item.size() + ", " + item.objectName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemList;
    }

    /**
     * 列出bucket中被部分上传的对象
     * @param bucketName
     * @param objectPrefix
     * @param isRecursive
     * @return
     */
    public List<Upload> listIncompleteObject(MinioClient minioClient,String bucketName, String objectPrefix, boolean isRecursive) {
        List<Upload> itemList = new ArrayList<>();
        try {
            Iterable<Result<Upload>> myObjects = minioClient.listIncompleteUploads(bucketName, objectPrefix, isRecursive);
            for (Result<Upload> result : myObjects) {
                Upload item = result.get();
                itemList.add(item);
                System.out.println(item.objectName() + ", " + item.uploadId() + ", " + item.aggregatedPartSize());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemList;
    }

    /**
     * 判断指定bucket下的对象是否存在
     * @param bucketName
     * @param objectName
     * @return
     */
    public boolean isExistObject(MinioClient minioClient,String bucketName, String objectName) {
        boolean isExist = false;
        try {
            ObjectStat response = statObject(minioClient,bucketName,objectName);
            isExist = response!=null ? true : false;
        } catch (Exception e) {
            isExist = false;
            e.printStackTrace();
        }
        return isExist;
    }

    /**
     * 查看指定bucket下的对象状态
     * @param bucketName
     * @param objectName
     * @return
     */
    public ObjectStat statObject(MinioClient minioClient,String bucketName, String objectName) {
        try {
            ObjectStat objectStat = minioClient.statObject(bucketName, objectName);
            return objectStat;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取指定对象的外链URL
     * 注意：必须先确认对象存在后，才能获取对象的外链URL
     * @param bucketName
     * @param objectName
     * @param duration
     * @return
     */
    public String getPresignedObjectUrl(MinioClient minioClient,String bucketName, String objectName, int duration) {
        String presignedObjectUrl = "";
        try {
            presignedObjectUrl = minioClient.getPresignedObjectUrl(Method.GET, bucketName, objectName, duration, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return presignedObjectUrl;
    }

    /**
     * 删除指定bucket下的对象
     * @param bucketName
     * @param objectName
     * @return
     */
    public boolean deleteObject(MinioClient minioClient,String bucketName, String objectName) {
        boolean isSuccess = false;
        try {
            minioClient.removeObject(bucketName, objectName);
            isSuccess = true;
        } catch (Exception e) {
            isSuccess = false;
            e.printStackTrace();
        }
        return isSuccess;
    }

    public boolean deleteIncomplateObject(MinioClient minioClient,String bucketName, String objectName) {
        boolean isSuccess = false;
        try {
            minioClient.removeIncompleteUpload(bucketName, objectName);
            isSuccess = true;
        } catch (Exception e) {
            isSuccess = false;
            e.printStackTrace();
        }
        return isSuccess;
    }

    /**
     * 以文件的方式上传到指定的bucket
     * @param bucketName
     * @param objectName
     * @param filePath
     * @return
     */
    public boolean uploadFile(MinioClient minioClient,String bucketName, String objectName, String filePath) {
        boolean isSuccess = false;
        try {
            minioClient.putObject(bucketName, objectName, filePath, null);
            isSuccess = true;
        } catch (Exception e) {
            isSuccess = false;
            e.printStackTrace();
        }
        return isSuccess;
    }

    /**
     * 以流的方式上传文件到指定的bucket
     * @param bucketName
     * @param objectName
     * @param inputStream
     * @param totalLength
     * @param partLength
     * @return
     */
    public boolean uploadFile(MinioClient minioClient,String bucketName, String objectName, InputStream inputStream, long totalLength, long partLength) {
        boolean isSuccess = false;
        try {
            PutObjectOptions options = new PutObjectOptions(totalLength, partLength);
            minioClient.putObject(bucketName, objectName, inputStream, options);
            isSuccess = true;
        } catch (Exception e) {
            isSuccess = false;
            e.printStackTrace();
        }
        return isSuccess;
    }
    /**
     * 以流的方式上传文件到指定的bucket
     * @param bucketName
     * @param objectName
     * @param inputStream
     * @param totalLength
     * @param partLength
     * @return
     */
    public boolean uploadFile(MinioClient minioClient,String bucketName, String objectName, InputStream inputStream, long totalLength, long partLength, String contentType) {
        boolean isSuccess = false;
        try {
            PutObjectOptions options = new PutObjectOptions(totalLength, partLength);
            options.setContentType(contentType);
            minioClient.putObject(bucketName, objectName, inputStream, options);
            isSuccess = true;
        } catch (Exception e) {
            isSuccess = false;
            e.printStackTrace();
        }
        return isSuccess;
    }
    /**
     * 从指定的位置下载文件到本地
     * @param bucketName
     * @param objectName
     * @param localPath
     */
    public boolean downloadObject(MinioClient minioClient,String bucketName, String objectName, String localPath) {
        boolean isSuccess = false;
        try {
            minioClient.getObject(bucketName, objectName, localPath);
            isSuccess = true;
        } catch (Exception e) {
            isSuccess = false;
            e.printStackTrace();
        }
        return isSuccess;
    }

    /**
     * 以流的方式从指定位置下载文件
     * @param bucketName
     * @param objectName
     * @return
     */
    public InputStream downloadObject(MinioClient minioClient,String bucketName, String objectName) {
        try {
            return minioClient.getObject(bucketName, objectName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void downloadObject(MinioClient minioClient,String bucketName, String objectName, OutputStream outputStream) {
        try {
            InputStream inputStream = minioClient.getObject(bucketName, objectName);
            IoUtil.copy(inputStream, outputStream);
            IoUtil.close((AutoCloseable) () -> inputStream.close());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void downloadObject(MinioClient minioClient,String bucketName, String objectName, OutputStream outputStream, int bufferSize, StreamProgress streamProgress) {
        try {
            InputStream inputStream = minioClient.getObject(bucketName, objectName);
            IoUtil.copy(inputStream, outputStream, bufferSize, streamProgress);
            IoUtil.close((AutoCloseable) () -> inputStream.close());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
