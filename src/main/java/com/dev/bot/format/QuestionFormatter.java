package com.dev.bot.format;

import com.dev.domain.model.Question;

public class QuestionFormatter {
    public static String formatQuestion(Question question) {
        return String.format("%s \n\n%s", question.getQuestion().replaceAll("\\n", " "), question.getTournamentTitle());
    }
}
