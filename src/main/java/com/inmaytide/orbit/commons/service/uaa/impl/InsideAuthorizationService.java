package com.inmaytide.orbit.commons.service.uaa.impl;

import com.inmaytide.orbit.commons.consts.Platforms;
import com.inmaytide.orbit.commons.domain.GlobalUser;
import com.inmaytide.orbit.commons.domain.Oauth2Token;
import com.inmaytide.orbit.commons.service.configuration.AuthorizedFeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Serializable;

/**
 * @author inmaytide
 * @since 2023/5/9
 */
@AuthorizedFeignClient(name = "uaa", contextId = "authorization")
interface InsideAuthorizationService {

    @PostMapping("/oauth2/token?grant_type=refresh_token")
    Oauth2Token refreshToken(@RequestParam("client_id") String clientId,
                             @RequestParam("client_secret") String clientSecret,
                             @RequestParam("refresh_token") String refreshToken);

    @PostMapping("/oauth2/token?grant_type=password")
    Oauth2Token getToken(@RequestParam("client_id") String clientId,
                         @RequestParam("client_secret") String clientSecret,
                         @RequestParam("username") String username,
                         @RequestParam("password") String password,
                         @RequestParam("platform") String platform,
                         @RequestParam("forcedReplacement") String forcedReplacement);

    @PostMapping("/oauth2/token?grant_type=client_credentials")
    Oauth2Token getToken(@RequestParam("client_id") String clientId, @RequestParam("client_secret") String clientSecret);

    @PostMapping(value = "/oauth2/revoke")
    void revokeToken(@RequestParam("token") String token, @RequestParam("client_id") String clientId, @RequestParam("client_secret") String clientSecret);

    @GetMapping("/api/internal/users/{id}")
    GlobalUser loadUserById(@PathVariable("id") Serializable id);

    @GetMapping(value = "/api/users/current", headers = {"Authorization=Bearer {accessToken}"})
    GlobalUser getCurrentUser(@RequestParam("accessToken") String accessToken);

    @GetMapping(value = "/api/users/current/platform", headers = {"Authorization=Bearer {accessToken}"})
    Platforms getCurrentPlatform(@RequestParam("accessToken") String accessToken);

}
