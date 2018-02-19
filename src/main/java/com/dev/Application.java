package com.dev;

import com.dev.bot.QuestionsBot;
import com.dev.config.ConfigLoader;
import com.dev.config.ConfigLoadingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class Application {
    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new QuestionsBot(ConfigLoader.load("bot.json")));
        } catch (TelegramApiException | ConfigLoadingException e) {
            LOG.error(e.getMessage(), e);
        }
        LOG.info("bot has been started");
    }
}
