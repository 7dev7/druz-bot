package com.dev.bot.handler.impl;

import com.dev.bot.handler.api.Handler;
import com.dev.bot.message.MessageTemplate;
import com.dev.domain.repository.State;
import com.dev.domain.repository.UserManager;
import org.telegram.telegrambots.api.objects.Message;

public class YearHandler implements Handler {
    private final UserManager userManager;

    public YearHandler() {
        this.userManager = UserManager.getInstance();
    }

    @Override
    public String handle(Message message) {
        Integer userId = message.getFrom().getId();
        String[] years = message.getText().trim().split("-");
        if (years.length != 2) {
            return MessageTemplate.incorrectYearFormat();
        } else {
            String fYear = years[0].trim();
            String tYear = years[1].trim();
            if (fYear.isEmpty() || tYear.isEmpty()) {
                return MessageTemplate.incorrectYearFormat();
            }
            try {
                Integer fromYear = Integer.valueOf(fYear);
                Integer toYear = Integer.valueOf(tYear);
                userManager.setUserState(userId, State.NONE);
                //TODO store data
                return MessageTemplate.approvedYear();
            } catch (NumberFormatException e) {
                return MessageTemplate.yearContainsNonDigit();
            }
        }
    }
}
