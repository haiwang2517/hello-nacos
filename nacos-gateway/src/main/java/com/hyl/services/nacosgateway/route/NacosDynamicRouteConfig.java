package com.hyl.services.nacosgateway.route;

import com.alibaba.cloud.nacos.NacosConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NacosDynamicRouteConfig  {

    private final ApplicationEventPublisher publisher;

    public NacosDynamicRouteConfig(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Configuration
    public class NacosDynRoute {
        private final NacosConfigProperties nacosConfigProperties;

        public NacosDynRoute(NacosConfigProperties nacosConfigProperties) {
            this.nacosConfigProperties = nacosConfigProperties;
        }

        @Bean
        public NacosRouteDefinitionRepository nacosRouteDefinitionRepository() {
            return new NacosRouteDefinitionRepository(publisher, nacosConfigProperties);
        }
    }
}


