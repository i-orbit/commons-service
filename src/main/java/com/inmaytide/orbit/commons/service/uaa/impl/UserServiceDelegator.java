package com.inmaytide.orbit.commons.service.uaa.impl;

import com.inmaytide.orbit.commons.domain.SystemUser;
import com.inmaytide.orbit.commons.service.CallableWrapper;
import com.inmaytide.orbit.commons.service.uaa.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
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
    public Optional<SystemUser> getUserByUsername(String username) {
        if (StringUtils.isBlank(username)) {
            return Optional.empty();
        }
        return CallableWrapper.call(() -> Optional.ofNullable(service.getUserByUsername(username)));
    }

    @Override
    public Map<Long, String> findEmailsWithIds(List<Long> ids) {
        return Map.of(9999L, "inmaytide@gmail.com");
    }

    @Override
    public Map<Long, String> findTelephoneNumbersWithIds(List<Long> ids) {
        return Collections.emptyMap();
    }

}
