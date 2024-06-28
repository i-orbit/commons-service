package com.inmaytide.orbit.commons.service.uaa.impl;

import com.inmaytide.orbit.commons.service.uaa.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

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
    public Map<Long, String> findNamesByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Map.of();
        }
        return service.findNamesByIds(StringUtils.join(ids, ","));
    }

    @Override
    public Map<Long, String> findEmailsByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Map.of();
        }
        return service.findEmailsByIds(StringUtils.join(ids, ","));
    }

    @Override
    public Map<Long, String> findTelephoneNumbersByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Map.of();
        }
        return service.findTelephoneNumbersByIds(StringUtils.join(ids, ","));
    }
}
