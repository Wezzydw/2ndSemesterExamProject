/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.gui.controller;

import com.jfoenix.controls.JFXProgressBar;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pkg2ndsemesterexamproject.be.IDepartmentTask;
import pkg2ndsemesterexamproject.be.IProductionOrder;
import pkg2ndsemesterexamproject.gui.ManagerModel;

/**
 * FXML Controller class
 *
 * @author marce
 */
public class ManagerOverviewController implements Initializable {

    private ManagerModel model;
    @FXML
    private TableView<IProductionOrder> tableView;
    @FXML
    private TableColumn<IProductionOrder, String> orderNum;
    @FXML
    private TableColumn<IProductionOrder, String> customer;
    @FXML
    private AnchorPane managerAnchor;
    @FXML
    private JFXProgressBar scanProgress;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        scanProgress.setVisible(false);
        managerAnchor.getStyleClass().add("backgroundPicture");
        managerAnchor.setOpacity(0.75);
        try {
            model = new ManagerModel();
        } catch (IOException ex) {
            System.out.println("manager init error " + ex);
        }

        //Catch nullpointer expection og bed user om at slette readfiles.txt og tjekke forbindelse til db
        orderNum.setCellValueFactory(celldata -> {
            try {
                return celldata.getValue().getOrder().getOrderProperty();
            } catch (NullPointerException ex) {
                System.out.println("Shit is fucked up, check document");
                return null;
            }
        });

        customer.setCellValueFactory(celldata -> {
            try {
                return celldata.getValue().getCustomer().getCustomerProperty();
            } catch (NullPointerException ex) {
                System.out.println("Shit is fucked up, check document");
                return null;
            }
        });

        try {
            model.scanFolderForNewFiles();
        } catch (IOException | SQLException ex) {
            System.out.println("scanFolder error " + ex);
        }
        try {
            getlistOfOrders();
        } catch (SQLException ex) {
            System.out.println("getAllOrders in manager " + ex);
        }

    }

    /**
     * Denne metode får fat i alle productiionsorders fra databasen til vores
     * tableview.
     *
     * @throws SQLException
     */
    public void getlistOfOrders() throws SQLException {
        tableView.setItems(model.getObservableProductionOrders());
    }

    /**
     * Denne metode kalder scanFolderForNewFiles metoden.
     *
     * @param event
     */
    @FXML
    private void scanFolderForNewFiles(ActionEvent event) {
        try {
            model.scanFolderForNewFiles();
        } catch (IOException | SQLException ex) {
            System.out.println("Error scanning new folder");
        }
        model.checkScanFolder(scanProgress);
    }

    /**
     * Denne metode tjekker efter null exceptions i vores tableview. derudover
     * loader den det rigtige fxml view og starter det op, samt registrere
     * hvilken ordre vi klikker på og åbner et nyt view op med den rigtige
     * information i sig.
     *
     * @param event
     */
    @FXML
    private void whenClicked(MouseEvent event) {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/pkg2ndsemesterexamproject/gui/view/OrderOverView.fxml"));
            try {
                loader.load();
            } catch (IOException ex) {
                System.out.println("selectedTable error" + ex);
            }
            OrderOverViewController display = loader.getController();
            display.setProductionOrder(tableView.getSelectionModel().getSelectedItem());
            tableView.getSelectionModel().clearSelection();
            Parent p = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(p));
            stage.showAndWait();
            stage.close();
        }
    }
}
