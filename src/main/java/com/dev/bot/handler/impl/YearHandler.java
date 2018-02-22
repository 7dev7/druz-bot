package com.dev.bot.handler.impl;

import com.dev.bot.handler.api.Handler;
import com.dev.bot.message.MessageTemplate;
import com.dev.bot.parser.YearParser;
import com.dev.bot.parser.exception.YearParsingException;
import com.dev.domain.repository.State;
import com.dev.domain.repository.UserManager;
import org.apache.commons.lang3.tuple.Pair;
import org.telegram.telegrambots.api.objects.Message;

public class YearHandler implements Handler {
    private final UserManager userManager;
    private final YearParser yearParser;

    public YearHandler() {
        this.userManager = UserManager.getInstance();
        this.yearParser = new YearParser();
    }

    @Override
    public String handle(Message message) {
        try {
            Pair<Integer, Integer> years = yearParser.parse(message);
            Integer userId = message.getFrom().getId();
            userManager.setUserState(userId, State.NONE);
            userManager.setYearsForUser(userId, years);
            return MessageTemplate.approvedYears();
        } catch (YearParsingException e) {
            return e.getMessage();
        }
    }
}
