package com.dev.bot.route;

import com.dev.bot.handler.api.Handler;
import com.dev.bot.handler.impl.AgreementHandler;
import com.dev.bot.handler.impl.AnswerHandler;
import com.dev.bot.handler.impl.NoneHandler;
import com.dev.bot.handler.impl.YearHandler;
import com.dev.domain.repository.State;
import com.dev.domain.repository.UserManager;
import org.telegram.telegrambots.api.objects.Message;

public class Router {
    private final UserManager userManager;
    private final AnswerHandler answerHandler;
    private final YearHandler yearHandler;
    private final AgreementHandler agreementHandler;
    private final NoneHandler noneHandler;

    public Router() {
        this.userManager = UserManager.getInstance();
        this.answerHandler = new AnswerHandler();
        this.yearHandler = new YearHandler();
        this.agreementHandler = new AgreementHandler();
        this.noneHandler = new NoneHandler();
    }

    public Handler route(Message message) {
        if (message.hasText()) {
            Integer userId = message.getFrom().getId();
            State userState = userManager.getUserState(userId);
            switch (userState) {
                case ANSWERING:
                    return answerHandler;
                case WAIT_FOR_NEXT_QUESTION:
                    return agreementHandler;
                case DATE_CHOOSING:
                    return yearHandler;
                case NONE:
                    return noneHandler;
            }
        }
        return noneHandler;
    }
}
