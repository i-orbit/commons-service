package com.inmaytide.orbit.commons.service.uaa.impl;

import com.inmaytide.orbit.commons.domain.GlobalUser;
import com.inmaytide.orbit.commons.domain.Tenant;
import com.inmaytide.orbit.commons.domain.dto.result.PageResult;
import com.inmaytide.orbit.commons.service.configuration.AuthorizedFeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author inmaytide
 * @since 2023/5/15
 */
@AuthorizedFeignClient(name = "uaa", contextId = "tenant")
interface InsideTenantService {

    @GetMapping("/api/tenants")
    PageResult<Tenant> pagination(@RequestParam("pageNumber") Integer pageNumber, @RequestParam("pageSize") Integer pageSize);

}
