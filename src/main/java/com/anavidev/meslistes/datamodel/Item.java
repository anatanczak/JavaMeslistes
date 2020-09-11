package com.anavidev.meslistes.datamodel;

public class Item {
    private int id;
    private String title;
    private  boolean done;
    private boolean important;
    private boolean hasImage;
    private String imageName;
    private int listId;

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean isImportant() {
        return important;
    }

    public void setImportant(boolean important) {
        this.important = important;
    }

    public boolean isHasImage() {
        return hasImage;
    }

    public void setHasImage(boolean hasImage) {
        this.hasImage = hasImage;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }



    public Item(int id, String title, boolean done, boolean important, boolean hasImage, String imageName, int listId) {
        this.id = id;
        this.title = title;
        this.done = done;
        this.important = important;
        this.hasImage = hasImage;
        this.imageName = imageName;
        this.listId = listId;
    }

    @Override
    public String toString() {
        return title;
    }
}
