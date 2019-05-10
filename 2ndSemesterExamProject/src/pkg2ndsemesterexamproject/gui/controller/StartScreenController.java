
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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.stage.WindowEvent;
import pkg2ndsemesterexamproject.be.IDepartment;

/**
 * FXML Controller class
 *
 * @author andreas
 */
public class StartScreenController implements Initializable {
    
    @FXML
    private MenuButton MenuButton;
    
    private Model model;
    
    private List<IDepartment> allDepartments;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            model = new Model();
        } catch (IOException ex) {
            Logger.getLogger(StartScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            allDepartments = model.getAllDepartments();
//        model.setMenuItems(MenuButton, model.getAllDepartments());
        } catch (SQLException ex) {
            System.out.println("ingen forbindelse");
        }
        
        EventHandler<ActionEvent> event1 = (ActionEvent e)
                -> {
            Department temp;
            MenuItem selectedItem = (MenuItem) e.getSource();
            String selectedItemName = selectedItem.getText();
            for (IDepartment allDepartment : allDepartments) {
                if (allDepartment.getName().equals(selectedItemName)) {
                    temp = (Department) allDepartment;
                    selectDepartment(temp);
                }
            }
        };
        List<MenuItem> departmentBtns = new ArrayList();
        
        if (allDepartments != null) {
            allDepartments.add(new Department("Manager"));
            for (IDepartment depar : allDepartments) {
                MenuItem item = new MenuItem(depar.getName());//label skal være allDepartment.getName() fra BE laget 
                item.setOnAction(event1);
                departmentBtns.add(item);
            }
        } else {
            allDepartments = new ArrayList();
            allDepartments.add(new Department("Manager"));
            allDepartments.add(new Department("halv"));
            allDepartments.add(new Department("bælg"));
            for (IDepartment depar : allDepartments) {
                MenuItem item = new MenuItem(depar.getName());//label skal være allDepartment.getName() fra BE laget 
                item.setOnAction(event1);
                departmentBtns.add(item);
            }
        }
        
        MenuButton.getItems().addAll(departmentBtns);
        
    }

    /*
    Denne metode skifter scene fra startscreen til manager screen
     */
    private void goToManagerScreen() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/pkg2ndsemesterexamproject/gui/view/CreateOrDeleteDepartment.fxml"));
        try {
            loader.load();
        } catch (IOException ex) {
            System.out.println("Error" + ex);
        }
        CreateOrDeleteDepartmentController display = loader.getController();
        
        Parent p = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(p));
        Stage stage1 = (Stage) MenuButton.getScene().getWindow();
//        stage1.close();
        stage.showAndWait();
    }

    /*
    Metoden skifter scene fra startscreen til DepartmentScreenViewet
     */
    private void goToDepartmentScreen(Department department) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/pkg2ndsemesterexamproject/gui/view/DepartmentScreenView.fxml"));
        try {
            loader.load();
        } catch (IOException ex) {
            System.out.println("Error" + ex);
        }
        DepartmentScreenViewController display = loader.getController();
        display.setDepartment(department);
        Parent p = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(p));
        Stage stage1 = (Stage) MenuButton.getScene().getWindow();
//        stage1.close();
        stage.setFullScreen(true);
        stage.showAndWait();
    }
    
    private void selectDepartment(Department department) {
        if (department.getName().equals("Manager")) {
            goToManagerScreen();
        } else {
//            Pane orderPane = model.createOrderInGUI();
//        Scene scene = new Scene(orderPane, 200, 150);
//        Stage primaryStage = new Stage();
//        primaryStage.setTitle("Hello World!");
//        primaryStage.setScene(scene);
//        primaryStage.show();
//            
            goToDepartmentScreen(department);
        }
        
    }
    
}
