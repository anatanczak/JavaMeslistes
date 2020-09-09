package com.anavidev.meslistes.datamodel;

import java.util.ArrayList;

public class TodoList {

    private int id;
    private String name;
    private boolean done;
    private boolean important;
    private String iconName;
    ArrayList<Item> items;

    public TodoList(int id, String name, boolean done, boolean important, String iconName, ArrayList<Item> items) {
        this.id = id;
        this.name = name;
        this.done = done;
        this.important = important;
        this.iconName = iconName;
        this.items = items;
    }

    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return name;
    }
}
