package com.inmaytide.orbit.commons.service.message.impl;

import com.inmaytide.orbit.commons.domain.Message;
import com.inmaytide.orbit.commons.service.message.MessageService;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author inmaytide
 * @since 2024/3/13
 */
@Service
public class MessageServiceDelegator implements MessageService {

    private final InsideMessageService insideMessageService;

    public MessageServiceDelegator(InsideMessageService insideMessageService) {
        this.insideMessageService = insideMessageService;
    }

    @Override
    public void createMessage(Message message) {
        Objects.requireNonNull(message);
        insideMessageService.createMessage(message);
    }
}
