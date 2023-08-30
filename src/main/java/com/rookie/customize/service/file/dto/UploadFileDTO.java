package com.rookie.customize.service.file.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yayee
 */
@Data
@NoArgsConstructor
public class UploadFileDTO {

    public UploadFileDTO(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    private String imgUrl;
}
