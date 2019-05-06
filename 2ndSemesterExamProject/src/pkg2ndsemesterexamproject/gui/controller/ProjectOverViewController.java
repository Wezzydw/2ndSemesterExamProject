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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import pkg2ndsemesterexamproject.be.IDepartmentTask;
import pkg2ndsemesterexamproject.be.IProductionOrder;
import pkg2ndsemesterexamproject.be.IWorker;
import pkg2ndsemesterexamproject.be.Order;
import pkg2ndsemesterexamproject.gui.Model;

/**
 * FXML Controller class
 *
 * @author andreas
 */
public class ProjectOverViewController implements Initializable
{
    private ObservableList<IWorker> allWorkers = FXCollections.observableArrayList();
    
    
    private Model model;
    @FXML
    private Label lblOrder;
    @FXML
    private Label lblCustomer;
    @FXML
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
        Order order = new Order("12");
        model.orderIsDone(order);
    }
    
    private void clockUpdate(){
        try {
            while (true) {
                
                Platform.runLater(()-> {
                    int hours = LocalDateTime.now().getHour();
                    int minute = LocalDateTime.now().getMinute();
                    int second = LocalDateTime.now().getSecond();
                    String clock = hours + ":" + minute + ":" + second;
                    lblClock.setText(clock);
                });
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (InterruptedException ex) {
            System.out.println("Erorr");
        }
//        int hours = LocalDateTime.now().getHour();
//        int minute = LocalDateTime.now().getMinute();
//        int second = LocalDateTime.now().getSecond();
//        String clock = hours + ":" + minute + ":" + second;
//        lblClock.setText(clock);
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
    
    
    public void setData(IDepartmentTask dt, IProductionOrder po){
        lblCustomer.setText(po.getCustomer().toString());
        lblOrder.setText(po.getOrder().toString());
        lblDeliveryDate.setText(po.getDelivery().toString());
        lblStartDate.setText(dt.getStartDate().toString());
        lblEndDate.setText(dt.getEndDate().toString());
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
        gc.fillRect(0, 0, 160, 20);
        gc.setStroke(Color.BLACK);
        gc.strokeRect(0, 0, 400, 20);
        
    }
}
