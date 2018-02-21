package com.dev.bot.command;

import com.dev.bot.message.MessageTemplate;
import com.dev.domain.model.Question;
import com.dev.domain.repository.State;
import com.dev.domain.repository.UserManager;
import com.dev.service.RandomQuestionService;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;

public class RandomQuestionCommand extends GenericCommand {
    private final RandomQuestionService randomQuestionService;
    private final UserManager userManager;

    public RandomQuestionCommand() {
        super("random_question", "get random question");
        this.randomQuestionService = new RandomQuestionService();
        this.userManager = UserManager.getInstance();
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        if (State.ANSWERING.equals(userManager.getUserState(user.getId()))) {
            Question currentQuestion = userManager.getUserCurrentQuestion(user.getId());
            sendMessage(absSender, chat, MessageTemplate.formatProhibitedNextQuestion(currentQuestion));
            return;
        }
        Question question = randomQuestionService.retrieve();
        userManager.setUserState(user.getId(), State.ANSWERING);
        userManager.setUserCurrentQuestion(user.getId(), question);
        sendMessage(absSender, chat, MessageTemplate.formatQuestion(question));
    }
}
