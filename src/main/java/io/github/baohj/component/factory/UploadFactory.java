package io.github.baohj.component.factory;

import io.github.baohj.component.enums.UploadEnum;
import io.github.baohj.component.service.UploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class UploadFactory implements ApplicationContextAware {

    public static Map<String, UploadService> uploadMap = new HashMap<String, UploadService>();

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        //根据接口类型返回相应的所有bean
        Map<String, UploadService> map = applicationContext.getBeansOfType(UploadService.class);
        for(String key : map.keySet()){
            UploadService uploadService = map.get(key);
            uploadMap.put(key,uploadService);
            log.info("文件上传服务:key={}",key);
        }
    }


    public UploadService getUploadService(UploadEnum uploadEnum) {
        UploadService connectionService = uploadMap.get(uploadEnum.name());
        if (connectionService != null) {
            return connectionService;
        } else {
            return null;
        }
    }
}
