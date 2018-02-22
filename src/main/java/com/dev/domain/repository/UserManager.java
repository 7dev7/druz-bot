package com.dev.domain.repository;

import com.dev.domain.model.Question;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private static final Map<Integer, State> states = new HashMap<>();
    private static final Map<Integer, Question> currentQuestions = new HashMap<>();
    private static final Map<Integer, Pair<Integer, Integer>> userToYears = new HashMap<>();
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
        states.put(userId, state);
    }

    public State getUserState(Integer userId) {
        State state = states.get(userId);
        return state == null ? State.NONE : state;
    }

    public void setUserCurrentQuestion(Integer userId, Question question) {
        currentQuestions.put(userId, question);
    }

    public Question getUserCurrentQuestion(Integer userId) {
        return currentQuestions.get(userId);
    }

    public void setYearsForUser(Integer userId, Pair<Integer, Integer> years) {
        userToYears.put(userId, years);
    }

    public Pair<Integer, Integer> getYearsForUser(Integer userId) {
        Pair<Integer, Integer> years = userToYears.get(userId);
        return years == null ? Pair.of(null, null) : years;
    }
}
