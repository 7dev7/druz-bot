package com.dev.service;

import com.dev.bot.format.QuestionFormatter;
import com.dev.domain.model.Question;
import com.dev.domain.repository.State;
import com.dev.domain.repository.UserManager;
import org.telegram.telegrambots.api.objects.Message;

public class AnswerService {
    public String handleAnswer(Message message) {
        UserManager userManager = UserManager.getInstance();
        Integer userId = message.getFrom().getId();
        String userAnswer = message.getText();
        Question question = userManager.getUserCurrentQuestion(userId);
        userManager.setUserState(userId, State.WAIT_FOR_NEXT_QUESTION);
        return isCorrectAnswer(userAnswer, question) ?
                QuestionFormatter.formatWinMessage(question) : QuestionFormatter.formatLoseMessage(question);
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
