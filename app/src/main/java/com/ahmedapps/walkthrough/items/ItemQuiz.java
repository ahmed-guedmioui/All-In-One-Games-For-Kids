package com.ahmedapps.walkthrough.items;

public class ItemQuiz {

    private final String title;
    private final String question;


    private final String answer_1;
    private final String answer_2;
    private final String answer_3;

    private final int correct;

    public ItemQuiz(String title, String question, String answer_1, String answer_2, String answer_3, int correct) {
        this.title = title;
        this.question = question;
        this.answer_1 = answer_1;
        this.answer_2 = answer_2;
        this.answer_3 = answer_3;
        this.correct = correct;
    }

    public String getTitle() {
        return title;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer_1() {
        return answer_1;
    }

    public String getAnswer_2() {
        return answer_2;
    }

    public String getAnswer_3() {
        return answer_3;
    }

    public int getCorrect() {
        return correct;
    }
}