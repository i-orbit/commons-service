package com.inmaytide.orbit.commons.service.uaa;

import com.inmaytide.orbit.commons.consts.Is;
import com.inmaytide.orbit.commons.consts.Platforms;
import com.inmaytide.orbit.commons.domain.GlobalUser;
import com.inmaytide.orbit.commons.domain.Oauth2Token;
import com.inmaytide.orbit.commons.provider.UserDetailsProvider;

/**
 * @author inmaytide
 * @since 2023/5/9
 */
public interface AuthorizationService extends UserDetailsProvider {

    Oauth2Token refreshToken(String refreshToken);

    Oauth2Token getToken(String username, String password, Platforms platform, Is forcedReplacement);

    void revokeToken(String accessToken);

    Oauth2Token getRobotToken();

    /**
     * 获取当前登录用户信息 - 适用系统无法自动获取 Access Token 的情况(WebFlux | Robot登录)
     */
    GlobalUser getCurrentUser(String accessToken);

    Platforms getCurrentPlatform(String accessToken);

    /**
     * 获取当前登录用户信息 - 适用系统可以自动获取 Access Token 的情况
     */
    GlobalUser getCurrentUser();

    Platforms getCurrentPlatform();

}
