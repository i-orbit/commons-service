package com.inmaytide.orbit.commons.service.configuration;

import com.inmaytide.orbit.commons.security.SecurityUtils;
import com.inmaytide.orbit.commons.service.authorization.AuthorizationService;
import com.inmaytide.orbit.commons.utils.ApplicationContextHolder;
import org.springframework.lang.NonNull;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import reactor.core.publisher.Mono;

import static com.inmaytide.orbit.commons.constants.Constants.HttpHeaderNames.*;

/**
 * @author inmaytide
 * @since 2024/12/31
 */
public class Oauth2WebClientFilter implements ExchangeFilterFunction {

    @NonNull
    @Override
    public Mono<ClientResponse> filter(@NonNull ClientRequest request, ExchangeFunction next) {
        String token = SecurityUtils.getAccessToken().orElseGet(() -> getAuthorizationService().getRobotToken());
        ClientRequest updatedRequest = ClientRequest.from(request)
                .header(AUTHORIZATION, AUTHORIZATION_PREFIX + token)
                .build();
        return next.exchange(updatedRequest);
    }

    private AuthorizationService getAuthorizationService() {
        return ApplicationContextHolder.getInstance().getBean(AuthorizationService.class);
    }

}
