package com.example.notemelab2;

public class Note {
    private int id;
    private String title;
    private String subtitle;
    private String text;
    private String color;

    public Note(int id, String title, String subtitle, String text, String color) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.text = text;
        this.color = color;
    }

    public int getId() { return id; }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getText() {
        return text;
    }

    public String getColor() {
        return color;
    }
}
