package com.ahmedapps.walkthrough.items;

public class ItemInfo {

    private final int id;

    private final String image;
    private final String title;
    private final String text;
    private final String text_two;

    private int likes;

    public ItemInfo(int id, String image, String title,
                    String text, String text_two, int likes) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.text = text;
        this.text_two = text_two;
        this.likes = likes;
    }

    public int getId() {
        return id;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getText_two() {
        return text_two;
    }


}