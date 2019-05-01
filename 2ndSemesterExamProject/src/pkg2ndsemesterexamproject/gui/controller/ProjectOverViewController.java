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
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import pkg2ndsemesterexamproject.be.Order;
import pkg2ndsemesterexamproject.be.Worker;
import pkg2ndsemesterexamproject.gui.Model;

/**
 * FXML Controller class
 *
 * @author andreas
 */
public class ProjectOverViewController implements Initializable
{
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
    private ListView<Worker> lstView;
    @FXML
    private HBox hboxDepartments;
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
        Order order = new Order(12);
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
    
    @FXML
    private void updateListViewWorkersAssigned(){
        
        try {
            model.updateListViewWorkersAssigned();
        } catch (IOException ex) {
            Logger.getLogger(ProjectOverViewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ProjectOverViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
