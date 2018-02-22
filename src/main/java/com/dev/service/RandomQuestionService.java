package com.dev.service;

import com.dev.domain.model.Question;
import com.dev.service.remote.DataLoader;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class RandomQuestionService {
    private static final Logger LOG = LoggerFactory.getLogger(RandomQuestionService.class);
    private static final String BASE_URL = "https://db.chgk.info/xml/random/types1/";

    public Question get(Pair<Integer, Integer> years) {
        String url = buildUrl(years);
        LOG.info("sent request to " + url);
        XmlMapper mapper = new XmlMapper();
        try {
            return mapper.readValue(loadXmlData(url), Question.class);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private String buildUrl(Pair<Integer, Integer> years) {
        String from = "";
        String to = "";
        Integer left = years.getLeft();
        Integer right = years.getRight();
        if (left != null) {
            from = "from_" + left + "-01-01/";
        }
        if (right != null) {
            to = "to_" + right + "-01-01/";
        }
        return BASE_URL + from + to + "limit1/";
    }

    private String loadXmlData(String url) {
        String retrieve = DataLoader.retrieve(url);
        //dirty hack to remove root tag
        return retrieve.substring(46, retrieve.length() - 9);
    }
}
