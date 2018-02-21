package com.dev;

import com.dev.bot.QuestionsBot;
import com.dev.domain.model.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class BotApplication {
    private static final Logger LOG = LoggerFactory.getLogger(BotApplication.class);

    public void run(Config config) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new QuestionsBot(config));
        } catch (TelegramApiException e) {
            LOG.error("could not connect to bot with current config: " + e.getMessage(), e);
            throw new IllegalArgumentException("could not connect to bot with current config: " + e.getMessage(), e);
        }
        LOG.info("bot has been started");
    }
}
