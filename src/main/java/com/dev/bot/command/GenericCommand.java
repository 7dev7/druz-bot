package com.dev.bot.command;

import com.dev.bot.message.MessageTemplate;
import com.dev.domain.model.Question;
import com.dev.domain.repository.UserManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public abstract class GenericCommand extends BotCommand {
    private static final Logger LOG = LoggerFactory.getLogger(GenericCommand.class);
    protected final UserManager userManager;

    public GenericCommand(String commandIdentifier, String description) {
        super(commandIdentifier, description);
        userManager = UserManager.getInstance();
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

    protected void forbidNextQuestion(AbsSender absSender, Chat chat, Integer userId) {
        Question currentQuestion = userManager.getUserCurrentQuestion(userId);
        sendMessage(absSender, chat, MessageTemplate.formatProhibitedNextQuestion(currentQuestion));
    }
}
