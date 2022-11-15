package io.github.baohj.component.config;

import io.github.baohj.component.enums.UploadEnum;
import io.github.baohj.component.factory.UploadFactory;
import io.github.baohj.component.properties.UploadFileProperty;
import io.github.baohj.component.service.impl.NginxUploadServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static io.github.baohj.component.enums.UploadEnum.*;

@Configuration
@EnableConfigurationProperties(UploadFileProperty.class)
@ConditionalOnProperty(name = "component.uploadfile.enabled", havingValue = "true")
public class ComponentAutoConfiguration {

    @Autowired
    private UploadFileProperty componentProperty;

    @Bean(name = "nginx")
    public NginxUploadServiceImpl nginxUpload(){
        NginxUploadServiceImpl myService = new NginxUploadServiceImpl(componentProperty);
        return myService;
    }

    @Bean
    public UploadFactory uploadFactory(){
        UploadFactory uploadFactory = new UploadFactory();
        return uploadFactory;
    }


}
