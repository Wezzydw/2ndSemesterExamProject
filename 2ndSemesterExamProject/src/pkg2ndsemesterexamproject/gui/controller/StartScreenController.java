/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.gui.controller;

import pkg2ndsemesterexamproject.be.Department;
import pkg2ndsemesterexamproject.gui.Model;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

/**
 * FXML Controller class
 *
 * @author andreas
 */
public class StartScreenController implements Initializable
{

    @FXML
    private MenuButton MenuButton;
    
    private Model model;
    
    private List<Department> allDepartments;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        model = new Model();
        
//        allDepartments = model.getAllDepartments();
//        model.setMenuItems(MenuButton, model.getAllDepartments());
        
        EventHandler<ActionEvent> event1 = (ActionEvent e) ->
        {
            System.out.println("1" + e.getSource());
            MenuItem temp = (MenuItem) e.getSource();
            System.out.println(temp.getText());
            {
                
            }
                
            
        };
        List<MenuItem> departmentBtns = new ArrayList();
//        for (Department allDepartment : allDepartments)
        List<Department> departmentNames = new ArrayList();
        departmentNames.add(new Department("halv"));
        departmentNames.add(new Department("tom"));        
        for (Department depar : departmentNames)
        {
            MenuItem item = new MenuItem("label");//label skal være allDepartment.getName() fra BE laget 
            item.setOnAction(event1);
            departmentBtns.add(item);
        }
        MenuButton.getItems().addAll(departmentBtns);
    }    
    
    private void goToManagerScreen(){
        
    }
    
    private void goToDepartmentScreen(){
        
    }
    
    private void selectDepartment(){
//        MenuButton.getItems().
    }
                
}
