package com.cosmo.component.function;

import com.cosmo.component.model.UploadFileVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadFuntion {

    UploadFileVO upload(MultipartFile file) throws IOException;
}
