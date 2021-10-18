package com.hyl.services.nacosclient.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "nacos-server", fallback = RemoteNacosFallbackService.class)
public interface IRemoteNacosService {
    @GetMapping("/echo/{string}")
    String echo(@PathVariable String string);
}
