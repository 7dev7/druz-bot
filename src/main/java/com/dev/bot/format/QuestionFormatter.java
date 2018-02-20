package com.dev.bot.format;

import com.dev.domain.model.Question;

public class QuestionFormatter {
    public static String formatQuestion(Question question) {
        return String.format("%s\n\n%s", question.getQuestion().replaceAll("\\n", " "), question.getTournamentTitle());
    }

    public static String formatWinMessage(Question question) {
        return String.format("А ты неплох! Ответ: %s\n\n%sХочешь еще?", question.getAnswer(),
                question.getComments() == null ? "" :
                        "Комментарий: " + question.getComments().replaceAll("\\n", " ") + "\n\n");
    }

    public static String formatLoseMessage(Question question) {
        return String.format("Эх, не в этот раз.\n\nОтвет: %s\n\n%sХочешь еще?", question.getAnswer(),
                question.getComments() == null ? "" :
                        "Комментарий: " + question.getComments().replaceAll("\\n", " ") + "\n\n");
    }
}
