package com.projects.marcoscavalcante.deloapp.Model;

public enum Category {

    WOMEN_FOOTWEAR("Women's Footwear"),
    WOMEN_CASUALWEAR("Women's Casualwear"),
    WOMEN_FORMALWEAR("Women's Formalwear"),
    MEN_FOOTWEAR("Men's Footwear"),
    MEN_CASUALWEAR("Men's Casualwear"),
    MEN_FORMALWEAR("Men's Formalwear");


    private final String text;


    Category(final String text) {
        this.text = text;
    }


    @Override
    public String toString() {
        return text;
    }
}
