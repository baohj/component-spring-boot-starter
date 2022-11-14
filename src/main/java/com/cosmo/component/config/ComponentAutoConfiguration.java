package com.cosmo.component.config;

import com.cosmo.component.properties.UploadFileProperty;
import com.cosmo.component.service.NginxUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(UploadFileProperty.class)
@ConditionalOnProperty(name = "component.uploadfile.enabled", havingValue = "true")
public class ComponentAutoConfiguration {

    @Autowired
    private UploadFileProperty componentProperty;

    @Bean
    public NginxUpload personService(){
        NginxUpload myService = new NginxUpload(componentProperty);
        return myService;
    }
}
