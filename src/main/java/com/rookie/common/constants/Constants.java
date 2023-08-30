package com.rookie.common.constants;

/**
 * @author yayee
 */
public class Constants {

    private Constants() {
    }

    public static final int KB = 1024;

    public static final int MB = KB * 1024;

    public static final int GB = MB * 1024;

    /**
     * 默认用户账号 前缀
     */
    public static final String USER_NAME_PREFIX = "user_";

    /**
     * 资源映射路径 前缀
     */
    public static final String RESOURCE_PREFIX = "image";

    public static class UploadSubDir {

        private UploadSubDir() {
        }

        public static final String IMPORT_PATH = "import";

        public static final String BLOG_IMAGE_PATH = "blog";

        public static final String DOWNLOAD_PATH = "download";

        public static final String UPLOAD_PATH = "upload";
    }
}
