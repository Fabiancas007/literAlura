package com.alura.library.model;

public enum Language {
    SPANISH("es"),
    ENGLISH("en"),
    FRENCH("fr"),
    PORTUGUESE("pt");

    private String abbrevation;

    Language(String abbrevation){
        this.abbrevation = abbrevation;
    }

    public static Language fromString(String text){
        for ( Language langu: Language.values()) {
            if (langu.abbrevation.equalsIgnoreCase(text)) {
                return langu;
            }
        }
        throw new IllegalArgumentException("No se encontró este lenguaje " + text);
    }

}
