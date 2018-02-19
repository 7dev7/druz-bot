package com.dev.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class ConfigLoader {

    public static Config load(String fileName) throws ConfigLoadingException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File(fileName), Config.class);
        } catch (IOException e) {
            throw new ConfigLoadingException("Exception during config loading", e);
        }
    }
}
