package com.dev.domain.repository;

import com.dev.domain.model.Question;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserManager {
    //TODO move to redis
    private static final Map<String, String> map = new HashMap<>();
    private static final String STATE_PREFIX = "/state/";
    private static final String QUESTION_PREFIX = "/q/";
    private static volatile UserManager instance;

    public static UserManager getInstance() {
        if (instance == null) {
            synchronized (UserManager.class) {
                if (instance == null) {
                    instance = new UserManager();
                }
            }
        }
        return instance;
    }

    public void setUserState(Integer userId, State state) {
        map.put(STATE_PREFIX + userId, state.name());
    }

    public State getUserState(Integer userId) {
        String state = map.get(STATE_PREFIX + userId);
        return state == null ? State.NONE : State.valueOf(state);
    }

    public void setUserCurrentQuestion(Integer userId, Question question) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            map.put(QUESTION_PREFIX + userId, mapper.writeValueAsString(question));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public Question getUserCurrentQuestion(Integer userId) {
        String questionContent = map.get(QUESTION_PREFIX + userId);
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(questionContent, Question.class);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
