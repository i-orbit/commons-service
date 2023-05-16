package com.inmaytide.orbit.commons.service.uaa.impl;

import com.inmaytide.orbit.commons.service.configuration.AuthorizedFeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author inmaytide
 * @since 2023/5/15
 */
@AuthorizedFeignClient(name = "uaa", contextId = "user")
interface InsideUserService {

    @GetMapping("/api/internal/users/get-id-by-username")
    Long getIdByUsername(@RequestParam("username") String username);

}
