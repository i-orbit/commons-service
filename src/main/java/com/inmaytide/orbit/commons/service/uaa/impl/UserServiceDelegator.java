package com.inmaytide.orbit.commons.service.uaa.impl;

import com.inmaytide.orbit.commons.service.CallableWrapper;
import com.inmaytide.orbit.commons.service.uaa.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author inmaytide
 * @since 2023/5/15
 */
@Component
public class UserServiceDelegator implements UserService {

    private final InsideUserService service;

    public UserServiceDelegator(InsideUserService service) {
        this.service = service;
    }

    @Override
    public Optional<Long> getIdByUsername(@Nullable String username) {
        if (StringUtils.isBlank(username)) {
            return Optional.empty();
        }
        return CallableWrapper.call(() -> Optional.ofNullable(service.getIdByUsername(username)));
    }
}
