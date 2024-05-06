package com.inmaytide.orbit.commons.service.uaa.impl;

import com.inmaytide.orbit.commons.service.configuration.AuthorizedFeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author inmaytide
 * @since 2023/5/15
 */
@AuthorizedFeignClient(name = "uaa", contextId = "user")
interface InsideUserService {

    @GetMapping("/api/users/names")
    Map<Long, String> findNamesByIds(@RequestParam("ids") String ids);

    @GetMapping("/api/users/emails")
    Map<Long, String> findEmailsByIds(@RequestParam("ids") String ids);

    @GetMapping("/api/users/telephone-numbers")
    Map<Long, String> findTelephoneNumbersByIds(@RequestParam("ids") String ids);

}
