package com.xichuan.files.minio.model;

import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 文件资源表
 * </p>
 *
 * @author wuxiao
 * @since 2022-03-14
 */
@Data
public class FilePojo {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 资源路径
     */
    private String url;

    /**
     * 资源原始名称
     */
    private String name;

    /**
     * 资源名称
     */
    private String fileName;

    /**
     * 后缀名
     */
    private String suffix;

    /**
     * 是否图片
     */
    private Boolean isImg;

    /**
     * 尺寸
     */
    private Long size;

    /**
     * 文件展示类型，非后缀名
     */
    private String type;

    /**
     * 上传时间
     */
    private Date putTime;

    /**
     * 是否目录
     */
    private Boolean isDir;

    private Long parentId;

    /**
     * 来源
     */
    private String source;

    /**
     * 重命名的名称值
     */
    private String rename;

    /**
     * 目录id拼接
     */
    private String dirIds;
}
