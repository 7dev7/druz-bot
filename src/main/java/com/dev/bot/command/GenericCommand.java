package com.dev.bot.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public abstract class GenericCommand extends BotCommand {
    private static final Logger LOG = LoggerFactory.getLogger(GenericCommand.class);

    public GenericCommand(String commandIdentifier, String description) {
        super(commandIdentifier, description);
    }

    protected void sendMessage(AbsSender absSender, Chat chat, String msgContent) {
        SendMessage message = new SendMessage();
        message.setChatId(chat.getId());
        message.setText(msgContent);
        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
