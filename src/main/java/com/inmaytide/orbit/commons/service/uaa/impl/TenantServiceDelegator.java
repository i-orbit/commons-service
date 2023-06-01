package com.inmaytide.orbit.commons.service.uaa.impl;

import com.inmaytide.orbit.commons.domain.Tenant;
import com.inmaytide.orbit.commons.service.uaa.TenantService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author inmaytide
 * @since 2023/6/1
 */
@Service
public class TenantServiceDelegator implements TenantService {

    private final InsideTenantService service;

    public TenantServiceDelegator(InsideTenantService service) {
        this.service = service;
    }

    @Override
    public List<Tenant> all() {
        return service.pagination(1, Integer.MAX_VALUE).getElements();
    }
}
