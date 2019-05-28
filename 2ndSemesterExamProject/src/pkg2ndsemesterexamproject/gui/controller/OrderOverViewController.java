/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import pkg2ndsemesterexamproject.be.IDepartmentTask;
import pkg2ndsemesterexamproject.be.IProductionOrder;
import pkg2ndsemesterexamproject.gui.CreatePane;

/**
 * FXML Controller class
 *
 * @author marce
 */
public class OrderOverViewController implements Initializable {

    private double orderPaneWidth = 200;
    private double orderPaneHeigth = 150;
    private final int minMargenEdgeX = 25;
    private final int minMargenEdgeY = 10;
    private final int minMargenX = 20;
    private final int minMargenY = 10;
    private IProductionOrder selectedProductionOrder;
    @FXML
    private AnchorPane orderOverviewAnchor;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        orderOverviewAnchor.getStyleClass().add("panetest");

    }

    public void setProductionOrder(IProductionOrder productionOrder) {
        selectedProductionOrder = productionOrder;
        placeOrderInOverView();
    }

    /**
     * denne metode åbner vores orderoverview.????????????
     *
     * @param po
     * @param dpt
     */
    private void goToOrderOverview(IProductionOrder po, IDepartmentTask dpt) {//skal nok også bruge en order eller noget, så vi kan få alt relevant information med 

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/pkg2ndsemesterexamproject/gui/view/OrderOverView.fxml"));
        try {
            loader.load();
        } catch (IOException ex) {
            System.out.println("Error" + ex);
        }

        Parent p = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(p));
        stage.showAndWait();
        stage.close();
    }

    /**
     * Denne metode sørger for at alle de panes der bliver sat ind i vores
     * orderoverview, sættes pænt op med det ønskede mellemrum imellem hinanden.
     */
    public void placeOrderInOverView() {
        List<Pane> panes = new ArrayList();
        for (IDepartmentTask orders : selectedProductionOrder.getDepartmentTasks()) {

            panes.add(CreatePane.createOrderInGUI(selectedProductionOrder, orders, 1.0));
        }
        int counter = 0;
        for (Pane pane : panes) {
            pane.setLayoutX(minMargenEdgeX + counter * (orderPaneWidth + minMargenX));
            pane.setLayoutY(250);
            Label label = new Label(selectedProductionOrder.getDepartmentTasks().get(counter).getDepartment().getName());
            label.setLayoutX(10);
            label.setLayoutY(10);
            pane.getChildren().add(label);
            orderOverviewAnchor.getChildren().add(pane);
            counter++;
        }
    }
}
