package com.inmaytide.orbit.commons.service.uaa.impl;

import com.inmaytide.orbit.commons.service.configuration.AuthorizedFeignClient;

/**
 * @author inmaytide
 * @since 2023/5/15
 */
@AuthorizedFeignClient(name = "uaa", contextId = "user")
interface InsideUserService {

}
