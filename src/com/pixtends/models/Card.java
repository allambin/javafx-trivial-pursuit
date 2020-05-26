package com.pixtends.models;

public class Card {
    private int id;
    private String question;
    private String answer;
    private CardType type;

    public Card(String question, String answer, CardType type) {
        this.question = question;
        this.answer = answer;
        this.type = type;
    }

    public Card(int id, String question, String answer, CardType type) {
        this(question, answer, type);
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public CardType getType() {
        return type;
    }

    public String getTypeName() {
        return type.getName();
    }

    public int getId() {
        return id;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setType(CardType type) {
        this.type = type;
    }
}
