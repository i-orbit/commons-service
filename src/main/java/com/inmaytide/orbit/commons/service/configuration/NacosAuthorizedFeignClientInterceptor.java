package com.inmaytide.orbit.commons.service.configuration;

import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.nacos.api.exception.NacosException;
import com.inmaytide.orbit.commons.consts.HttpHeaderNames;
import com.inmaytide.orbit.commons.utils.ApplicationContextHolder;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;

/**
 * @author inmaytide
 * @since 2023/5/11
 */
class NacosAuthorizedFeignClientInterceptor implements RequestInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(NacosAuthorizedFeignClientInterceptor.class);

    private NacosServiceManager serviceManager;

    private NacosServiceManager getServiceManager() {
        if (serviceManager == null) {
            serviceManager = ApplicationContextHolder.getInstance().getBean(NacosServiceManager.class);
        }
        return serviceManager;
    }

    private String getInstanceId() {
        String service = ApplicationContextHolder.getInstance().getProperty("spring.application.name");
        try {
            return getServiceManager().getNamingService()
                    .selectOneHealthyInstance(service)
                    .getInstanceId();
        } catch (NacosException e) {
            LOG.error("Failed to get instanceId for current service, {}", service, e);
        }
        return StringUtils.EMPTY;
    }

    @Override
    public void apply(RequestTemplate template) {
        setApplicationInstantId(template);
        // 这里由于ReactiveSecurityContextHolder获取不到当前的context, 暂时修改为手动获取token
        setAuthorization(template);
    }

    private void setApplicationInstantId(RequestTemplate template) {
        template.header(HttpHeaderNames.SERVICE_INSTANCE_ID, getInstanceId());
    }

    private void setAuthorization(RequestTemplate template, OAuth2AccessToken token) {
        template.header(HttpHeaderNames.AUTHORIZATION, HttpHeaderNames.AUTHORIZATION_PREFIX + token.getTokenValue());
    }

    private void setAuthorization(RequestTemplate template) {
        if (SecurityContextHolder.getContext() != null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication instanceof BearerTokenAuthentication) {
                setAuthorization(template, ((BearerTokenAuthentication) authentication).getToken());
            }
        }
    }
}
