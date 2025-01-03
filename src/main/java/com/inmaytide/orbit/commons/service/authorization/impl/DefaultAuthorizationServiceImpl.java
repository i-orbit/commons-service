package com.inmaytide.orbit.commons.service.authorization.impl;

import com.inmaytide.orbit.commons.domain.Oauth2Token;
import com.inmaytide.orbit.commons.domain.OrbitClientDetails;
import com.inmaytide.orbit.commons.domain.Robot;
import com.inmaytide.orbit.commons.domain.SystemUser;
import com.inmaytide.orbit.commons.domain.dto.params.LoginParameters;
import com.inmaytide.orbit.commons.service.WebClientFactory;
import com.inmaytide.orbit.commons.service.authorization.AuthorizationService;
import com.inmaytide.orbit.commons.service.configuration.Constants;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;

/**
 * @author inmaytide
 * @since 2023/5/9
 */
@Service
public class DefaultAuthorizationServiceImpl implements AuthorizationService {

    private final WebClientFactory clientFactory;

    public DefaultAuthorizationServiceImpl(WebClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    private WebClient getClient() {
        return clientFactory.get(SERVICE_NAME);
    }

    private Mono<Oauth2Token> getOauth2Token(MultiValueMap<String, String> params) {
        return getClient().post()
                .uri("/oauth2/token")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .header(HttpHeaders.AUTHORIZATION, OrbitClientDetails.getInstance().getClientSecretBasicAuthentication())
                .body(BodyInserters.fromFormData(params))
                .retrieve()
                .bodyToMono(Oauth2Token.class);
    }

    @Override
    public Oauth2Token refreshToken(String refreshToken) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", AuthorizationGrantType.REFRESH_TOKEN.getValue());
        params.add("refresh_token", refreshToken);
        return getOauth2Token(params).block(Duration.ofSeconds(Constants.BLOCK_TIMEOUT_SECONDS));
    }

    @Override
    public Mono<Oauth2Token> getToken(LoginParameters params) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("username", params.getLoginName());
        body.add("password", params.getPassword());
        body.add(com.inmaytide.orbit.commons.constants.Constants.RequestParameters.PLATFORM, params.getPlatform().name());
        body.add("forcedReplacement", params.getForcedReplacement().name());
        body.add("grant_type", AuthorizationGrantType.PASSWORD.getValue());

        return getClient().post()
                .uri("/oauth2/token")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .header(HttpHeaders.AUTHORIZATION, OrbitClientDetails.getInstance().getClientSecretBasicAuthentication())
                .body(BodyInserters.fromFormData(body))
                .retrieve()
                .bodyToMono(Oauth2Token.class);
    }

    @Override
    public String getRobotToken() {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", AuthorizationGrantType.CLIENT_CREDENTIALS.getValue());
        return getClient().post()
                .uri("/oauth2/token")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .header(HttpHeaders.AUTHORIZATION, Robot.getInstance().getClientSecretBasicAuthentication())
                .body(BodyInserters.fromFormData(body))
                .retrieve()
                .bodyToMono(Oauth2Token.class)
                .blockOptional(Duration.ofSeconds(Constants.BLOCK_TIMEOUT_SECONDS))
                .map(Oauth2Token::getAccessToken)
                .orElseThrow(IllegalStateException::new);
    }

    @Override
    public Mono<Void> revokeToken(String accessToken) {
        return getClient().post()
                .uri("/oauth2/revoke")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .header(HttpHeaders.AUTHORIZATION, Robot.getInstance().getClientSecretBasicAuthentication())
                .bodyValue(Map.of("token", accessToken))
                .retrieve()
                .bodyToMono(Void.class)
                .then();
    }

    @Override
    @Cacheable(cacheNames = com.inmaytide.orbit.commons.constants.Constants.CacheNames.USER_DETAILS, key = "#p0", unless = "#result == null")
    public SystemUser get(Serializable id) {
        return getClient().get()
                .uri("/api/system-users/{id}", id)
                .retrieve()
                .bodyToMono(SystemUser.class)
                .block(Duration.ofSeconds(Constants.BLOCK_TIMEOUT_SECONDS));
    }

    @Override
    public Optional<SystemUser> findByUsername(String username) {
        throw new UnsupportedOperationException();
    }

}
