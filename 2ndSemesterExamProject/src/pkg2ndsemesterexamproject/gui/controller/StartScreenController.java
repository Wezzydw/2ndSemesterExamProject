/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.gui.controller;

import java.io.IOException;
import pkg2ndsemesterexamproject.be.Department;
import pkg2ndsemesterexamproject.gui.Model;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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
            MenuItem temp = (MenuItem) e.getSource();
            System.out.println(temp.getText());
            selectDepartment(temp.getText());
                
            
        };
        List<MenuItem> departmentBtns = new ArrayList();
        allDepartments = new ArrayList();
        allDepartments.add(new Department("manager"));
        allDepartments.add(new Department("halv"));
        allDepartments.add(new Department("bælg"));
        for (Department depar : allDepartments)
        {
            MenuItem item = new MenuItem(depar.getName());//label skal være allDepartment.getName() fra BE laget 
            item.setOnAction(event1);
            departmentBtns.add(item);
        }
        MenuButton.getItems().addAll(departmentBtns);
        
        
    }    
    
    private void goToManagerScreen(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/pkg2ndsemesterexamproject/gui/view/Manager.fxml"));
        try
        {
            loader.load();
        } catch (IOException ex)
        {
            System.out.println("Error" + ex);
        }
        ManagerController display = loader.getController();

        Parent p = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(p));
        Stage stage1 = (Stage) MenuButton.getScene().getWindow();
        stage1.close();
        stage.showAndWait();
    }
    
    private void goToDepartmentScreen(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/pkg2ndsemesterexamproject/gui/view/DepartmentScreenView.fxml"));
        try
        {
            loader.load();
        } catch (IOException ex)
        {
            System.out.println("Error" + ex);
        }
        DepartmentScreenViewController display = loader.getController();

        Parent p = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(p));
        Stage stage1 = (Stage) MenuButton.getScene().getWindow();
        stage1.close();
        stage.showAndWait();
    }
    
    private void selectDepartment(String department){
        if (department.equals("manager")){
            goToManagerScreen();
        }
        else{
            Pane orderPane = model.createOrderInGUI();
        Scene scene = new Scene(orderPane, 200, 150);
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
            
//            goToDepartmentScreen();
        }
    }
                
}
