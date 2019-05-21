/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pkg2ndsemesterexamproject.be.Customer;
import pkg2ndsemesterexamproject.be.ICustomer;
import pkg2ndsemesterexamproject.be.IDepartment;
import pkg2ndsemesterexamproject.be.IDepartmentTask;
import pkg2ndsemesterexamproject.be.IOrder;
import pkg2ndsemesterexamproject.be.IProductionOrder;
import pkg2ndsemesterexamproject.be.Order;
import pkg2ndsemesterexamproject.bll.OrdersPerDepartment;
import pkg2ndsemesterexamproject.gui.ManagerModel;
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

    private ManagerModel model;
    @FXML
    private TableView<IProductionOrder> tableView;
    @FXML
    private TableColumn<Order, String> orderNum;
    @FXML
    private TableColumn<Customer, String> customer;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        try
        {
            model = new ManagerModel(tableView);
        } catch (IOException ex)
        {
            Logger.getLogger(StartScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
           orderNum.setCellValueFactory(celldata->celldata.getValue().getOrderProperty());
           customer.setCellValueFactory(celldata->celldata.getValue().getCustomerProperty());
                  
           orderNum.setCellValueFactory(new PropertyValueFactory<>("OrderNumber"));
           customer.setCellValueFactory(new PropertyValueFactory<>("Customer"));
    }

    public void getlistOfOrders() throws SQLException
    {
        ObservableList managerOBS = ();
        List<IProductionOrder> saveData = new ArrayList();
        saveData.addAll(model.getProductionOrders());
        tableView.

        departmentTask.getDepartment();
        productionOrder.getDepartmentTasks();

    }

}
