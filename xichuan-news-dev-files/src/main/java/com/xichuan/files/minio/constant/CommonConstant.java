package com.xichuan.files.minio.constant;

/**
 * @author : wuxiao
 * @date : 11:11 2022/3/14
 */
public interface CommonConstant {


    /**
     * 路径目录分隔符
     */
    String DIR_SPLIT = "/";

    /**
     * 字符串分隔符
     */
    String STRING_SPLIT = ",";

    /**
     * 后缀分隔符
     */
    String SUFFIX_SPLIT = ".";

    /**
     * 目录默认类型
     */
    String DEFAULT_DIR_TYPE = "dir";

    /**
     * 默认树顶级id
     */
    Long ROOT_PARENT_ID = -1L;

    /**
     * 存储类型-本地
     */
    String FILE_TYPE_LOCAL = "local";

    /**
     * 云存储类型-oss
     */
    String FILE_TYPE_OSS = "oss";

    /**
     * 云存储类型-mini
     */
    String FILE_TYPE_MINIO = "minio";
    /**
     * dtree指定图标
     */
    String DTREE_ICON_1 = "dtree-icon-weibiaoti5";

    /**
     * dtree指定图标
     */
    String DTREE_ICON_2 = "dtree-icon-normal-file";


    /**
     * URL中问号
     */
    String URL_WENHAO = "?";

    /**
     * URL中右斜杠
     */
    String URL_RIGHT_XIEGANG = "/";

}
