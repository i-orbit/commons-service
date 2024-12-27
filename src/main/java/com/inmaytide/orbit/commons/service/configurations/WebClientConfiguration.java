package com.inmaytide.orbit.commons.service.configurations;

import com.inmaytide.orbit.commons.security.SecurityUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author inmaytide
 * @since 2024/12/27
 */
@Configuration
public class WebClientConfiguration {

    @Bean("internalApiClientBuilder")
    public WebClient.Builder clientBuilder() {
        return WebClient.builder()
                .filter((request, next) -> {
//                    ClientRequest updatedRequest = ClientRequest.from(request)
//                            .header("Authorization", "Bearer your-token-here")
//                            .build();
                    return next.exchange(request);
                });
    }

}
