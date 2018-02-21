package com.dev.bot.handler.impl;

import com.dev.bot.handler.api.Handler;
import com.dev.bot.message.MessageTemplate;
import com.dev.domain.model.Question;
import com.dev.domain.repository.State;
import com.dev.domain.repository.UserManager;
import org.telegram.telegrambots.api.objects.Message;

public class AnswerHandler implements Handler {
    private final UserManager userManager;

    public AnswerHandler() {
        this.userManager = UserManager.getInstance();
    }

    public String handle(Message message) {
        Integer userId = message.getFrom().getId();
        String userAnswer = message.getText();
        Question question = userManager.getUserCurrentQuestion(userId);
        userManager.setUserState(userId, State.WAIT_FOR_NEXT_QUESTION);
        return isCorrectAnswer(userAnswer, question) ?
                MessageTemplate.formatWin(question) : MessageTemplate.formatLose(question);
    }

    private boolean isCorrectAnswer(String userAnswer, Question question) {
        String formattedUserAnswer = userAnswer.toLowerCase().trim();
        String formattedCorrectAnswer = question.getAnswer().toLowerCase().trim();
        formattedCorrectAnswer = formattedCorrectAnswer.charAt(formattedCorrectAnswer.length() - 1) == '.' ?
                formattedCorrectAnswer.substring(0, formattedCorrectAnswer.length() - 1) : formattedCorrectAnswer;
        if (question.getPassCriteria() != null) {
            String formattedPassCriteria = question.getPassCriteria().toLowerCase().trim();
            if (formattedPassCriteria.equals(formattedUserAnswer)) {
                return true;
            }
        }
        return formattedUserAnswer.equals(formattedCorrectAnswer);
    }
}
