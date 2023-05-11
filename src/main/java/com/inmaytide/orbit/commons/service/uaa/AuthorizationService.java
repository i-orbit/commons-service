package com.inmaytide.orbit.commons.service.uaa;

import com.inmaytide.orbit.commons.consts.Platforms;
import com.inmaytide.orbit.commons.domain.Oauth2Token;

/**
 * @author inmaytide
 * @since 2023/5/9
 */
public interface AuthorizationService {

    Oauth2Token refreshToken(String clientId, String clientSecret, String refreshToken);

    Oauth2Token getToken(String clientId, String clientSecret, String username, String password, Platforms platform);

    void revokeToken(String accessToken);

    Oauth2Token getRobotToken();

}
