package com.dev.bot.command;

import com.dev.bot.message.MessageTemplate;
import com.dev.domain.repository.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;

public class StartCommand extends GenericCommand {
    private static final Logger LOG = LoggerFactory.getLogger(RandomQuestionCommand.class);

    public StartCommand() {
        super("start", "With this command you can start the Bot");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        Integer userId = user.getId();
        LOG.info("changed state to DATE_CHOOSING for user " + userId);
        userManager.setUserState(userId, State.DATE_CHOOSING);
        sendMessage(absSender, chat, MessageTemplate.formatGreetings(user));
    }
}
