/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.gui.controller;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author Wezzy
 */
public class ExceptionsHandler
{

    public static void errorPopUpScreen(Exception ex)
    {
        Button btnOk = new Button("Ok");
        Label lblNoConnection = new Label("" + ex);
        lblNoConnection.setLayoutX(40);
        lblNoConnection.setLayoutY(80);
        btnOk.setLayoutX(100);
        btnOk.setLayoutY(125);

        Pane root2 = new Pane();
        root2.getChildren().addAll(btnOk, lblNoConnection);

        Scene scene = new Scene(root2, 300, 250);
        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        btnOk.setOnAction((ActionEvent event) ->
        {
            primaryStage.close();
        });
        primaryStage.setAlwaysOnTop(true);
        primaryStage.show();
    }
}
