package com.anavidev.meslistes;

import com.anavidev.meslistes.datamodel.DB;
import com.anavidev.meslistes.datamodel.TodoList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class AddNewListController {
    public boolean listIsBeingEdited = false;
    public TodoList todoList;
    public Button deleteButton = new Button();

    public TextField listTextField;
    public Button checkIcon;
    public Button starIcon;
    public Button planeIcon;
    public Button cartIcon;
    public Button houseIcon;
    public Button clothesIcon;
    public Button giftIcon;
    public Button bagIcon;
    public Button bulbIcon;
    public Button sportIcon;
    public Button cookingIcon;
    public Button bookIcon;
    public Button okButton;
    public Button cancelButton;
    public AnchorPane mainAnchorPane;
    public VBox container;
    private String iconName = "";


    public  void  initialize(){

        if (listIsBeingEdited){
            okButton.setDisable(false);

        } else {
            okButton.setDisable(true);
        }
    }

    public void setListTextField(TodoList todoList){
        this.todoList = todoList;
        this.listTextField.setText(todoList.getName());
    }

    public void okCancelAction(javafx.event.ActionEvent event) throws IOException{
        if (event.getSource().equals(okButton)){
            String name = listTextField.getText();

           //TODO: Add if statement to handle editing and adding
            if(listIsBeingEdited){
                if(iconName.isEmpty()){
                    iconName = todoList.getIconName();
                }
                DB.getInstance().updateList(todoList.getId(), name, iconName);
            } else {
                if (iconName.isEmpty()){
                    iconName = "check-gray.png";
                }
                DB.getInstance().addList(name, false, false, iconName);
            }
        }

        Stage stage = (Stage) mainAnchorPane.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        stage.setTitle("Meslistes");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void handleKeyReleased(){
        String text = listTextField.getText();
        boolean disableOkButton = text.isEmpty() || text.trim().isEmpty();
        okButton.setDisable(disableOkButton);
    }

    public void iconAction(javafx.event.ActionEvent event) throws IOException{
        if (event.getSource().equals(starIcon)){
            iconName = "star-gray.png";
        } else if (event.getSource().equals(planeIcon)){
            iconName = "plane-gray.png";
        } else if (event.getSource().equals(cartIcon)){
            iconName = "shopping-cart.png";
        } else if (event.getSource().equals(houseIcon)){
            iconName = "house-gray.png";
        } else if (event.getSource().equals(clothesIcon)){
            iconName = "clothes-gray.png";
        } else if (event.getSource().equals(giftIcon)){
            iconName = "gift-gray.png";
        } else if (event.getSource().equals(bagIcon)){
            iconName = "bag-gray.png";
        } else if (event.getSource().equals(bulbIcon)){
            iconName = "light-bulb-gray.png";
        } else if (event.getSource().equals(sportIcon)){
            iconName = "sport-gray.png";
        } else if (event.getSource().equals(cookingIcon)){
            iconName = "cooking-gray.png";
        } else if (event.getSource().equals(bookIcon)){
            iconName = "book-gray.png";
        }  else if (event.getSource().equals(checkIcon)){
            iconName = "check-gray.png";
        }

        System.out.println(iconName);
    }
}
