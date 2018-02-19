package com.dev.config;

import lombok.Data;

@Data
public class Config {
    private String botUsername;
    private String botToken;

    public Config() {
    }
}
