package com.dev.bot.handler.impl;

import com.dev.bot.handler.api.Handler;
import com.dev.bot.message.MessageTemplate;
import org.telegram.telegrambots.api.objects.Message;

public class NoneHandler implements Handler {
    @Override
    public String handle(Message message) {
        return MessageTemplate.none();
    }
}
