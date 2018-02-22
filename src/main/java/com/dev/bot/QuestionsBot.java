package com.dev.bot;

import com.dev.bot.command.ChangeYearsCommand;
import com.dev.bot.command.RandomQuestionCommand;
import com.dev.bot.command.StartCommand;
import com.dev.bot.message.MessageTemplate;
import com.dev.bot.route.Router;
import com.dev.domain.model.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class QuestionsBot extends TelegramLongPollingCommandBot {
    private static final Logger LOG = LoggerFactory.getLogger(QuestionsBot.class);
    private final Router router;
    private final Config config;

    public QuestionsBot(Config config) {
        super(config.getBotUsername());
        this.config = config;
        this.router = new Router();
        registerHandlers();
    }

    private void registerHandlers() {
        register(new RandomQuestionCommand());
        register(new StartCommand());
        register(new ChangeYearsCommand());
        registerDefaultAction((absSender, message) -> {
            SendMessage commandUnknownMessage = new SendMessage();
            commandUnknownMessage.setChatId(message.getChatId());
            commandUnknownMessage.setText(MessageTemplate.formatUnknownCmd(message.getText()));
            try {
                absSender.execute(commandUnknownMessage);
            } catch (TelegramApiException e) {
                LOG.error(e.getMessage(), e);
            }
        });
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            String msg = router.route(message);

            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(message.getChatId());
            sendMessage.setText(msg);
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }

    @Override
    public String getBotToken() {
        return config.getBotToken();
    }
}
