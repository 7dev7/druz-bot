package com.dev.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Question {
    @JsonProperty("QuestionId")
    private Long questionId;

    @JsonProperty("Question")
    private String question;

    @JsonProperty("Answer")
    private String answer;

    @JsonProperty("PassCriteria")
    private String passCriteria;

    @JsonProperty("Authors")
    private String authors;

    @JsonProperty("Sources")
    private String sources;

    @JsonProperty("Comments")
    private String comments;

    @JsonProperty("tournamentTitle")
    private String tournamentTitle;

    public Question() {
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionId=" + questionId +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", passCriteria='" + passCriteria + '\'' +
                ", authors='" + authors + '\'' +
                ", sources='" + sources + '\'' +
                ", comments='" + comments + '\'' +
                ", tournamentTitle='" + tournamentTitle + '\'' +
                '}';
    }
}
