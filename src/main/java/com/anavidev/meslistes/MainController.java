package com.anavidev.meslistes;

import com.anavidev.meslistes.datamodel.DB;
import com.anavidev.meslistes.datamodel.Item;
import com.anavidev.meslistes.datamodel.TodoList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

    private ObservableList<TodoList> lists;


    static class ItemCell extends ListCell<Item> {
        HBox hbox = new HBox();
        Label label = new Label("");
        Pane pane = new Pane();
        Button button = new Button("");

        public ItemCell() {
            super();

            hbox.getChildren().addAll(label, pane, button);
            HBox.setHgrow(pane, Priority.ALWAYS);
            hbox.setAlignment(Pos.CENTER_LEFT);
           // button.setOnAction(event -> getListView().getItems().remove(getItem()));
            button.getStyleClass().add("trash-button");
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

        this.lists = DB.getInstance().getMyLists();

        //Listen to the selection of the cell in listView and display the correspondent items in the itemsView
       todolistView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TodoList>() {
           @Override
           public void changed(ObservableValue<? extends TodoList> observable, TodoList oldValue, TodoList newValue) {
               if (newValue != null){
                   TodoList todoList = todolistView.getSelectionModel().getSelectedItem();
                   itemsView.getItems().setAll(todoList.getItems());
               }
           }
       });

        todolistView.setItems(lists);
        todolistView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        todolistView.getSelectionModel().selectFirst();

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
        itemsView.setCellFactory(param -> new ItemCell());

 /*      itemsView.setCellFactory(new Callback<ListView<Item>, ListCell<Item>>() {
           @Override
           public ListCell<Item> call(ListView<Item> param) {


               ListCell<Item> cell = new ListCell<>(){
                   @Override
                   protected void updateItem(Item item, boolean empty) {
                       super.updateItem(item, empty);
                       if(empty) {
                           setText(null);

                       } else {
                           setText(item.getTitle());
                           setStyle("-fx-background-color: white;");
                           if(isSelected()){
                               setStyle("-fx-background-color: #F0D6E2;");
                           }
                       }

                   }
               };

               return cell;
           }
       });
       */

    }


    public void plusButtonClicked() throws IOException {

        Stage stage = (Stage) mainGridPane.getScene().getWindow();

        Parent root = FXMLLoader.load(getClass().getResource("addNewList.fxml"));

        stage.setTitle("Meslistes");
        stage.setScene(new Scene(root));
        stage.show();

    }

}
