package com.dev.service;

import com.dev.domain.model.Question;
import com.dev.domain.repository.State;
import com.dev.domain.repository.UserManager;
import org.telegram.telegrambots.api.objects.Message;

public class AnswerService {
    private static final String WIN_MSG = "А ты неплох! Ответ: %s\n\n%sХочешь еще?";
    private static final String LOSE_MSG = "Эх, не в этот раз.\n\nОтвет: %s\n\n%sХочешь еще?";

    public String handleAnswer(Message message) {
        UserManager userManager = UserManager.getInstance();
        Integer userId = message.getFrom().getId();
        String userAnswer = message.getText();
        Question question = userManager.getUserCurrentQuestion(userId);
        userManager.setUserState(userId, State.WAIT_FOR_NEXT_QUESTION);
        if (isCorrectAnswer(userAnswer, question)) {
            return String.format(WIN_MSG, question.getAnswer(),
                    question.getComments() == null ? "" : "Комментарий: " + question.getComments() + "\n\n");
        } else {
            return String.format(LOSE_MSG, question.getAnswer(),
                    question.getComments() == null ? "" : "Комментарий: " + question.getComments() + "\n\n");
        }
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
