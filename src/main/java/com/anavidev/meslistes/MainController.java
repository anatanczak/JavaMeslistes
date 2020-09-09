package com.anavidev.meslistes;

import com.anavidev.meslistes.datamodel.Item;
import com.anavidev.meslistes.datamodel.TodoList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class MainController {
    public ListView <TodoList> todolistView;
    public ListView <Item> itemsView;
    public GridPane mainGridPane;

    private ObservableList<TodoList> lists;



   public void initialize(){
      Item item1 = new Item(1, "bread", false, false, false, "");
        Item item2 = new Item(1, "milk", false, false, false, "");
        Item item3 = new Item(1, "butter", false, false, false, "");

          ArrayList items = new ArrayList();
        items.add(item1);
        items.add(item2);
        items.add(item3);

        TodoList shoppingList = new TodoList(1, "Shopping list", false, false, "cart-icon.svg", items);

        Item item4 = new Item(1, "passport", false, false, false, "");
        Item item5 = new Item(1, "charger", false, false, false, "");
        Item item6 = new Item(1, "credit card", false, false, false, "");

       ArrayList travelItems = new ArrayList();
       travelItems.add(item4);
       travelItems.add(item5);
       travelItems.add(item6);

        TodoList travelList = new TodoList(1, "Travel", false, false, "plane-icon.svg", travelItems);

       ObservableList<TodoList> newLists = FXCollections.observableArrayList();
        newLists.add(shoppingList);
        newLists.add(travelList);
        this.lists = newLists;

        //Listen to the selection of the cell in listView and dispay the correspondent items in the itemsView
       todolistView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TodoList>() {
           @Override
           public void changed(ObservableValue<? extends TodoList> observable, TodoList oldValue, TodoList newValue) {
               if (newValue != null){
                   TodoList todoList = todolistView.getSelectionModel().getSelectedItem();
                   itemsView.getItems().setAll(todoList.getItems());
               }
           }
       });

        todolistView.getItems().setAll(lists);
        todolistView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        todolistView.getSelectionModel().selectFirst();
    }


    public void plusButtonClicked() throws IOException {

        Stage stage = (Stage) mainGridPane.getScene().getWindow();

        Parent root = FXMLLoader.load(getClass().getResource("addNewList.fxml"));

        stage.setTitle("Meslistes");
        stage.setScene(new Scene(root));
        stage.show();

    }

}
