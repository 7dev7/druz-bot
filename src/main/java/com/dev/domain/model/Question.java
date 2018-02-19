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

    @JsonProperty("tourTitle")
    private String tourTitle;

    @JsonProperty("tournamentTitle")
    private String tournamentTitle;

    @JsonProperty("tourPlayedAt")
    private String tourPlayedAt;

    @JsonProperty("tournamentPlayedAt")
    private String tournamentPlayedAt;

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
                ", tourTitle='" + tourTitle + '\'' +
                ", tournamentTitle='" + tournamentTitle + '\'' +
                ", tourPlayedAt='" + tourPlayedAt + '\'' +
                ", tournamentPlayedAt='" + tournamentPlayedAt + '\'' +
                '}';
    }
}
