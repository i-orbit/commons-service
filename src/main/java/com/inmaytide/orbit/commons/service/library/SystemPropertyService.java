package com.inmaytide.orbit.commons.service.library;

import java.util.Optional;

/**
 * @author inmaytide
 * @since 2023/5/26
 */
public interface SystemPropertyService {


    Optional<String> getValue(Long tenant, String key);

    Optional<Integer> getIntValue(Long tenant, String key);

    void initializeForTenant(Long tenantId);

}
