package com.inmaytide.orbit.commons.service.uaa.impl;

import com.inmaytide.orbit.commons.domain.Oauth2Token;
import com.inmaytide.orbit.commons.domain.SystemUser;
import com.inmaytide.orbit.commons.service.configuration.AuthorizedFeignClient;
import feign.Headers;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

/**
 * @author inmaytide
 * @since 2023/5/9
 */
@AuthorizedFeignClient(name = "uaa", contextId = "authorization")
interface InsideAuthorizationService {

    @PostMapping(value = "/oauth2/token", consumes = "multipart/form-data")
    Oauth2Token refreshToken(@RequestHeader("Authorization") String clientSecretBasicAuthentication,
                             @RequestPart("grant_type") String grantType,
                             @RequestPart("refresh_token") String refreshToken);

    @PostMapping(value = "/oauth2/token", consumes = "multipart/form-data")
    Oauth2Token getToken(@RequestHeader("Authorization") String clientSecretBasicAuthentication,
                         @RequestPart("grant_type") String grantType,
                         @RequestPart("username") String username,
                         @RequestPart("password") String password,
                         @RequestPart("platform") String platform,
                         @RequestPart("forcedReplacement") String forcedReplacement);

    @PostMapping(value = "/oauth2/token", consumes = "multipart/form-data")
    Oauth2Token getToken(@RequestHeader("Authorization") String clientSecretBasicAuthentication, @RequestPart("grant_type") String grantType);

    @PostMapping(value = "/oauth2/revoke", consumes = "multipart/form-data")
    void revokeToken(@RequestPart("token") String token, @RequestHeader("Authorization") String clientSecretBasicAuthentication);

    @GetMapping(value = "/api/users/systemd/{id}")
    SystemUser loadUserById(@PathVariable("id") Serializable id);

}
