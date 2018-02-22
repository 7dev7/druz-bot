package com.dev.bot.command;

import com.dev.bot.message.MessageTemplate;
import com.dev.domain.model.Question;
import com.dev.domain.repository.State;
import com.dev.service.RandomQuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;

public class RandomQuestionCommand extends GenericCommand {
    private static final Logger LOG = LoggerFactory.getLogger(RandomQuestionCommand.class);
    private final RandomQuestionService randomQuestionService;

    public RandomQuestionCommand() {
        super("random_question", "get random question");
        this.randomQuestionService = new RandomQuestionService();
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        Integer userId = user.getId();
        if (State.ANSWERING.equals(userManager.getUserState(userId))) {
            forbidNextQuestion(absSender, chat, userId);
            return;
        }
        LOG.info("changed state to ANSWERING for user " + userId);
        Question question = randomQuestionService.get(userManager.getYearsForUser(userId));
        if (question.getQuestion() == null) {
            sendMessage(absSender, chat, MessageTemplate.noQuestion());
            return;
        }
        userManager.setUserState(userId, State.ANSWERING);
        userManager.setUserCurrentQuestion(userId, question);
        sendMessage(absSender, chat, MessageTemplate.formatQuestion(question));
    }
}
