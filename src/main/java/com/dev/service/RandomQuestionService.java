package com.dev.service;

import com.dev.domain.model.Question;
import com.dev.service.remote.DataLoader;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.IOException;

public class RandomQuestionService {
    private static final String URL = "https://db.chgk.info/xml/random/types1/from_2014-01-01/limit1";

    public Question get() {
        XmlMapper mapper = new XmlMapper();
        try {
            return mapper.readValue(loadXmlData(), Question.class);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private String loadXmlData() {
        String retrieve = DataLoader.retrieve(URL);
        //dirty hack to remove root tag
        return retrieve.substring(46, retrieve.length() - 9);
    }
}
