package com.dev.bot.handler.api;

import org.telegram.telegrambots.api.objects.Message;

public interface Handler {
    String handle(Message message);
}
