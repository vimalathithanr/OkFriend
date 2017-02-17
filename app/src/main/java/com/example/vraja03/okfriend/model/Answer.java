package com.example.vraja03.okfriend.model;

/**
 * Created by VRAJA03 on 2/16/2017.
 */

public class Answer {
    private int questionId;
    private int answer;
    private int importance;
    private int[] acceptableAnswers;

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }

    public int[] getAcceptableAnswers() {
        return acceptableAnswers;
    }

    public void setAcceptableAnswers(int[] acceptableAnswers) {
        this.acceptableAnswers = acceptableAnswers;
    }

}
