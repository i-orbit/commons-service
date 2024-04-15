package com.inmaytide.orbit.commons.service.uaa;

import com.inmaytide.orbit.commons.business.SystemUserService;
import com.inmaytide.orbit.commons.constants.Is;
import com.inmaytide.orbit.commons.constants.Platforms;
import com.inmaytide.orbit.commons.domain.Oauth2Token;
import com.inmaytide.orbit.commons.domain.SystemUser;
import com.inmaytide.orbit.commons.domain.dto.params.LoginParameters;

/**
 * @author inmaytide
 * @since 2023/5/9
 */
public interface AuthorizationService extends SystemUserService {

    Oauth2Token refreshToken(String refreshToken);

    Oauth2Token getToken(LoginParameters params);

    void revokeToken(String accessToken);

    Oauth2Token getRobotToken();

}
