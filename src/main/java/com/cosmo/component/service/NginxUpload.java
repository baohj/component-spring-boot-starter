package com.cosmo.component.service;

import com.cosmo.component.function.UploadFuntion;
import com.cosmo.component.model.UploadFileVO;
import com.cosmo.component.properties.UploadFileProperty;
import com.cosmo.component.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class NginxUpload implements UploadFuntion {

    private UploadFileProperty uploadFileProperty;

    public NginxUpload(UploadFileProperty uploadFileProperty){
        this.uploadFileProperty = uploadFileProperty;
    }
    public UploadFileVO upload(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new RuntimeException("请选择文件");
        }
        String fileName = file.getOriginalFilename();
        String fileType = fileName.substring(fileName.lastIndexOf(".")+1);
        String prefix=fileName.substring(fileName.lastIndexOf("."));
        String newFileName = UUID.randomUUID() +prefix;
        File dest = new File(uploadFileProperty.getFilePath() + newFileName);
        file.transferTo(dest);
        UploadFileVO vo = new UploadFileVO().setUrl(uploadFileProperty.getFileHost()+newFileName)
                .setFileName(fileName).setFileSize(CommonUtil.formetFileSize(file.getSize())).setFileType(fileType);
        return vo;
    }
}
