package io.github.baohj.component.service;

import io.github.baohj.component.model.UploadFileVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadService {

    UploadFileVO upload(MultipartFile file) throws IOException;
}
