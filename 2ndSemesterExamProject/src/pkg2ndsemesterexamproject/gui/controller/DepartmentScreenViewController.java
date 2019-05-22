/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.gui.controller;

import com.jfoenix.controls.JFXSlider;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
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
    @FXML
    private JFXSlider postSlider;
    @FXML
    private Label lblZoom;
    
    private Department currentDepartment;
    private Model model;
    private ISortStrategy sortStrategy;
    private double scrollValue;
    private double lastDrag;
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
        lblZoom.setText("" + postSlider.getValue() + "%");
        LocalDate date = LocalDate.now();
        lblDate.setText(date.format(DateTimeFormatter.ofPattern("d/MM/YYYY")));
        scrollPane.setFitToWidth(true);
        
        model.msOnDepartmentView(departmentAnchorPane, borderPane, sortStrategy);
        functionThatUpdatedGUIEvery5Seconds();
        initListeners();

        sortStrategy = new SortOrderId();
        setComboBox();
        
        scrollValue = 0;
        lastDrag = -1;
    }
    
    
    /**
     * Denne metode sætter comboboxens itmes og laver deres onAction.
     */
    private void setComboBox(){
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
    }
    /**
     * Denne metode kalder metoden model.msOnDepartmentView.
     */
    public void comboChanged() {
        model.msOnDepartmentView(departmentAnchorPane, borderPane, sortStrategy);
    }

    public void initListeners() {
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
    /**
     * Denne metode sætter department i lblText og currentDepartment til 
     * parameteren department, derefter kaldes model.setSelecetedDepartment 
     * @param department 
     */
    public void setDepartment(Department department) {
        currentDepartment = department;
        lblText.setText(department.getName());
        model.setSelectedDepartmentName(currentDepartment.getName());
    }
    /**
     * Denne metode sætter viewet til fullscreen
     */
    public void setFullscreen() {
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.setFullScreen(true);
    }

    /**
     * Denne metode opdatere gui'en men med en thred.sleep delay på 5000ms så,
     * den kun opdatere programmet hver 5 sekund for at reducere lag
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

    /**
     * Denne metode tager event som parameter, dette event 
     * er når man trykker og trækker med musen, dette scroller 
     * an på hvilken retting du trækker musen
     * @param event 
     */
    @FXML
     private void scrollOnDragQueen(MouseEvent event) {
        double apHeight = departmentAnchorPane.getHeight();
        double bpHeight = borderPane.getHeight();
        if (lastDrag > event.getSceneY() && lastDrag > 0) {
            scrollValue = scrollValue + bpHeight/apHeight/50;
        } else if (lastDrag < event.getSceneY() && lastDrag > 0) {
            scrollValue = scrollValue - bpHeight/apHeight/50;
        }
        if (scrollValue < 0) {
            scrollValue = 0;
        }
        if (scrollValue > 1) {
            scrollValue = 1;
        }
        lastDrag = event.getSceneY();
        scrollPane.setVvalue(scrollValue);
    }
    
    /**
     * Denne metode sætter slideren på den nærmeste 1tiende 
     * du slipper musen ved og sætter lblZoom til den værdi, 
     * derefter kalder den model.zoomControl() med værdien fra slideren 
     * og updatere GUI med metoden model.msOnDepartmentView
     * @param event 
     */
    @FXML
    private void sliderZoom(MouseEvent event)
    {
        lblZoom.setText("" + postSlider.getValue() + "%");
        model.zoomControl(postSlider.getValue());
        model.msOnDepartmentView(departmentAnchorPane, borderPane, sortStrategy);
    }

}
