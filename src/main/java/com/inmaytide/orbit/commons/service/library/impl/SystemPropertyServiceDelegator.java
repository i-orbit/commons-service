package com.inmaytide.orbit.commons.service.library.impl;

import com.inmaytide.orbit.commons.domain.SystemProperty;
import com.inmaytide.orbit.commons.security.SecurityUtils;
import com.inmaytide.orbit.commons.service.library.SystemPropertyService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

/**
 * @author inmaytide
 * @since 2023/5/26
 */
@Service
public class SystemPropertyServiceDelegator implements SystemPropertyService {

    private final InsideSystemPropertyService service;

    public SystemPropertyServiceDelegator(InsideSystemPropertyService service) {
        this.service = service;
    }


    @Override
    public Optional<String> getValue(String key) {
        return Optional.ofNullable(service.getValue(SecurityUtils.getAuthorizedUser().getTenantId(), key));
    }


    @Override
    public Optional<Integer> getIntValue(String key) {
        String value = service.getValue(SecurityUtils.getAuthorizedUser().getTenantId(), key);
        return NumberUtils.isCreatable(value) ? Optional.of(NumberUtils.createInteger(value)) : Optional.empty();
    }

    @Override
    public void initializeForTenant(Long tenantId) {
        service.initializeForTenant(tenantId);
    }
}
