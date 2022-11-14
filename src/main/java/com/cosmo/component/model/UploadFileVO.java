package com.cosmo.component.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UploadFileVO {

    //文件地址
    private String url;

    //文件地址
    private String fileName;

    //文件大小
    private String fileSize;

    //文件格式
    private String fileType;
}
