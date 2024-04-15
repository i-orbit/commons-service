package com.inmaytide.orbit.commons.service.uaa.impl;

import com.inmaytide.orbit.commons.constants.Constants;
import com.inmaytide.orbit.commons.constants.Platforms;
import com.inmaytide.orbit.commons.domain.Oauth2Token;
import com.inmaytide.orbit.commons.domain.OrbitClientDetails;
import com.inmaytide.orbit.commons.domain.Robot;
import com.inmaytide.orbit.commons.domain.SystemUser;
import com.inmaytide.orbit.commons.domain.dto.params.LoginParameters;
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
    public Oauth2Token refreshToken(String refreshToken) {
        return CallableWrapper.call(() -> service.refreshToken(OrbitClientDetails.getInstance().getClientId(), OrbitClientDetails.getInstance().getClientSecret(), refreshToken));
    }

    @Override
    public Oauth2Token getToken(LoginParameters params) {
        return CallableWrapper.call(
                () -> service.getToken(
                        OrbitClientDetails.getInstance().getClientId(),
                        OrbitClientDetails.getInstance().getClientSecret(),
                        params.getLoginName(),
                        params.getPassword(),
                        params.getPlatform().name(),
                        params.getForcedReplacement().name()
                )
        );
    }

    @Override
    public void revokeToken(String accessToken) {
        RunnableWrapper.execute(() -> service.revokeToken(accessToken, OrbitClientDetails.getInstance().getClientId(), OrbitClientDetails.getInstance().getClientSecret()));
    }

    @Override
    public Oauth2Token getRobotToken() {
        return CallableWrapper.call(() -> service.getToken(Robot.getInstance().getLoginName(), Robot.getInstance().getPassword()));
    }

    @Override
    @Cacheable(cacheNames = Constants.CacheNames.USER_DETAILS, key = "#id", unless = "#result == null")
    public SystemUser get(Serializable id) {
        return CallableWrapper.call(() -> service.loadUserById(id));
    }

}
