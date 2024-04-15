package com.inmaytide.orbit.commons.service.configuration;

import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.nacos.api.exception.NacosException;
import com.inmaytide.orbit.commons.constants.Constants;
import com.inmaytide.orbit.commons.security.SecurityUtils;
import com.inmaytide.orbit.commons.service.uaa.AuthorizationService;
import com.inmaytide.orbit.commons.utils.ApplicationContextHolder;
import com.inmaytide.orbit.commons.utils.CodecUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author inmaytide
 * @since 2023/5/11
 */
class NacosAuthorizedFeignClientInterceptor implements RequestInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(NacosAuthorizedFeignClientInterceptor.class);

    private NacosServiceManager serviceManager;

    private AuthorizationService authorizationService;

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    private NacosServiceManager getServiceManager() {
        if (serviceManager == null) {
            serviceManager = ApplicationContextHolder.getInstance().getBean(NacosServiceManager.class);
        }
        return serviceManager;
    }

    public AuthorizationService getAuthorizationService() {
        if (authorizationService == null) {
            authorizationService = ApplicationContextHolder.getInstance().getBean(AuthorizationService.class);
        }
        return authorizationService;
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
        // 设置 client 端微服务实例标识
        setApplicationInstantId(template);
        // 非 oauth2 相关认证请求且请求中 HttpHeader.Authorization 未设置值
        if (!pathMatcher.match("/oauth2/**", template.path())) {
            setAuthorization(template);
        }
        // 在请求 HttpHeader 中设置调用链标识
        if (CollectionUtils.isEmpty(template.headers().get(Constants.HttpHeaderNames.CALL_CHAIN))) {
            String id;
            try {
                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
                HttpServletRequest request = attributes.getRequest();
                id = request.getHeader(Constants.HttpHeaderNames.CALL_CHAIN);
            } catch (Exception e) {
                id = CodecUtils.randomUUID();
            }
            template.header(Constants.HttpHeaderNames.CALL_CHAIN, id);
        }
    }

    private void setApplicationInstantId(RequestTemplate template) {
        template.header(Constants.HttpHeaderNames.SERVICE_INSTANCE_ID, getInstanceId());
    }

    private void setAuthorization(RequestTemplate template, String token) {
        if (CollectionUtils.isEmpty(template.headers().get(Constants.HttpHeaderNames.AUTHORIZATION))) {
            template.header(Constants.HttpHeaderNames.AUTHORIZATION, Constants.HttpHeaderNames.AUTHORIZATION_PREFIX + token);
        }
    }

    private void setAuthorization(RequestTemplate template) {
        if (SecurityContextHolder.getContext() != null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated() && authentication instanceof BearerTokenAuthentication bearerTokenAuthentication) {
                setAuthorization(template, bearerTokenAuthentication.getToken().getTokenValue());
                return;
            }
        }
        setAuthorization(template, getAuthorizationService().getRobotToken().getAccessToken());
    }
}
