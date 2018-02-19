package com.dev.service;

import com.dev.domain.model.Question;
import com.dev.domain.repository.State;
import com.dev.domain.repository.UserManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.objects.Message;

public class AnswerService {
    private static final Logger LOG = LoggerFactory.getLogger(AnswerService.class);
    private static final String WIN_MSG = "Правильно! Ответ: %s\nХотите следующий вопрос?";
    private static final String LOSE_MSG = "Увы, этот ответ не правильный :(\n\nОтвет: %s\n\nХотите следующий вопрос?";

    public String handleAnswer(Message message) {
        UserManager userManager = UserManager.getInstance();
        Integer userId = message.getFrom().getId();
        String userAnswer = message.getText();
        Question question = userManager.getUserCurrentQuestion(userId);
        userManager.setUserState(userId, State.WAIT_FOR_NEXT_QUESTION);
        if (isCorrectAnswer(userAnswer, question)) {
            return String.format(WIN_MSG, question.getAnswer());
        } else {
            return String.format(LOSE_MSG, question.getAnswer());
        }
    }

    private boolean isCorrectAnswer(String userAnswer, Question question) {
        LOG.info(String.format("question: [%s], answer: [%s], passCriteria: [%s], userAnswer: [%s]",
                question.getQuestion(), question.getAnswer(), question.getPassCriteria(), userAnswer));
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
