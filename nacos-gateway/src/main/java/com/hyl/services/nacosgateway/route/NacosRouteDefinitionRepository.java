package com.hyl.services.nacosgateway.route;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.cloud.nacos.NacosConfigProperties;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.common.utils.StringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.context.ApplicationEventPublisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;

public class NacosRouteDefinitionRepository implements RouteDefinitionRepository {

    private ApplicationEventPublisher publisher;
    private NacosConfigManager nacosConfigManager;
    private NacosConfigProperties nacosConfigProperties;

    public NacosRouteDefinitionRepository(ApplicationEventPublisher publisher, NacosConfigProperties nacosConfigProperties) {
        this.publisher = publisher;
        this.nacosConfigProperties = nacosConfigProperties;
        this.nacosConfigManager = new NacosConfigManager(nacosConfigProperties);
        addListener();
    }

    private void addListener() {
        try {
            nacosConfigManager.getConfigService().addListener(nacosConfigProperties.getName(), nacosConfigProperties.getGroup(), new Listener() {
                @Override
                public Executor getExecutor() {
                    return null;
                }

                @Override
                public void receiveConfigInfo(String configInfo) {
                    publisher.publishEvent(new RefreshRoutesEvent(this));
                }
            });
        }catch (NacosException e){
            // TODO log print
            e.printStackTrace();
        }
    }

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {

        try {
            String configContent = nacosConfigManager.getConfigService().getConfig(nacosConfigProperties.getName(), nacosConfigProperties.getGroup(), 5000);
            List<RouteDefinition> routeDefinitions = getListByStr(configContent);
            return Flux.fromIterable(routeDefinitions);
        } catch (NacosException e) {
            //TODO log print
            System.out.println("load config");
        }
        return Flux.fromIterable(Collections.EMPTY_LIST);
    }

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return null;
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return null;
    }

    private List<RouteDefinition> getListByStr(String content) {
        if(StringUtils.isBlank(content)){
            return new ArrayList<>(0);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return Arrays.asList(objectMapper.readValue(content, RouteDefinition[].class));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return new ArrayList<>(0);
    }

}
