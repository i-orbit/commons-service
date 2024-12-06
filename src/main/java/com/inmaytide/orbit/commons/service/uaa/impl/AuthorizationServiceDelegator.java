package com.inmaytide.orbit.commons.service.uaa.impl;

import com.inmaytide.orbit.commons.constants.Constants;
import com.inmaytide.orbit.commons.domain.Oauth2Token;
import com.inmaytide.orbit.commons.domain.OrbitClientDetails;
import com.inmaytide.orbit.commons.domain.Robot;
import com.inmaytide.orbit.commons.domain.SystemUser;
import com.inmaytide.orbit.commons.domain.dto.params.LoginParameters;
import com.inmaytide.orbit.commons.service.CallableWrapper;
import com.inmaytide.orbit.commons.service.RunnableWrapper;
import com.inmaytide.orbit.commons.service.uaa.AuthorizationService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Optional;

/**
 * @author inmaytide
 * @since 2023/5/9
 */
@Service
public class AuthorizationServiceDelegator implements AuthorizationService {

    private final InsideAuthorizationService service;

    public AuthorizationServiceDelegator(InsideAuthorizationService service) {
        this.service = service;
    }

    @Override
    public Oauth2Token refreshToken(String refreshToken) {
        return CallableWrapper.call(
                () -> service.refreshToken(
                        OrbitClientDetails.getInstance().getClientSecretBasicAuthentication(),
                        "refresh_token",
                        refreshToken
                )
        );
    }

    @Override
    public Oauth2Token getToken(LoginParameters params) {
        return CallableWrapper.call(
                () -> service.getToken(
                        OrbitClientDetails.getInstance().getClientSecretBasicAuthentication(),
                        "password",
                        params.getLoginName(),
                        params.getPassword(),
                        params.getPlatform().name(),
                        params.getForcedReplacement().name()
                )
        );
    }

    @Override
    public void revokeToken(String accessToken) {
        RunnableWrapper.execute(() -> service.revokeToken(accessToken, OrbitClientDetails.getInstance().getClientSecretBasicAuthentication()));
    }

    @Override
    public Oauth2Token getRobotToken() {
        return CallableWrapper.call(
                () -> service.getToken(
                        Robot.getInstance().getClientSecretBasicAuthentication(),
                        "client_credentials"
                )
        );
    }

    @Override
    @Cacheable(cacheNames = Constants.CacheNames.USER_DETAILS, key = "#id", unless = "#result == null")
    public SystemUser get(Serializable id) {
        return CallableWrapper.call(() -> service.loadUserById(id));
    }

    @Override
    public Optional<SystemUser> findByUsername(String username) {
        return Optional.empty();
    }

}
