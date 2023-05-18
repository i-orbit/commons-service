package com.inmaytide.orbit.commons.service.uaa.impl;

import com.inmaytide.orbit.commons.consts.CacheNames;
import com.inmaytide.orbit.commons.consts.Is;
import com.inmaytide.orbit.commons.consts.Platforms;
import com.inmaytide.orbit.commons.domain.GlobalUser;
import com.inmaytide.orbit.commons.domain.Oauth2Token;
import com.inmaytide.orbit.commons.domain.Robot;
import com.inmaytide.orbit.commons.security.SecurityUtils;
import com.inmaytide.orbit.commons.service.CallableWrapper;
import com.inmaytide.orbit.commons.service.RunnableWrapper;
import com.inmaytide.orbit.commons.service.uaa.AuthorizationService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.Serializable;

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
    public Oauth2Token refreshToken(String clientId, String clientSecret, String refreshToken) {
        return CallableWrapper.call(() -> service.refreshToken(clientId, clientSecret, refreshToken));
    }

    @Override
    public Oauth2Token getToken(String clientId, String clientSecret, String username, String password, Platforms platform, Is forcedReplacement) {
        return CallableWrapper.call(() -> service.getToken(clientId, clientSecret, username, password, platform.name(), forcedReplacement.name()));
    }

    @Override
    public void revokeToken(String accessToken) {
        RunnableWrapper.execute(() -> service.revokeToken(accessToken));
    }

    @Override
    public Oauth2Token getRobotToken() {
        return CallableWrapper.call(() -> service.getToken(Robot.getInstance().getUsername(), Robot.getInstance().getPassword()));
    }

    @Override
    @Cacheable(cacheNames = CacheNames.USER_DETAILS, key = "#id", unless = "#result == null")
    public GlobalUser loadUserById(Serializable id) {
        return CallableWrapper.call(() -> service.loadUserById(id));
    }

    @Override
    public GlobalUser getCurrentUser(String accessToken) {
        return CallableWrapper.call(() -> service.getCurrentUser(accessToken));
    }

    @Override
    public GlobalUser getCurrentUser() {
        return SecurityUtils.getAuthorizedUser();
    }

    @Override
    public Platforms getCurrentPlatform(String accessToken) {
        return CallableWrapper.call(() -> service.getCurrentPlatform(accessToken));
    }

    @Override
    public Platforms getCurrentPlatform() {
        return SecurityUtils.getPlatform().orElse(null);
    }
}
