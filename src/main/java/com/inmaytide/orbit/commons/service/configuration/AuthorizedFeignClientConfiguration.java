package com.inmaytide.orbit.commons.service.configuration;

import feign.RequestInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;

/**
 * @author inmaytide
 * @since 2023/5/11
 */
class AuthorizedFeignClientConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizedFeignClientConfiguration.class);


    @Bean(name = "authorizedFeignClientInterceptor")
    @ConditionalOnClass(name = "com.alibaba.cloud.nacos.discovery.NacosDiscoveryClient")
    public RequestInterceptor nacosAuthorizedFeignClientInterceptor() {
        LOGGER.info("NacosAuthorizedFeignClientInterceptor was enabled for Feign Services");
        return new NacosAuthorizedFeignClientInterceptor();
    }

}
