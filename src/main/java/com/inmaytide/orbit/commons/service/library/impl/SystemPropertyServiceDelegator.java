package com.inmaytide.orbit.commons.service.library.impl;

import com.inmaytide.orbit.commons.domain.SystemProperty;
import com.inmaytide.orbit.commons.security.SecurityUtils;
import com.inmaytide.orbit.commons.service.library.SystemPropertyService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

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
    public String get(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        return service.getProperties(SecurityUtils.getAuthorizedUser().getTenantId())
                .stream().filter(e -> Objects.equals(e.getKey(), key))
                .findFirst()
                .map(SystemProperty::getValue)
                .orElse(null);
    }
}
