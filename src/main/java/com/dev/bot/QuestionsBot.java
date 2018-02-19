package com.dev.bot;

import com.dev.command.RandomQuestionCommand;
import com.dev.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class QuestionsBot extends TelegramLongPollingCommandBot {
    private static final Logger LOG = LoggerFactory.getLogger(QuestionsBot.class);
    private Config config;

    public QuestionsBot(Config config) {
        super(config.getBotUsername());
        this.config = config;
        registerHandlers();
    }

    private void registerHandlers() {
        register(new RandomQuestionCommand());
        registerDefaultAction((absSender, message) -> {
            SendMessage commandUnknownMessage = new SendMessage();
            commandUnknownMessage.setChatId(message.getChatId());
            commandUnknownMessage.setText(String.format("The command '%s' is not known by this bot", message.getText()));
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
            if (message.hasText()) {
                SendMessage echoMessage = new SendMessage();
                echoMessage.setChatId(message.getChatId());
                echoMessage.setText("Hey here is your message:\n" + message.getText());
                try {
                    execute(echoMessage);
                } catch (TelegramApiException e) {
                    LOG.error(e.getMessage(), e);
                }
            }
        }
    }

    @Override
    public String getBotToken() {
        return config.getBotToken();
    }
}
