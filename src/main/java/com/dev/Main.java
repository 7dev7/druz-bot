package com.dev;

import com.dev.domain.model.config.Config;

public class Main {
    public static void main(String[] args) {
        validateArgs(args);
        BotApplication botApplication = new BotApplication();
        botApplication.run(new Config(args[0], args[1]));
    }

    private static void validateArgs(String[] args) {
        if (args.length < 2 || args[0] == null || args[1] == null) {
            throw new IllegalArgumentException("BotUsername and BotToken should be filled");
        }
    }
}
