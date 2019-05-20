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
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import pkg2ndsemesterexamproject.be.Department;
import pkg2ndsemesterexamproject.bll.ISortStrategy;
import pkg2ndsemesterexamproject.bll.SortCustomer;
import pkg2ndsemesterexamproject.bll.SortEndDate;
import pkg2ndsemesterexamproject.bll.SortOrderId;
import pkg2ndsemesterexamproject.bll.SortReady;
import pkg2ndsemesterexamproject.bll.SortStartDate;
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
    private ComboBox<ISortStrategy> comboBox;
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
    private ISortStrategy sortStrategy;

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

        LocalDate date = LocalDate.now();
        lblDate.setText(date.format(DateTimeFormatter.ofPattern("d/MM/YYYY")));

        model.msOnDepartmentView(departmentAnchorPane, borderPane, sortStrategy);
        functionThatUpdatedGUIEvery5Seconds();
        initListeners();
        //tmpLoop();
        sortStrategy = new SortOrderId();
        comboBox.getItems().add(new SortCustomer());
        comboBox.getItems().add(new SortEndDate());
        comboBox.getItems().add(new SortOrderId());
        comboBox.getItems().add(new SortReady());
        comboBox.getItems().add(new SortStartDate());
        comboBox.setOnAction((ActionEvent event)
                -> {
            sortStrategy = comboBox.getSelectionModel().getSelectedItem();
            comboChanged();
        });

        //sortStrategy = comboBox.getSelectionModel().getSelectedItem();
    }

    public void comboChanged() {
        model.msOnDepartmentView(departmentAnchorPane, borderPane, sortStrategy);
    }

    public void initListeners() {
//        departmentAnchorPane.heightProperty().addListener(new ChangeListener<Number>() {
//            @Override
//            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
//                
//            }
//        });

        borderPane.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                model.msOnDepartmentView(departmentAnchorPane, borderPane, sortStrategy);
            }

        });
    }

    @FXML
    private void searchBar(KeyEvent event) {
        model.setSearchString(txtSearchfield.getText().toLowerCase().trim());
        model.msOnDepartmentView(departmentAnchorPane, borderPane, sortStrategy);
    }

    public void setDepartment(Department department) {
        currentDepartment = department;
        lblText.setText(department.getName());
        model.setSelectedDepartmentName(currentDepartment.getName());

    }

    public void setFullscreen() {
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.setFullScreen(true);
    }

    /*
    denne metode opdatere gui'en men med en thred.sleep delay på 5000ms så,
    den kun opdatere programmet hver 5 sekund for at reducere lag
     */
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
                            model.msOnDepartmentView(departmentAnchorPane, borderPane, sortStrategy);
                        }
                    });
                }

            }
        });
        t.setDaemon(true);
        t.start();
    }

    @FXML
    private void scrollOnTouch(TouchEvent event) {
        System.out.println("pifspojdf");
    }

    @FXML
    private void scrollOnDragQueen(MouseEvent event) {
//        double getPosition = event.getY();
//        double getMaxHeight = departmentAnchorPane.getMaxHeight();
//        
//        System.out.println("scene y " + event.MOUSE_CLICKED);
//        System.out.println(""+ event.getY());
//        scrollPane.setVvalue(0.5);

          
    }

   }
