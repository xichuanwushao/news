package com.xichuan.api.files;

import com.xichuan.model.pojo.bo.NewAdminBO;
import com.xichuan.vommon.result.GraceJSONResult;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Api(value = "文件上传的Controller", tags = {"文件上传的Controller"})
@RequestMapping("fs")
public interface FileUploaderControllerApi {

    /**
     * 上传单文件
     * @param userId
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/uploadFace")
    public GraceJSONResult uploadFace(@RequestParam String userId,
                                      MultipartFile file) throws Exception;
    /**
     * 文件上传到mongodb的gridfs中
     * @param newAdminBO
     * @return
     * @throws Exception
     */
    @PostMapping("/uploadToGridFS")
    public GraceJSONResult uploadToGridFS(@RequestBody NewAdminBO newAdminBO)
            throws Exception;

    /**
     * 从gridfs中读取图片内容
     * @param faceId
     * @return
     * @throws Exception
     */
    @GetMapping("/readInGridFS")
    public void readInGridFS(String faceId,
                             HttpServletRequest request,
                             HttpServletResponse response)
            throws Exception;


}