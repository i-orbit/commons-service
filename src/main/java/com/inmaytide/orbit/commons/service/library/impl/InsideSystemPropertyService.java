package com.inmaytide.orbit.commons.service.library.impl;

import com.inmaytide.orbit.commons.domain.SystemProperty;
import com.inmaytide.orbit.commons.service.configuration.AuthorizedFeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author inmaytide
 * @since 2023/5/26
 */
@AuthorizedFeignClient(name = "library", contextId = "system-property")
public interface InsideSystemPropertyService {

    @GetMapping("/api/backend/system/properties")
    List<SystemProperty> getProperties(@RequestParam("tenantId") Long tenantId);

}
