package com.inmaytide.orbit.commons.service.uaa.impl;

import com.inmaytide.orbit.commons.domain.Oauth2Token;
import com.inmaytide.orbit.commons.domain.SystemUser;
import com.inmaytide.orbit.commons.service.configuration.AuthorizedFeignClient;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

/**
 * @author inmaytide
 * @since 2023/5/9
 */
@AuthorizedFeignClient(name = "uaa", contextId = "authorization")
interface InsideAuthorizationService {

    @PostMapping(value = "/oauth2/token?grant_type=refresh_token")
    Oauth2Token refreshToken(@RequestHeader("Authorization") String clientSecretBasicAuthentication,
                             @RequestParam("refresh_token") String refreshToken);

    @PostMapping(value = "/oauth2/token?grant_type=password")
    Oauth2Token getToken(@RequestHeader("Authorization") String clientSecretBasicAuthentication,
                         @RequestParam("username") String username,
                         @RequestParam("password") String password,
                         @RequestParam("platform") String platform,
                         @RequestParam("forcedReplacement") String forcedReplacement);

    @PostMapping(value = "/oauth2/token?grant_type=client_credentials")
    Oauth2Token getToken(@RequestHeader("Authorization") String clientSecretBasicAuthentication);

    @PostMapping(value = "/oauth2/revoke")
    void revokeToken(@RequestParam("token") String token, @RequestHeader("Authorization") String clientSecretBasicAuthentication);

    @GetMapping(value = "/api/internal/users/{id}")
    SystemUser loadUserById(@PathVariable("id") Serializable id);

}
