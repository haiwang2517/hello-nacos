package com.hyl.services.nacosclient.client;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class ClientController {
    @Value("${app.test}")
    private String test;

    @Value("${app.number:0}")
    private int number;

    @Autowired
    private IRemoteNacosService remoteNacosService;

    @GetMapping("/get")
    @SentinelResource(value = "/client/getTest")
    public String test (){
        String ret = remoteNacosService.echo(getTest() + ":" + getNumber());
        return  ret;
    }

    @GetMapping("/show")
    @SentinelResource(value = "/client/getShow")
    public String show (){
        String ret = remoteNacosService.echo(getTest() + ":" + getNumber());
        return  ret;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
