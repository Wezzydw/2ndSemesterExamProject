/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import pkg2ndsemesterexamproject.be.IDepartmentTask;
import pkg2ndsemesterexamproject.be.IProductionOrder;
import pkg2ndsemesterexamproject.bll.OrdersPerDepartment;
import pkg2ndsemesterexamproject.gui.Model;

/**
 * FXML Controller class
 *
 * @author marce
 */
public class ManagerOverviewController implements Initializable
{

    /**
     * Initializes the controller class.
     */
    private IDepartmentTask departmentTask;
    private IProductionOrder productionOrder;
    
    
    private Model model;
    @FXML
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
         try {
            model = new Model();
        } catch (IOException ex) {
            Logger.getLogger(StartScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }    
    public void getlistOfOrders() throws SQLException
    {
      model.getAllDepartments();
        departmentTask.getDepartment();
        productionOrder.getDepartmentTasks();   
        
    }
    
    
}
