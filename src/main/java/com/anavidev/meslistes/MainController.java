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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.FileNotFoundException;
import java.io.IOException;

public class MainController {
    public ListView <TodoList> todolistView;
    public ListView <Item> itemsView;
    public GridPane mainGridPane;
    public Label listLabel;
    public TextField itemTextField;
    private int listId = 0;

    private ObservableList<TodoList> lists;




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
        //todolistView.setCellFactory(param -> new TodoListCell());

        todolistView.setCellFactory(new Callback<ListView<TodoList>, ListCell<TodoList>>() {
            @Override
            public ListCell<TodoList> call(ListView<TodoList> param) {
                try {
                    ListCell<TodoList> cell = new TodoListCell();
                    return cell;
                } catch (FileNotFoundException e){
                    ListCell<TodoList> cell = new ListCell();
                    return cell;
                }

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
                setStyle("-fx-background-color: #f5f2f2; -fx-text-fill: black;");
            }

        }
    }


    /* TODOLIST CELL*/



    static class TodoListCell extends ListCell<TodoList> {
        HBox hbox = new HBox();
        Image image;
        ImageView imageView;
        Label label = new Label("");
        Pane pane = new Pane();

        public TodoListCell() throws FileNotFoundException {
            super();

            image = new Image(getClass().getResourceAsStream("assets/images/plus-icon.png"));
            imageView = new ImageView(image);
            hbox.getChildren().addAll(imageView, label, pane);
            HBox.setHgrow(pane, Priority.ALWAYS);
            hbox.setAlignment(Pos.CENTER_LEFT);
            hbox.setSpacing(10);
            imageView.setFitHeight(25);
            imageView.setPreserveRatio(true);
            label.getStyleClass().add("item-cell-label");
        }

        @Override
        protected void updateItem(TodoList todoList, boolean empty) {
            super.updateItem(todoList, empty);
            setText(null);
            setGraphic(null);

            if (todoList != null && !empty) {
                label.setText(todoList.getName().toUpperCase());
                setGraphic(hbox);
                setStyle("-fx-background-color: white;");
                Image newIcon = new Image(getClass().getResourceAsStream("assets/images/listicons/gray/" + todoList.getIconName()));
                imageView.setImage(newIcon);

            }
            if(isSelected()){
                setStyle("-fx-background-color: #f5f2f2; -fx-text-fill: black;");
            }

        }
    }


}
