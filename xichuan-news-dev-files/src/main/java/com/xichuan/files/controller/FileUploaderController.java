package com.xichuan.files.controller;

import com.xichuan.files.resource.FileResource;
import com.xichuan.files.service.UploaderService;
import com.xichuan.api.files.FileUploaderControllerApi;
import com.xichuan.vommon.result.GraceJSONResult;
import com.xichuan.vommon.result.ResponseStatusEnum;
import com.xichuan.vommon.util.extend.AliImageReviewUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
public class FileUploaderController implements FileUploaderControllerApi {

    final static Logger logger = LoggerFactory.getLogger(FileUploaderController.class);

    @Autowired
    private UploaderService uploaderService;

    @Autowired
    private FileResource fileResource;

    @Autowired
    private AliImageReviewUtils aliImageReviewUtils;


    @Override
    public GraceJSONResult uploadFace(String userId,
                                      MultipartFile file) throws Exception {

        String path = "";
        if (file != null) {
            // 获得文件上传的名称
            String fileName = file.getOriginalFilename();

            // 判断文件名不能为空
            if (StringUtils.isNotBlank(fileName)) {
                String fileNameArr[] = fileName.split("\\.");
                // 获得后缀
                String suffix = fileNameArr[fileNameArr.length - 1];
                // 判断后缀符合我们的预定义规范
                if (!suffix.equalsIgnoreCase("png") &&
                        !suffix.equalsIgnoreCase("jpg") &&
                        !suffix.equalsIgnoreCase("jpeg")
                ) {
                    return GraceJSONResult.errorCustom(ResponseStatusEnum.FILE_FORMATTER_FAILD);
                }

                // 执行上传
                  //path = uploaderService.uploadFdfs(file, suffix);
//                path = uploaderService.uploadOSS(file, userId, suffix);
                path = uploaderService.uploadMinio(file);

            } else {
                return GraceJSONResult.errorCustom(ResponseStatusEnum.FILE_UPLOAD_NULL_ERROR);
            }
        } else {
            return GraceJSONResult.errorCustom(ResponseStatusEnum.FILE_UPLOAD_NULL_ERROR);
        }

        logger.info("path = " + path);

        String finalPath = "";
        if (StringUtils.isNotBlank(path)) {
            finalPath = fileResource.getHost() + path;
//            finalPath = fileResource.getOssHost() + path;
        } else {
            return GraceJSONResult.errorCustom(ResponseStatusEnum.FILE_UPLOAD_FAILD);
        }

//        return GraceJSONResult.ok(doAliImageReview(finalPath));
        return GraceJSONResult.ok(finalPath);
    }



    public static final String FAILED_IMAGE_URL = "https://imooc-news.oss-cn-shanghai.aliyuncs.com/image/faild.jpeg";
    private String doAliImageReview(String pendingImageUrl) {

        /**
         * fastdfs 默认存在于内网，无法被阿里云内容管理服务检查到
         * 需要配置到公网才行：
         * 1. 内网穿透，natppp/花生壳/ngrok
         * 2. 路由配置端口映射
         * 3. fdfs 发布到云服务器
         */
        boolean result = false;
        try {
            result = aliImageReviewUtils.reviewImage(pendingImageUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!result) {
            return FAILED_IMAGE_URL;
        }

        return pendingImageUrl;
    }





}
