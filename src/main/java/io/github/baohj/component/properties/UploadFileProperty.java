package io.github.baohj.component.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "component.uploadfile")
public class UploadFileProperty {

    private String filePath;

    private String fileHost;
}
