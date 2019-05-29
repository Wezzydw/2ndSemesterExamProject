/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.gui.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import pkg2ndsemesterexamproject.be.IDepartmentTask;
import pkg2ndsemesterexamproject.be.IProductionOrder;
import pkg2ndsemesterexamproject.gui.CreatePane;

/**
 * FXML Controller class
 *
 * @author marce
 */
public class OrderOverViewController implements Initializable
{

    @FXML
    private AnchorPane orderOverviewAnchor;

    private double orderPaneWidth = 200;
    private double orderPaneHeigth = 150;
    private static final int minMargenEdgeX = 25;
    private static final int minMargenEdgeY = 10;
    private static final int minMargenX = 20;
    private static final int minMargenY = 10;
    private IProductionOrder selectedProductionOrder;
    private Timeline guiUpdateLimit;
    private List<Pane> panes;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        panes = new ArrayList();
        orderOverviewAnchor.getStyleClass().add("panetest");
        guiUpdateLimit = initializeGUIUpdateLimit();
        guiUpdateLimit.setCycleCount(1);
        orderOverviewAnchor.widthProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
            {
                if (newValue.intValue() != 0)
                {
                    guiUpdateLimit.play();
                }
            }
        });

    }

    /**
     *
     * @return @throws RuntimeException
     */
    private Timeline initializeGUIUpdateLimit() throws RuntimeException
    {
        return new Timeline(new KeyFrame(Duration.seconds(0.1), new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                placeStickyNotes(false);
            }
        }));
    }

    /**
     * Denne metode sætter selectedProductionOrder til productionOrder og kalder
     * metoden placeORderInOverView()
     *
     * @param productionOrder er den productionOrder som bliver sendt med
     */
    public void setProductionOrder(IProductionOrder productionOrder)
    {
        selectedProductionOrder = productionOrder;
        placeOrderInOverView();
    }

    /**
     * Denne metode sørger for at alle de panes der bliver sat ind i vores
     * orderoverview, sættes pænt op med det ønskede mellemrum imellem hinanden.
     */
    public void placeOrderInOverView()
    {

        for (IDepartmentTask orders : selectedProductionOrder.getDepartmentTasks())
        {
            panes.add(CreatePane.createOrderInGUI(selectedProductionOrder, orders, 1.0));
        }

        placeStickyNotes(true);
    }

    public void placeStickyNotes(boolean isToBeAdded)
    {
        int counter = 0;
        double width = orderOverviewAnchor.getWidth();
        if (width == 0)
        {
            width = orderOverviewAnchor.getPrefWidth();
        }

        outerloop:
        for (int k = 0; k < panes.size(); k++)
        {
            for (int j = 0; j < calcNumberOfXPanes(width); j++)
            {
                panes.get(counter).setLayoutX(minMargenEdgeX + j * (orderPaneWidth + minMargenX));
                panes.get(counter).setLayoutY(minMargenEdgeY + k * (orderPaneHeigth + minMargenY));

                if (isToBeAdded)
                {
                    orderOverviewAnchor.getChildren().add(panes.get(counter));
                }

                if (counter == panes.size() - 1)
                {
                    break outerloop;
                }

                counter++;

            }

        }
    }

    public int calcNumberOfXPanes(double anchorWidth)
    {
        double viewWidth = anchorWidth;
        double numberOfPanes = viewWidth / (orderPaneWidth + minMargenX);
        int xNumberOfPanes = (int) (numberOfPanes);
        return xNumberOfPanes;
    }
}
