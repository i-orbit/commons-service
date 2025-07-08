package com.inmaytide.orbit.commons.service;

import com.inmaytide.orbit.commons.configuration.GlobalProperties;
import com.inmaytide.orbit.commons.service.authorization.AuthorizationService;
import com.inmaytide.orbit.commons.service.configuration.InternalServiceExchangeFilter;
import com.inmaytide.orbit.commons.service.configuration.OAuth2WebClientFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author inmaytide
 * @since 2024/12/31
 */
@Component
public final class WebClientFactory {

    private static final Map<String, WebClient> CACHE_CLIENTS = new ConcurrentHashMap<>(16);

    private final GlobalProperties properties;

    private final WebClient.Builder webClientBuilder;

    public WebClientFactory(GlobalProperties properties) {
        this.properties = properties;
        this.webClientBuilder = createWebClientBuilder();
    }

    private WebClient.Builder createWebClientBuilder() {
        return WebClient.builder()
                .filter(new InternalServiceExchangeFilter())
                .filter(new OAuth2WebClientFilter());
    }

    public WebClient get(String service) {
        WebClient client = CACHE_CLIENTS.get(service);
        if (client == null) {
            client = createWebClient(service);
            CACHE_CLIENTS.put(service, client);
        }
        return client;
    }

    private WebClient createWebClient(String service) {
        if (AuthorizationService.SERVICE_NAME.equals(service)) {
            return createAuthorizationWebClient();
        }
        String baseURL = StringUtils.removeEnd(properties.getGatewayURI(), "/") + "/";
        if (StringUtils.isBlank(baseURL)) {
            baseURL = "http://";
        }
        baseURL += service;
        return webClientBuilder.baseUrl(baseURL).build();
    }

    private WebClient createAuthorizationWebClient() {
        if (StringUtils.isNotBlank(properties.getAuthorizationServerURI())) {
            return WebClient.builder().baseUrl(properties.getAuthorizationServerURI()).build();
        }
        return WebClient.builder().baseUrl("http://authorization:12003").build();
    }

}
