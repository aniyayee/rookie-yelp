package com.rookie.utils;

import com.rookie.common.constants.Constants;
import java.io.File;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取项目相关配置
 *
 * @author yayee
 */
@Component
@ConfigurationProperties(prefix = "yelp")
@Data
public class YelpConfig {

    /**
     * 项目名称
     */
    private String name;

    /**
     * 版本
     */
    private String version;

    /**
     * 版权年份
     */
    private String copyrightYear;

    /**
     * 上传路径
     */
    private static String fileBaseDir;

    public static String getFileBaseDir() {
        return fileBaseDir;
    }

    public void setFileBaseDir(String fileBaseDir) {
        YelpConfig.fileBaseDir = fileBaseDir + File.separator + Constants.RESOURCE_PREFIX;
    }
}
