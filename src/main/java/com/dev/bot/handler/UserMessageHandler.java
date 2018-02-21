package com.dev.bot.handler;

import com.dev.bot.message.MessageTemplate;
import com.dev.domain.model.Question;
import com.dev.domain.repository.State;
import com.dev.domain.repository.UserManager;
import com.dev.service.AnswerService;
import com.dev.service.RandomQuestionService;
import org.telegram.telegrambots.api.objects.Message;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


//TODO refactor this
public class UserMessageHandler {
    private final UserManager userManager;
    private final AnswerService answerService;
    private final RandomQuestionService randomQuestionService;
    private final Set<String> agreeWords = new HashSet<>(Arrays.asList("да", "д", "давай", "yes", "y", "конечно", "go", "g"));

    public UserMessageHandler() {
        this.userManager = UserManager.getInstance();
        this.answerService = new AnswerService();
        this.randomQuestionService = new RandomQuestionService();
    }

    public String handle(Message message) {
        if (message.hasText()) {
            Integer userId = message.getFrom().getId();
            State userState = userManager.getUserState(userId);
            switch (userState) {
                case ANSWERING:
                    return answerService.handleAnswer(message);
                case WAIT_FOR_NEXT_QUESTION:
                    return handleWaitQuestion(message, userId);
                case DATE_CHOOSING:
                    return handleDateChoosing(message, userId);
                case NONE:
                    return MessageTemplate.none();
            }
        }
        return "";
    }

    //TODO move out to separate class
    private String handleDateChoosing(Message message, Integer userId) {
        String[] years = message.getText().trim().split("-");
        if (years.length != 2) {
            return MessageTemplate.incorrectYearFormat();
        } else {
            String fYear = years[0].trim();
            String tYear = years[1].trim();
            if (fYear.isEmpty() || tYear.isEmpty()) {
                return MessageTemplate.incorrectYearFormat();
            }
            try {
                Integer fromYear = Integer.valueOf(fYear);
                Integer toYear = Integer.valueOf(tYear);
                userManager.setUserState(userId, State.NONE);
                //TODO store data
                return MessageTemplate.approvedYear();
            } catch (NumberFormatException e) {
                return MessageTemplate.yearContainsNonDigit();
            }
        }
    }

    //TODO move out to separate class
    private String handleWaitQuestion(Message message, Integer userId) {
        String text = message.getText().trim().toLowerCase();
        if (agreeWords.contains(text)) {
            Question question = randomQuestionService.get();
            userManager.setUserState(userId, State.ANSWERING);
            userManager.setUserCurrentQuestion(userId, question);
            return MessageTemplate.formatQuestion(question);
        } else {
            userManager.setUserState(userId, State.NONE);
            return MessageTemplate.bye();
        }
    }
}
