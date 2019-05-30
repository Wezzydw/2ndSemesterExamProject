package pkg2ndsemesterexamproject.gui.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ExceptionsHandler {

    public static void errorPopUpScreen(Exception ex) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Button btnOk = new Button("Ok");
                Label error = new Label("Error");
                Label errorMessage = new Label("" + ex.getMessage());
                error.setLayoutX(10);
                error.setLayoutY(10);
                errorMessage.setLayoutX(10);
                errorMessage.setLayoutY(30);
                btnOk.setLayoutX(100);
                btnOk.setLayoutY(125);

                Pane root2 = new Pane();
                root2.getChildren().addAll(btnOk, errorMessage, error);

                Scene scene = new Scene(root2, 300, 250);
                Stage primaryStage = new Stage();
                primaryStage.setScene(scene);
                btnOk.setOnAction((ActionEvent event)
                        -> {
                    primaryStage.close();
                });
                primaryStage.setAlwaysOnTop(true);
                primaryStage.show();
            }
        });
    }
}
