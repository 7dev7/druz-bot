package com.dev.domain.model.config;

import lombok.Data;

@Data
public class Config {
    private String botUsername;
    private String botToken;

    public Config() {
    }

    public Config(String botUsername, String botToken) {
        this.botUsername = botUsername;
        this.botToken = botToken;
    }
}
