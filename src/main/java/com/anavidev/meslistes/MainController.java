package com.anavidev.meslistes;

import com.anavidev.meslistes.datamodel.DB;
import com.anavidev.meslistes.datamodel.Item;
import com.anavidev.meslistes.datamodel.TodoList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;

public class MainController {
    public ListView <TodoList> todolistView;
    public ListView <Item> itemsView;
    public GridPane mainGridPane;
    public Label listLabel;
    public TextField itemTextField;
    private int listId = 0;

    private ObservableList<TodoList> lists;




    static class ItemCell extends ListCell<Item> {
        HBox hbox = new HBox();
        Label label = new Label("");
        Pane pane = new Pane();
        Button button = new Button("");
        Button editButton = new Button("");

        public ItemCell() {
            super();

            hbox.getChildren().addAll(label, pane, editButton, button);
            HBox.setHgrow(pane, Priority.ALWAYS);
            hbox.setAlignment(Pos.CENTER_LEFT);
            hbox.setSpacing(2);

            button.getStyleClass().add("trash-button");
            editButton.getStyleClass().add("edit-button");

            label.getStyleClass().add("item-cell-label");
        }

        @Override
        protected void updateItem(Item item, boolean empty) {
            super.updateItem(item, empty);
            setText(null);
            setGraphic(null);

            if (item != null && !empty) {
                label.setText(item.getTitle());
                setGraphic(hbox);
                setStyle("-fx-background-color: white;");
            } else {
                setStyle("-fx-background-color: transparent;");
            }
            if(isSelected()){
                setStyle("-fx-background-color: #F0D6E2; -fx-text-fill: black;");
            }

        }
    }


   public void initialize(){
        itemsView.getItems().addListener(new ListChangeListener<Item>() {
            @Override
            public void onChanged(Change<? extends Item> c) {
/*                lists = DB.getInstance().getMyLists();
                System.out.println("changed item");
                itemsView.refresh();
                todolistView.refresh();*/
            }
        });

        todolistView.getItems().addListener(new ListChangeListener<TodoList>() {
            @Override
            public void onChanged(Change<? extends TodoList> c) {
//                System.out.println("changed list");
            }
        });

        //Listen to the selection of the cell in listView and display the correspondent items in the itemsView
       todolistView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TodoList>() {
           @Override
           public void changed(ObservableValue<? extends TodoList> observable, TodoList oldValue, TodoList newValue) {
               if (newValue != null){
                  TodoList selectedTodoList = todolistView.getSelectionModel().getSelectedItem();
                 /*   itemsView.getItems().setAll(todoList.getItems());
                   listLabel.setText(newValue.getName().toUpperCase());*/
                   listId = newValue.getId();
                   lists = DB.getInstance().getMyLists();
                   lists.forEach( todoList -> {
                       if (selectedTodoList.getId() == todoList.getId()){
                           itemsView.getItems().setAll(todoList.getItems());
                           listLabel.setText(todoList.getName().toUpperCase());
                       }
                   });
               }
           }
       });

       fillInLists();
       todolistView.getSelectionModel().selectFirst();
    }


    public void plusButtonClicked() throws IOException {

        Stage stage = (Stage) mainGridPane.getScene().getWindow();

        Parent root = FXMLLoader.load(getClass().getResource("addNewList.fxml"));

        stage.setTitle("Meslistes");
        stage.setScene(new Scene(root));
        stage.show();

    }

    public void enterPressedHandler(KeyEvent keyEvent) {
        String title = itemTextField.getText();
        boolean textIsEmpty = title.isEmpty() ||  title.trim().isEmpty();
        if (keyEvent.getCode().equals(KeyCode.ENTER) && textIsEmpty == false){
            System.out.println(title);

            DB.getInstance().addItem(title, false, false, false, listId);
            fillInLists();
            itemTextField.setText("");
        }
    }

    public void fillInLists(){

        this.lists = DB.getInstance().getMyLists();


        todolistView.setItems(lists);
        todolistView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        todolistView.setCellFactory(new Callback<ListView<TodoList>, ListCell<TodoList>>() {
            @Override
            public ListCell<TodoList> call(ListView<TodoList> param) {
                ListCell<TodoList> cell = new ListCell<>(){
                    @Override
                    protected void updateItem(TodoList item, boolean empty) {
                        super.updateItem(item, empty);
                        if(empty) {
                            setText(null);
                        } else {
                            setText(item.getName());
                        }
                    }
                };
                return cell;
            }
        });
        //itemsView.setCellFactory(param -> new ItemCell());
        itemsView.setCellFactory(new Callback<ListView<Item>, ListCell<Item>>() {
            @Override
            public ListCell<Item> call(ListView<Item> param) {
                ItemCell cell = new ItemCell();
                cell.button.setOnAction(event -> {
                   Item thisItem = cell.getItem();
                    DB.getInstance().deleteItem(thisItem.getId());
                    itemsView.getItems().remove(thisItem);
                    System.out.println("List id is: " + listId);
/*                    lists.forEach(todoList -> {
                        todoList.getItems().forEach(item -> {
                            if (thisItem.getId() == item.getId()){
                                todoList.getItems().remove(item);
                            }
                        });
                    });*/

                });
                return cell;
            }
        });
        //selects the current list in todoListView
        this.lists.forEach(todoList -> {
            if(todoList.getId() == listId){
                todolistView.getSelectionModel().select(todoList);
            }
        });

    }

}
