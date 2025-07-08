package com.inmaytide.orbit.commons.service.core.impl;

import com.inmaytide.orbit.commons.service.WebClientFactory;
import com.inmaytide.orbit.commons.service.configuration.Constants;
import com.inmaytide.orbit.commons.service.core.SystemPropertyService;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.Optional;

/**
 * @author inmaytide
 * @since 2024/12/18
 */
@Service
public class DefaultSystemPropertyServiceImpl implements SystemPropertyService {

    private static final String SERVICE_NAME = "core";

    private final WebClientFactory clientFactory;

    public DefaultSystemPropertyServiceImpl(WebClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    private WebClient getClient() {
        return clientFactory.get(SERVICE_NAME);
    }

    @Override
    public Optional<String> getValue(String tenant, String key) {
        return getClient().get()
                .uri(builder -> builder.path("/api/system/properties/value").queryParam("tenantId", tenant).queryParam("key", key).build())
                .retrieve()
                .bodyToMono(String.class)
                .blockOptional(Duration.ofSeconds(Constants.BLOCK_TIMEOUT_SECONDS));
    }

    @Override
    public Optional<Integer> getIntValue(String tenant, String key) {
        return getValue(tenant, key).filter(NumberUtils::isCreatable).map(NumberUtils::createInteger);
    }

}
