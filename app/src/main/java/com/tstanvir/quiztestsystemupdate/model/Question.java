package com.tstanvir.quiztestsystemupdate.model;

public class Question {
    private String question;
    private boolean ansToTheQues;

    public Question() {

    }

    public Question(String question, boolean ansToTheQues) {
        this.question = question;
        this.ansToTheQues = ansToTheQues;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public boolean isAnsToTheQues() {
        return ansToTheQues;
    }

    public void setAnsToTheQues(boolean ansToTheQues) {
        this.ansToTheQues = ansToTheQues;
    }
}
