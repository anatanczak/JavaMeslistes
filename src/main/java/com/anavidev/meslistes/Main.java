package com.anavidev.meslistes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        String url = "jdbc:mysql://localhost:3306/meslistes?serverTimezone=UTC";

        Connection myConnection = null;
        try{
            myConnection = DriverManager.getConnection(url,"root","123456");
            System.out.println("ok");

            Statement stm = myConnection.createStatement();
            ResultSet resultSet = stm.executeQuery("SELECT * FROM LIST");

            while (resultSet.next()){
                int id = resultSet.getInt("id");
                System.out.println(id);
            }


        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));


        primaryStage.setTitle("Meslistes");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
