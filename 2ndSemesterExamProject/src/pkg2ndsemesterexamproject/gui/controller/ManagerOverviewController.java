package pkg2ndsemesterexamproject.gui.controller;

import com.jfoenix.controls.JFXProgressBar;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pkg2ndsemesterexamproject.be.IProductionOrder;
import pkg2ndsemesterexamproject.gui.ManagerModel;

public class ManagerOverviewController implements Initializable {

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

    private ManagerModel model;
    private static final long updateTime = 5000;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        scanProgress.setVisible(false);
        managerAnchor.getStyleClass().add("backgroundPicture");
        functionThatUpdatedGUIEvery5Seconds();

        try {
            model = new ManagerModel();
        } catch (IOException ex) {
            ExceptionsHandler.errorPopUpScreen(ex);
        }

        orderNum.setCellValueFactory(celldata
                -> {
            try {
                return celldata.getValue().getOrder().getOrderProperty();
            } catch (NullPointerException ex) {
                ExceptionsHandler.errorPopUpScreen(ex);
                return null;
            }
        });

        customer.setCellValueFactory(celldata
                -> {
            try {
                return celldata.getValue().getCustomer().getCustomerProperty();
            } catch (NullPointerException ex) {
                ExceptionsHandler.errorPopUpScreen(ex);
                return null;
            }
        });

        try {
            model.scanFolderForNewFiles();
        } catch (IOException | SQLException ex) {
            ExceptionsHandler.errorPopUpScreen(ex);
        }
        getListOfOrders();
    }

    /**
     * Denne metode får fat i alle productiionsorders fra databasen til vores
     * tableview.
     */
    public void getListOfOrders() {
        try {
            tableView.setItems(model.getObservableProductionOrders());
        } catch (SQLException ex) {
            ExceptionsHandler.errorPopUpScreen(ex);
        }
    }

    /**
     * Denne metode kalder scanFolderForNewFiles metoden.
     *
     * @param event er når man trykker på knappen "Scan folder"
     */
    @FXML
    private void scanFolderForNewFiles(ActionEvent event) {
        try {
            model.scanFolderForNewFiles();
        } catch (IOException | SQLException ex) {
            ExceptionsHandler.errorPopUpScreen(ex);
        }
        model.setProgressBarToScanFolder(scanProgress);
    }

    /**
     * Denne metode tjekker efter null exceptions i vores tableview. derudover
     * loader den det rigtige fxml view og starter det op, samt registrere
     * hvilken ordre vi klikker på og åbner et nyt view op med den rigtige
     * information i sig.
     *
     * @param event er når man trykker på en celle i tableviewet
     */
    @FXML
    private void whenClicked(MouseEvent event) {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/pkg2ndsemesterexamproject/gui/view/OrderOverView.fxml"));
            try {
                loader.load();
            } catch (IOException ex) {
                ExceptionsHandler.errorPopUpScreen(ex);
            }
            OrderOverViewController display = loader.getController();
            display.setProductionOrder(tableView.getSelectionModel().getSelectedItem());
            Parent p = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(p));
            stage.getIcons().add(new Image("pkg2ndsemesterexamproject/gui/view/css/logo-13.jpg"));
            stage.setTitle("Order number: "
                    + tableView.getSelectionModel().getSelectedItem().getOrder().getOrderNumber());
            tableView.getSelectionModel().clearSelection();
            stage.showAndWait();
            stage.close();
        }
    }

    /**
     * Denne metode opdatere gui'en men med en thred.sleep delay på 5000ms så,
     * den kun opdatere programmet hver 5 sekund for at reducere lag
     */
    public void functionThatUpdatedGUIEvery5Seconds() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(updateTime);
                    } catch (InterruptedException ex) {
                        ExceptionsHandler.errorPopUpScreen(ex);
                    }
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            getListOfOrders();
                        }
                    });
                }
            }
        });
        t.setDaemon(true);
        t.start();
    }
}
