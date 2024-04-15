package com.inmaytide.orbit.commons.service.message.impl;

import com.inmaytide.orbit.commons.domain.Message;
import com.inmaytide.orbit.commons.service.configuration.AuthorizedFeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author inmaytide
 * @since 2024/3/13
 */
@AuthorizedFeignClient(name = "message", contextId = "message")
interface InsideMessageService {

    @PostMapping("/api/messages")
    void createMessage(@RequestBody Message message);

}
