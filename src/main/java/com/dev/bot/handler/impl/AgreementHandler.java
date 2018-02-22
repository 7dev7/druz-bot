package com.dev.bot.handler.impl;

import com.dev.bot.handler.api.Handler;
import com.dev.bot.message.MessageTemplate;
import com.dev.domain.model.Question;
import com.dev.domain.repository.State;
import com.dev.domain.repository.UserManager;
import com.dev.service.RandomQuestionService;
import org.telegram.telegrambots.api.objects.Message;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class AgreementHandler implements Handler {
    private final Set<String> agreeWords = new HashSet<>(Arrays.asList("да", "д", "давай", "yes", "y", "конечно", "go", "g"));
    private final UserManager userManager;
    private final RandomQuestionService randomQuestionService;

    public AgreementHandler() {
        this.userManager = UserManager.getInstance();
        this.randomQuestionService = new RandomQuestionService();
    }

    @Override
    public String handle(Message message) {
        Integer userId = message.getFrom().getId();
        String text = message.getText().trim().toLowerCase();
        if (agreeWords.contains(text)) {
            Question question = randomQuestionService.get(userManager.getYearsForUser(userId));
            userManager.setUserState(userId, State.ANSWERING);
            userManager.setUserCurrentQuestion(userId, question);
            return MessageTemplate.formatQuestion(question);
        } else {
            userManager.setUserState(userId, State.NONE);
            return MessageTemplate.bye();
        }
    }
}
