package com.dev;

import com.dev.bot.QuestionsBot;
import com.dev.domain.model.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class Application {
    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        String botUsername = args[0];
        String botToken = args[1];

        if (botUsername == null ||
                botToken == null) {
            throw new IllegalArgumentException("BotUsername and BotToken should be filled");
        }

        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            Config config = new Config(botUsername, botToken);
            telegramBotsApi.registerBot(new QuestionsBot(config));
        } catch (TelegramApiException e) {
            LOG.error(e.getMessage(), e);
        }
        LOG.info("bot has been started");
    }
}
