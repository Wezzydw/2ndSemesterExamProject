/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.gui.controller;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import pkg2ndsemesterexamproject.be.Department;
import pkg2ndsemesterexamproject.gui.Model;

/**
 * FXML Controller class
 *
 * @author andreas
 */
public class DepartmentScreenViewController implements Initializable
{    
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
    public void initialize(URL url, ResourceBundle rb)
    {
        LocalDate date = LocalDate.now();
        lblDate.setText(date.toString());

        model = new Model();
        model.placeOrderInUI(departmentAnchorPane);
        tmpLoop();
    }

    
    private void searchBar(KeyEvent event) {
    }
    
    public void setDepartment(Department department){
        lblText.setText(department.getName());
    }
    public void tmpLoop(){
       Thread thread1 = new Thread(new Runnable() {
    @Override
    public void run(){
        while (true)
        {            
            System.out.println(borderPane.getWidth());
//            System.out.println(departmentAnchorPane.getHeight()); 
//            System.out.println(departmentAnchorPane.getWidth());
//            System.out.println(departmentAnchorPane.getHeight()); 
//            System.out.println(departmentAnchorPane.getPrefWidth());
//            System.out.println(departmentAnchorPane.getPrefHeight()); 
//            System.out.println(departmentAnchorPane.getMaxWidth());
//            System.out.println(departmentAnchorPane.getMaxHeight()); 
//            System.out.println(departmentAnchorPane.getMinHeight());
//            System.out.println(departmentAnchorPane.getMinWidth());
            
            
            try
            {
                Thread.sleep(2000);
            } catch (InterruptedException ex)
            {
                Logger.getLogger(DepartmentScreenViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
});
 
thread1.start();
        
    }
}
