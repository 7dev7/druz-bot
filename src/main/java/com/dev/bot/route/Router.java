package com.dev.bot.route;

import com.dev.bot.handler.impl.AgreementHandler;
import com.dev.bot.handler.impl.AnswerHandler;
import com.dev.bot.handler.impl.YearHandler;
import com.dev.bot.message.MessageTemplate;
import com.dev.domain.repository.State;
import com.dev.domain.repository.UserManager;
import org.telegram.telegrambots.api.objects.Message;

public class Router {
    private UserManager userManager;
    private AnswerHandler answerHandler;
    private YearHandler yearHandler;
    private AgreementHandler agreementHandler;

    public Router() {
        this.userManager = UserManager.getInstance();
        this.answerHandler = new AnswerHandler();
        this.yearHandler = new YearHandler();
        this.agreementHandler = new AgreementHandler();
    }

    public String route(Message message) {
        if (message.hasText()) {
            Integer userId = message.getFrom().getId();
            State userState = userManager.getUserState(userId);
            switch (userState) {
                case ANSWERING:
                    return answerHandler.handle(message);
                case WAIT_FOR_NEXT_QUESTION:
                    return agreementHandler.handle(message);
                case DATE_CHOOSING:
                    return yearHandler.handle(message);
                case NONE:
                    return MessageTemplate.none();
            }
        }
        return "";
    }
}
