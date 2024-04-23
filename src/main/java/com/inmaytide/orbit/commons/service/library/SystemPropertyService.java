package com.inmaytide.orbit.commons.service.library;

import java.util.Optional;

/**
 * @author inmaytide
 * @since 2023/5/26
 */
public interface SystemPropertyService extends com.inmaytide.orbit.commons.business.SystemPropertyService {

    void initializeForTenant(Long tenantId);

}
