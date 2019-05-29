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
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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
    private Timeline guiUpdateLimit;
    private final long updateTime = 5000;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            model = new Model(departmentAnchorPane);
        } catch (IOException | SQLException ex) {
            System.out.println("initialize error" +ex);
        }
        guiUpdateLimit = initializeGUIUpdateLimit();
        guiUpdateLimit.setCycleCount(1);
        lblZoom.setText("" + postSlider.getValue() + "%");
        LocalDate date = LocalDate.now();
        WeekFields weekFields = WeekFields.of(Locale.getDefault()); 
        int weekNumber = date.get(weekFields.weekOfWeekBasedYear());
        lblDate.setText("["+weekNumber+ ":" +date.getDayOfWeek().getValue()+"]");
        scrollPane.setFitToWidth(true);

        updateFlowRate();
        functionThatUpdatedGUIEvery5Seconds();
        initListeners();
        
        sortStrategy = new SortOrderId();
        setComboBox();
        scrollValue = 0;
        lastDrag = -1;
    }

    /**
     * metoden her sørger for at programmet ikke konstant opdaterer, som kan
     * medføre funktionalitets problemer. Istedet opdateres hver aktion hver 0.1
     * sekund så der herved ikke forekommer ringe funktionalitet i form af lag.
     * @return Timeline
     * @throws RuntimeException
     */
    private Timeline initializeGUIUpdateLimit() throws RuntimeException {
        return new Timeline(new KeyFrame(Duration.seconds(0.1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    model.placeOrderInUI();
                } catch (SQLException ex) {
                    System.out.println("updateGUI error " + ex);
                    //ingen forbindelse til databasen
                }
            }
        }));
    }

    private void updateFlowRate() {
        guiUpdateLimit.play();
    }

    /**
     * Denne metode sætter comboboxens itmes og laver deres onAction.
     */
    private void setComboBox() {
        comboBox.getItems().add(new SortCustomer());
        comboBox.getItems().add(new SortEndDate());
        comboBox.getItems().add(new SortOrderId());
        comboBox.getItems().add(new SortReady());
        comboBox.getItems().add(new SortStartDate());
        comboBox.setOnAction((ActionEvent event)
                -> {
            sortStrategy = comboBox.getSelectionModel().getSelectedItem();
            comboChanged(sortStrategy);
            updateFlowRate();
        });
        comboChanged(new SortStartDate());
    }

    /**
     * Denne metode kalder metoden model.msOnDepartmentView.
     * @param sortStrategy er den valgte sortStrategy
     */
    public void comboChanged(ISortStrategy sortStrategy) {
        model.setSortStrategy(sortStrategy);
    }
    
    /**
     * Denne metode checker om man har gjort vinduet større eller mindre
     * hvis den er blevet ændret kalder den updateFlowRate()
     */
    public void initListeners() {
        borderPane.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                updateFlowRate();
            }
        });
    }
    
    /**
     * Denne metode registrer tastatur tryk og sender det skrevne ned 
     * i metoden model.setSearchString()
     * @param event er tastatur tryk inde i searchbaren 
     */
    @FXML
    private void searchBar(KeyEvent event) {
        model.setSearchString(txtSearchfield.getText().toLowerCase().trim());
        updateFlowRate();
    }

    /**
     * Denne metode sætter department i lblText og currentDepartment til
     * parameteren department, derefter kaldes model.setSelecetedDepartment
     * @param department er den department der er blevet valgt af brugeren
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
                        Thread.sleep(updateTime);
                    } catch (InterruptedException ex) {
                        System.out.println("Update 5 seconds error " + ex);
                    }
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                model.runDataCheckInDataHandler();
                            } catch (SQLException ex) {
                               System.out.println("run datacheck error " + ex);
                            }
                            updateFlowRate();
                        }
                    });
                }
            }
        });
        t.setDaemon(true);
        t.start();
    }

    /**
     * Denne metode tager event som parameter, dette event er når man trykker og
     * trækker med musen, dette scroller an på hvilken retting du trækker musen
     * @param event er når man trækker med musen
     */
    @FXML
    private void scrollOnDrag(MouseEvent event) {
        double apHeight = departmentAnchorPane.getHeight();
        double bpHeight = borderPane.getHeight();
        if (lastDrag > event.getSceneY() && lastDrag > 0) {
            scrollValue = scrollValue + bpHeight / apHeight / 50;
        } else if (lastDrag < event.getSceneY() && lastDrag > 0) {
            scrollValue = scrollValue - bpHeight / apHeight / 50;
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
     * Denne metode sætter slideren på den nærmeste 1tiende du slipper musen ved
     * og sætter lblZoom til den værdi, derefter kalder den model.zoomControl()
     * med værdien fra slideren og updatere GUI med metoden
     * model.resizeStickyNotes()
     * @param event er når man slipper efter man har trykket på slideren
     */
    @FXML
    private void sliderZoom(MouseEvent event) {
        lblZoom.setText("" + postSlider.getValue() + "%");
        model.zoomControl(postSlider.getValue());
        model.resizeStickyNotes();
        updateFlowRate();
    }
    /**
     * Denne metode checker om man trykker på f11 
     * hvis man trykker på f11 sættes scene til fullscreen
     * @param event er når man trykker på en knap på tastaturet
     */
    @FXML
    private void onF11Pressed(KeyEvent event) {
        if (event.getCode().equals(KeyCode.F11)) {
            setFullscreen();
        }
    }
}
