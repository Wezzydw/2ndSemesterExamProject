/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.gui.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import pkg2ndsemesterexamproject.be.IDepartmentTask;
import pkg2ndsemesterexamproject.be.IProductionOrder;
import pkg2ndsemesterexamproject.gui.CreatePane;

/**
 * FXML Controller class
 *
 * @author marce
 */
public class OrderOverViewController implements Initializable {

    @FXML
    private AnchorPane orderOverviewAnchor;

    private double orderPaneWidth = 200;
    private double orderPaneHeigth = 150;
    private static final int minMargenEdgeX = 25;
    private static final int minMargenEdgeY = 10;
    private static final int minMargenX = 20;
    private static final int minMargenY = 10;
    private IProductionOrder selectedProductionOrder;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        orderOverviewAnchor.getStyleClass().add("panetest");
    }

    /**
     * Denne metode sætter selectedProductionOrder til productionOrder og kalder
     * metoden placeORderInOverView()
     *
     * @param productionOrder er den productionOrder som bliver sendt med
     */
    public void setProductionOrder(IProductionOrder productionOrder) {
        selectedProductionOrder = productionOrder;
        placeOrderInOverView();
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

        placeStickyNotes(true, panes);
    }

    public void placeStickyNotes(boolean isToBeAdded, List<Pane> panes) {
        int counter = 0;
        System.out.println(orderOverviewAnchor.getPrefWidth());
        outerloop:
        for (int k = 0; k < panes.size(); k++) {
            for (int j = 0; j < calcNumberOfXPanes(orderOverviewAnchor.getPrefWidth()); j++) {
                panes.get(counter).setLayoutX(minMargenEdgeX + j * (orderPaneWidth + minMargenX));
                panes.get(counter).setLayoutY(minMargenEdgeY + k * (orderPaneHeigth + minMargenY));

                if (isToBeAdded) {
                    orderOverviewAnchor.getChildren().add(panes.get(counter));
                }

                if (counter == panes.size() - 1) {
                    break outerloop;
                }

                counter++;

            }

        }
    }

    public int calcNumberOfXPanes(double anchorWidth) {
        double viewWidth = anchorWidth;
        double numberOfPanes = viewWidth / (orderPaneWidth + minMargenX);
        int xNumberOfPanes = (int) (numberOfPanes);
        return xNumberOfPanes;
    }
}
