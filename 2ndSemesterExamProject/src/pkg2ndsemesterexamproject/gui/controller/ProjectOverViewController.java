/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import pkg2ndsemesterexamproject.be.IDepartmentTask;
import pkg2ndsemesterexamproject.be.IProductionOrder;
import pkg2ndsemesterexamproject.be.IWorker;
import pkg2ndsemesterexamproject.gui.Model;

/**
 * FXML Controller class
 *
 * @author andreas
 */
public class ProjectOverViewController implements Initializable
{
    private ObservableList<IWorker> allWorkers = FXCollections.observableArrayList();
    private IDepartmentTask departmentTask;
    private IProductionOrder productionOrder;
    
    
    private Model model;
    @FXML
    private Label lblOrder;
    @FXML
    private Label lblCustomer;
    private Label lblDeliveryDate;
    @FXML
    private Label lblClock;
    
    private ExecutorService executor;
    @FXML
    private ListView<IWorker> lstView;
    @FXML
    private HBox hboxDepartments;
    @FXML
    private Label lblStartDate;
    @FXML
    private Label lblEndDate;
    @FXML
    private AnchorPane mainPane;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        try {
            // TODO
            model = new Model();
        } catch (IOException ex) {
            Logger.getLogger(ProjectOverViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        updateListViewWorkersAssigned();
    }    
    
    public void startClock(){
        executor = Executors.newSingleThreadExecutor();
        executor.submit(() ->
        {
            clockUpdate();
        });
    }
    public void closeWindow() {
        
        executor.shutdownNow();
        
    }
    
    @FXML
    private void orderIsDone(ActionEvent event)
    {
        try {
            model.orderIsDone(departmentTask, productionOrder);
        } catch (SQLException ex) {
            Logger.getLogger(ProjectOverViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void clockUpdate(){
        try {
            while (true) {
                
                Platform.runLater(()-> {
                    String sec = "";
                    String min = "";
                    String hour = "";
                    int hours = LocalDateTime.now().getHour();
                    int minute = LocalDateTime.now().getMinute();
                    int second = LocalDateTime.now().getSecond();
                    sec = "" + second;
                    min = "" + minute;
                    hour = "" + hours;
                    if (second<10){
                        sec = "0" + second;
                    }
                    if (minute<10){
                        min = "0" + minute;
                    }
                    if (hours<10){
                        hour = "0" + hours;
                    }
                    String clock = hour + ":" + min + ":" + sec;
                    lblClock.setText(clock);
                });
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (InterruptedException ex) {
            System.out.println("Closed Window");
        }
    }
    
    private void updateListViewWorkersAssigned(){
        
        allWorkers.clear();
        
        try {
            for (IWorker iWorker : model.updateListViewWorkersAssigned()) {
                allWorkers.add(iWorker);
            }
        } catch (IOException ex) {
            Logger.getLogger(ProjectOverViewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ProjectOverViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
            lstView.setItems(allWorkers);
    }
    
    public void setOrder(IDepartmentTask dt, IProductionOrder po){
        
        this.departmentTask = dt;
        this.productionOrder = po;
    }
    
    
    public void setData(IDepartmentTask dt, IProductionOrder po){
        lblCustomer.setText(po.getCustomer().toString());
        lblOrder.setText(po.getOrder().toString());
        lblStartDate.setText(dt.getStartDate().toLocalDate() + "");
        lblEndDate.setText(dt.getEndDate().toLocalDate() + "");
        int indexOfDepartment = 0;
        int counter = 0;
        
        for (IDepartmentTask tasks : po.getDepartmentTasks()) {
            if(tasks.equals(dt)){
                indexOfDepartment = counter;
            }
            counter++;
        }
        
        for (int i = 0; i <= indexOfDepartment; i++) {
            Label l1 = new Label();
            l1.setText(po.getDepartmentTasks().get(i).getDepartment().toString());
            l1.setStyle("-fx-background-color: Blue");
            hboxDepartments.getChildren().add(l1);
        }
        
        Pane progress = new Pane();
        progress.setMaxSize(400, 20);
        Canvas canvas = new Canvas();
        canvas.setHeight(20);
        canvas.setWidth(400);
        canvas.setLayoutX(14);
        canvas.setLayoutY(285);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.GREEN);
        Long daysBetween = ChronoUnit.DAYS.between(dt.getStartDate(), dt.getEndDate());
        int progressInterval = (int) (400 / daysBetween);
        LocalDateTime todayIs = LocalDateTime.now();
        Long startToNow = ChronoUnit.DAYS.between(dt.getStartDate(), todayIs);
        gc.fillRect(0, 0, progressInterval * startToNow, 20);
        gc.setStroke(Color.BLACK);
        gc.strokeRect(0, 0, 400, 20);
        
        mainPane.getChildren().add(canvas);
    }
}
