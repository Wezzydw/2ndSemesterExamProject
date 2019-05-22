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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Pair;
import pkg2ndsemesterexamproject.be.Customer;
import pkg2ndsemesterexamproject.be.ICustomer;
import pkg2ndsemesterexamproject.be.IDepartment;
import pkg2ndsemesterexamproject.be.IDepartmentTask;
import pkg2ndsemesterexamproject.be.IOrder;
import pkg2ndsemesterexamproject.be.IProductionOrder;
import pkg2ndsemesterexamproject.be.Order;
import pkg2ndsemesterexamproject.be.ProductionOrder;
import pkg2ndsemesterexamproject.bll.OrdersPerDepartment;
import pkg2ndsemesterexamproject.gui.ManagerModel;
import pkg2ndsemesterexamproject.gui.Model;

/**
 * FXML Controller class
 *
 * @author marce
 */
public class ManagerOverviewController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private IDepartmentTask departmentTask;
    private IProductionOrder productionOrder;

    private ManagerModel model;
    @FXML
    private TableView<IProductionOrder> tableView;
    @FXML
    private TableColumn<IProductionOrder, String> orderNum;
    @FXML
    private TableColumn<IProductionOrder, String> customer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            model = new ManagerModel();
        } catch (IOException ex) {
            Logger.getLogger(StartScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        orderNum.setCellValueFactory(celldata -> celldata.getValue().getOrder().getOrderProperty());
        customer.setCellValueFactory(celldata -> celldata.getValue().getCustomer().getCustomerProperty());


//        orderNum.setCellValueFactory(new PropertyValueFactory<>("OrderNum"));
//        customer.setCellValueFactory(new PropertyValueFactory<>("Customer"));
        try {

            getlistOfOrders();
        } catch (SQLException ex) {
            Logger.getLogger(ManagerOverviewController.class.getName()).log(Level.SEVERE, null, ex);
        }

//           orderNum.setCellValueFactory(new PropertyValueFactory<>("OrderNumber"));
//           customer.setCellValueFactory(new PropertyValueFactory<>("Customer"));
        model.scanFolderForNewFiles();

    }

    public void getlistOfOrders() throws SQLException {

        tableView.setItems(model.getObservableProductionOrders());

    }

    @FXML
    private void scanFolderForNewFiles(ActionEvent event) {
        model.scanFolderForNewFiles();
    }

    @FXML
    private void whenClicked(MouseEvent event)
    {
         FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/pkg2ndsemesterexamproject/gui/view/OrderOverView.fxml"));
        try {
            loader.load();
        } catch (IOException ex) {
            System.out.println("Error" + ex);
        }
        OrderOverViewController display = loader.getController();
        display.setProductionOrder(tableView.getSelectionModel().getSelectedItem());
        Parent p = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(p));
        stage.showAndWait();
        stage.close();

    }

}
