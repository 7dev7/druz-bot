package com.dev.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commandbot.commands.BotCommand;

public class RandomQuestionCommand extends BotCommand {
    private static final Logger LOG = LoggerFactory.getLogger(RandomQuestionCommand.class);

    public RandomQuestionCommand() {
        super("random_question", "get random question");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        LOG.info("executed");
    }
}
