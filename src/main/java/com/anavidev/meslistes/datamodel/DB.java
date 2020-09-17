package com.anavidev.meslistes.datamodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class DB {
    private static DB instance = new DB();
    private ObservableList<TodoList> myLists;
    private String url = "jdbc:mysql://localhost:3306/meslistes?serverTimezone=UTC";
    private Connection myConnection = null;

    //Public method to get instance of this singleton
    public static DB getInstance() {
        return instance;
    }


    //Make the constructor private to ensure that there is only one instance of this class
    private DB() {

        try{
            myConnection = DriverManager.getConnection(url,"root","123456");

        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public ObservableList<TodoList> getMyLists() {

        //temporary storage for lists without items
        ObservableList<TodoList> tempMyLists = FXCollections.observableArrayList();

        int tempListId = 0;

        if (myConnection != null) {
            try {
                Statement stm = myConnection.createStatement();
                ResultSet resultSet = stm.executeQuery("SELECT * FROM meslistes.list");

                while (resultSet.next()) {

                    int id = resultSet.getInt("list.id");
                        tempListId = id;

                        String name = resultSet.getNString("list.name");
                        boolean done = resultSet.getBoolean("list.done");
                        boolean important = resultSet.getBoolean("list.important");
                        String iconName = resultSet.getNString("icon_name");

                        TodoList newList = new TodoList(id, name, done, important, iconName, FXCollections.observableArrayList());
                        tempMyLists.add(newList);
                }

                stm.close();

                Statement itemstm = myConnection.createStatement();
                ResultSet itemResultSet = itemstm.executeQuery("SELECT * FROM meslistes.item");

                while (itemResultSet.next()) {

                    int itemId = itemResultSet.getInt("item.id");
                    String title = itemResultSet.getNString("item.title");
                    boolean itemDone = itemResultSet.getBoolean("item.done");
                    boolean itemImportant = itemResultSet.getBoolean("item.important");
                    boolean hasImage = itemResultSet.getBoolean("item.has_image");
                    String imageName = itemResultSet.getNString("item.image_name");
                    int listId = itemResultSet.getInt("item.list_id");
                    Item newItem = new Item(itemId, title, itemDone, itemImportant, hasImage, imageName, listId);


                    tempMyLists.forEach(todoList -> {
                        // System.out.println("List Id after being added to the array: " + todoList.getId() + "  " + todoList.getName());
                        if (newItem.getListId() == todoList.getId()) {
                            todoList.appendItem(newItem);
                        }
                    });

                }

              this.myLists = tempMyLists;
            } catch (Exception e){
                System.out.println(e.getMessage());
            }

        }
        return this.myLists;
    }

    public void addList(String name, boolean done, boolean important, String iconName){
        try{
            PreparedStatement stm = this.myConnection.prepareStatement("INSERT INTO list (name, done, important, icon_name) VALUES (?, ?, ?, ?)");
            stm.setString(1, name);
            stm.setBoolean(2, done);
            stm.setBoolean(3, important);
            stm.setString(4, iconName);

            stm.execute();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void addItem(String title, boolean done, boolean important, boolean hasImage, int listId){
        try{
            PreparedStatement stm = this.myConnection.prepareStatement("INSERT INTO item (title, done, important, has_image, list_id) VALUES (?, ?, ?, ?, ?)");
            stm.setString(1, title);
            stm.setBoolean(2, done);
            stm.setBoolean(3, important);
            stm.setBoolean(4, hasImage);
            stm.setInt(5, listId);

            stm.execute();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteItem(int id){
        try{
            PreparedStatement stm = this.myConnection.prepareStatement("DELETE FROM item WHERE id = " + id + ";");
            stm.execute();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateItem(int id, String newTitle){
        try{
            PreparedStatement stm = this.myConnection.prepareStatement("UPDATE item SET title = \"" + newTitle + "\" WHERE id = " + id + ";");
            stm.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateList(int id, String newName, String newIcon){
        try{
            PreparedStatement stm = this.myConnection.prepareStatement("UPDATE list SET name = \"" + newName + "\", icon_name = \"" + newIcon + "\" WHERE id = " + id + ";");
            stm.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

