package com.xichuan.api.files;

import com.xichuan.model.pojo.bo.NewAdminBO;
import com.xichuan.vommon.result.GraceJSONResult;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


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
}
