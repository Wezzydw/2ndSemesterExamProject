/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.gui.controller;

import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pkg2ndsemesterexamproject.be.Department;
import pkg2ndsemesterexamproject.gui.Model;

/**
 * FXML Controller class
 *
 * @author andreas
 */
public class DepartmentScreenViewController implements Initializable {
    
    private Department currentDepartment;
    private Model model;
    
    @FXML
    private ComboBox<?> comboBox;
    @FXML
    private Label lblDate;
    @FXML
    private JFXTextField txtSearchfield;
    @FXML
    private AnchorPane departmentAnchorPane;
    @FXML
    private Label lblText;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private BorderPane borderPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            model = new Model();
        } catch (IOException ex) {
            Logger.getLogger(DepartmentScreenViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        borderPane.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
//                model.extentAnchorPaneX(departmentAnchorPane, borderPane);
//                System.out.println(borderPane.getWidth() + " " + departmentAnchorPane.getWidth());
//
//                model.extentAnchorPaneY(departmentAnchorPane);
                //model.placeOrderInUI(departmentAnchorPane);

                model.msOnDepartmentView(departmentAnchorPane, borderPane);
            }
            
        });
        
        LocalDate date = LocalDate.now();
        lblDate.setText(date.toString());
//        Stage stage = (Stage) borderPane.getScene().getWindow();
//        stage.setFullScreen(true);
        model.msOnDepartmentView(departmentAnchorPane, borderPane);
        functionThatUpdatedGUIEvery5Seconds();
        
        departmentAnchorPane.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println("Olde: " + oldValue + " New: " + newValue);
            }
        });

        //tmpLoop();
    }
    
    @FXML
    private void searchBar(KeyEvent event) {
    }
    
    public void setDepartment(Department department) {
        lblText.setText(department.getName());
    }
    
    public void functionThatUpdatedGUIEvery5Seconds() {
        
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(DepartmentScreenViewController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            //Insert metoder her
                            model.msOnDepartmentView(departmentAnchorPane, borderPane);
                            System.out.println("Tester");
                        }
                    });
                }
                
            }
        });
        t.setDaemon(true);
        t.start();
    }
    
}
