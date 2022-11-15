package io.github.baohj.component.service.impl;

import io.github.baohj.component.model.UploadFileVO;
import io.github.baohj.component.properties.UploadFileProperty;
import io.github.baohj.component.service.UploadService;
import io.github.baohj.component.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class NginxUploadServiceImpl implements UploadService {

    private UploadFileProperty uploadFileProperty;

    public NginxUploadServiceImpl(UploadFileProperty uploadFileProperty){
        this.uploadFileProperty = uploadFileProperty;
    }
    public UploadFileVO upload(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new RuntimeException("请选择文件");
        }
        File file1 = new File(uploadFileProperty.getFilePath());
        if(!file1.exists()){
            file1.mkdirs();
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
