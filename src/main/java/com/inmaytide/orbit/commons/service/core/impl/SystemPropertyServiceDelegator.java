package com.inmaytide.orbit.commons.service.core.impl;

import com.inmaytide.orbit.Version;
import com.inmaytide.orbit.commons.constants.Constants;
import com.inmaytide.orbit.commons.security.SecurityUtils;
import com.inmaytide.orbit.commons.service.core.SystemPropertyService;
import org.apache.commons.lang3.math.NumberUtils;
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
    public Optional<String> getValue(Long tenant, String key) {
        return Optional.ofNullable(service.getValue(tenant, key));
    }

    @Override
    public Optional<Integer> getIntValue(Long tenant, String key) {
        String value = service.getValue(SecurityUtils.getAuthorizedUser().getTenant(), key);
        return NumberUtils.isCreatable(value) ? Optional.of(NumberUtils.createInteger(value)) : Optional.empty();
    }

}
