package com.dev.bot.command;

import com.dev.bot.message.MessageTemplate;
import com.dev.domain.repository.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;

public class ChangeYearsCommand extends GenericCommand {
    private static final Logger LOG = LoggerFactory.getLogger(ChangeYearsCommand.class);

    public ChangeYearsCommand() {
        super("change_years", "with this command you can change years of questions");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        Integer userId = user.getId();
        State userState = userManager.getUserState(userId);
        if (State.ANSWERING.equals(userState)) {
            forbidNextQuestion(absSender, chat, userId);
            return;
        }
        LOG.info("changed state to DATE_CHOOSING for user " + userId);
        userManager.setUserState(userId, State.DATE_CHOOSING);
        sendMessage(absSender, chat, MessageTemplate.chooseYears());
    }
}
