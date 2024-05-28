package com.inmaytide.orbit.commons.service.core.impl;

import com.inmaytide.orbit.commons.service.configuration.AuthorizedFeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author inmaytide
 * @since 2023/5/26
 */
@AuthorizedFeignClient(name = "core", contextId = "system-property")
public interface InsideSystemPropertyService {

    @GetMapping("/api/system/properties/value")
    String getValue(@RequestParam("tenantId") Long tenantId, @RequestParam("key") String key);

}
