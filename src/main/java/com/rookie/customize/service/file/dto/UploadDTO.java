package com.rookie.customize.service.file.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author yayee
 */
@Data
@Builder
public class UploadDTO {

    private String url;
    private String fileName;
    private String newFileName;
    private String originalFilename;
}
