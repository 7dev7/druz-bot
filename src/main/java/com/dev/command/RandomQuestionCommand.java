package com.dev.command;

import com.dev.model.Question;
import com.dev.service.RandomQuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class RandomQuestionCommand extends BotCommand {
    private static final Logger LOG = LoggerFactory.getLogger(RandomQuestionCommand.class);
    private final RandomQuestionService randomQuestionService;

    public RandomQuestionCommand() {
        super("random_question", "get random question");
        this.randomQuestionService = new RandomQuestionService();
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        Question question = randomQuestionService.retrieve();
        SendMessage message = new SendMessage();
        message.setChatId(chat.getId());
        message.setText(formatQuestion(question));
        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private String formatQuestion(Question question) {
        return String.format("%s \n\n%s", question.getQuestion().replaceAll("\\n", " "), question.getTournamentTitle());
    }
}
