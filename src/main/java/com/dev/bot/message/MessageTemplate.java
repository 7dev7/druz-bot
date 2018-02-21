package com.dev.bot.message;

import com.dev.domain.model.Question;
import org.telegram.telegrambots.api.objects.User;

public class MessageTemplate {
    public static String formatQuestion(Question question) {
        return String.format("%s\n\n%s", question.getQuestion(), question.getTournamentTitle());
    }

    public static String formatWin(Question question) {
        return String.format("А ты неплох! Ответ: %s\n\n%sХочешь еще?", question.getAnswer(),
                question.getComments() == null ? "" :
                        "Комментарий: " + question.getComments() + "\n\n");
    }

    public static String formatLose(Question question) {
        return String.format("Эх, не в этот раз.\n\nОтвет: %s\n\n%sХочешь еще?", question.getAnswer(),
                question.getComments() == null ? "" :
                        "Комментарий: " + question.getComments() + "\n\n");
    }

    public static String formatUnknownCmd(String cmd) {
        return String.format("Даже я не знаю что '%s' значит", cmd);
    }

    public static String formatProhibitedNextQuestion(Question question) {
        return String.format("Какой быстрый. Ты еще не ответил на предыдущий вопрос:\n\n%s", formatQuestion(question));
    }

    public static String formatGreetings(User user) {
        return String.format("Приветствую, %s %s!\n\n Если хочешь хорошенько подумать жмакни /random_question",
                user.getFirstName(), user.getLastName() == null ? "" : user.getLastName());
    }

    public static String none() {
        return "Слабо ответить на мой вопрос? /random_question";
    }

    public static String bye() {
        return "Ясненько. Если захочешь еще поиграть только скажи мне /random_question";
    }
}
