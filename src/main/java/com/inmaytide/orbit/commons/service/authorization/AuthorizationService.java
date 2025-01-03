package com.inmaytide.orbit.commons.service.authorization;

import com.inmaytide.orbit.commons.business.RefreshTokenService;
import com.inmaytide.orbit.commons.business.SystemUserService;
import com.inmaytide.orbit.commons.domain.Oauth2Token;
import com.inmaytide.orbit.commons.domain.dto.params.LoginParameters;
import reactor.core.publisher.Mono;

/**
 * @author inmaytide
 * @since 2023/5/9
 */
public interface AuthorizationService extends SystemUserService, RefreshTokenService {

    String SERVICE_NAME = "authorization";

    Mono<Oauth2Token> getToken(LoginParameters params);

    Mono<Void> revokeToken(String accessToken);

    String getRobotToken();

}
