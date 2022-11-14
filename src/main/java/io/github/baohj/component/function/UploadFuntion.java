package io.github.baohj.component.function;

import io.github.baohj.component.model.UploadFileVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadFuntion {

    UploadFileVO upload(MultipartFile file) throws IOException;
}
