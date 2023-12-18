package com.inmaytide.orbit.commons.service.uaa.impl;

import com.inmaytide.orbit.commons.domain.SystemUser;
import com.inmaytide.orbit.commons.service.configuration.AuthorizedFeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author inmaytide
 * @since 2023/5/15
 */
@AuthorizedFeignClient(name = "uaa", contextId = "user")
interface InsideUserService {

    @GetMapping("/api/users/by-username")
    SystemUser getUserByUsername(@RequestParam("username") String username);

}
