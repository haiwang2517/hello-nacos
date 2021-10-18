package com.hyl.services.nacosgateway.route;

import com.alibaba.cloud.nacos.NacosConfigProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NacosDynamicRouteConfig  {

    private ApplicationEventPublisher publisher;

    public NacosDynamicRouteConfig(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Configuration
    public class NacosDynRoute {
        private NacosConfigProperties nacosConfigProperties;

        public NacosDynRoute(NacosConfigProperties nacosConfigProperties) {
            this.nacosConfigProperties = nacosConfigProperties;
        }

        @Bean
        public NacosRouteDefinitionRepository nacosRouteDefinitionRepository() {
            return new NacosRouteDefinitionRepository(publisher, nacosConfigProperties);
        }
    }
}


