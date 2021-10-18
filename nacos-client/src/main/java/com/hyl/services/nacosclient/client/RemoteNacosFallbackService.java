package com.hyl.services.nacosclient.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
public class RemoteNacosFallbackService implements IRemoteNacosService {

    @Override
    public String echo(@PathVariable String string){
        return "当前请求被降级啦";
    }
}
