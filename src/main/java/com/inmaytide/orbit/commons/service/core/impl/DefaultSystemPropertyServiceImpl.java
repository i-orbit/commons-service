package com.inmaytide.orbit.commons.service.core.impl;

import com.inmaytide.orbit.commons.service.core.SystemPropertyService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

/**
 * @author inmaytide
 * @since 2024/12/18
 */
@Service
public class DefaultSystemPropertyServiceImpl implements SystemPropertyService {

    private final WebClient client;

    public DefaultSystemPropertyServiceImpl(@Qualifier("internalApiClientBuilder") WebClient.Builder clientBuilder) {
        this.client = clientBuilder.build();
    }

    @Override
    public Optional<String> getValue(Long tenant, String key) {
        return Optional.empty();
    }

    @Override
    public Optional<Integer> getIntValue(Long tenant, String key) {
        return Optional.empty();
    }

}
