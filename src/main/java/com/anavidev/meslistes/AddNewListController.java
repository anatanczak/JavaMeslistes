package com.anavidev.meslistes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AddNewListController {
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


    public void cancelAction() throws IOException {
        Stage stage = (Stage) mainAnchorPane.getScene().getWindow();

       Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));

        stage.setTitle("Meslistes");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
