/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import pkg2ndsemesterexamproject.be.IDepartmentTask;
import pkg2ndsemesterexamproject.be.IProductionOrder;

/**
 * FXML Controller class
 *
 * @author marce
 */
public class OrderOverViewController implements Initializable
{
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
    public void initialize(URL url, ResourceBundle rb)
    {
        orderOverviewAnchor.getStyleClass().add("panetest");
       
    }

    public void setProductionOrder(IProductionOrder productionOrder)
    {
        selectedProductionOrder = productionOrder;
        placeOrderInOverView();
    }
    
/**
 * denne metode laver vores panes i vores orderoverview. Den satte størrelse på panesne
 * samt alt på vores panes positioner. den tjekker også hver odre efter om den er færdig eller ej,
 * samt hvor langt i processen ordrene er. Herefter sættes vores progress circle 
 * og canvas til de rigtige status farver................MER tror jeg
 * @param po
 * @param dpt
 * @param scale
 * @return 
 */
    public Pane createOrderInOverView(IProductionOrder po, IDepartmentTask dpt, Double scale)
    {//IProductionOrder po, IDepartmentTask dp
        Label invis = new Label();
        invis.setMinSize(0, 0);
        List<IDepartmentTask> tasks = po.getDepartmentTasks();
        IDepartmentTask task = null;
        Label orderNum = new Label(po.getOrder().toString());
        Label customer = new Label("Customer: " + po.getCustomer().getName());
        Label startDate = new Label(dpt.getStartDate().format(DateTimeFormatter.ofPattern("dd/MM/YYYY")));
        Label endDate = new Label(dpt.getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/YYYY")));
        Pane orderPane = new Pane();
        orderPane.setMaxSize(200 * scale, 150 * scale);
        orderPane.getStyleClass().add("pane");
        Rectangle rec = new Rectangle(200 * scale, 150 * scale);
        rec.setArcHeight(25);
        rec.setArcWidth(25);
        orderPane.setShape(rec);
        Circle circle = new Circle(13 * scale);
        circle.setFill(Paint.valueOf("Green"));

        for (int i = 0; i < tasks.size(); i++)
        {
            if (tasks.get(i).equals(dpt) && i > 0)
            {
                task = tasks.get(i - 1);
            }
        }
        if (task == null || task.getFinishedOrder())
        {
            circle.setFill(Paint.valueOf("Green"));
        } else
        {
            circle.setFill(Paint.valueOf("Red"));
        }

        orderNum.getStyleClass().add("label");
        customer.getStyleClass().add("label");
        startDate.getStyleClass().add("label");
        endDate.getStyleClass().add("label");

        orderNum.setStyle("-fx-font-size:" + 15 * scale);
        customer.setStyle("-fx-font-size:" + 15 * scale);
        startDate.setStyle("-fx-font-size:" + 15 * scale);
        endDate.setStyle("-fx-font-size:" + 15 * scale);

        Pane progress = new Pane();
        progress.setMaxSize(175 * scale, 15 * scale);
        Canvas canvas = new Canvas();
        canvas.setHeight(15 * scale);
        canvas.setWidth(175 * scale);
        canvas.setLayoutX(13 * scale);
        canvas.setLayoutY(130 * scale);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        Long daysBetween = ChronoUnit.DAYS.between(dpt.getStartDate(), dpt.getEndDate());
        int progressInterval = (int) ((175 * scale) / daysBetween);
        LocalDateTime todayIs = LocalDateTime.now();
        Long startToNow = ChronoUnit.DAYS.between(dpt.getStartDate(), todayIs);
        double dd = progressInterval * startToNow;
        gc.setFill(Color.GREEN);
        if (progressInterval * startToNow > 175 * scale)
        {
            gc.setFill(Color.RED);
            dd = 175 * scale;
        }
        gc.fillRect(0, 0, dd, 20 * scale);
//        gc.fillRect(0, 0, 160, 15);
        gc.setStroke(Color.BLACK);
        gc.strokeRect(0, 0, 175 * scale, 15 * scale);

        orderPane.getChildren().add(canvas);

        circle.setLayoutX(180 * scale);
        circle.setLayoutY(20 * scale);

        orderNum.setLayoutX(50 * scale);
        orderNum.setLayoutY(15 * scale);

        startDate.setLayoutX(20 * scale);
        startDate.setLayoutY(100 * scale);

        orderNum.setLayoutX(25 * scale);
        orderNum.setLayoutY(40 * scale);

        customer.setLayoutX(25 * scale);
        customer.setLayoutY(60 * scale);

        startDate.setLayoutX(15 * scale);
        startDate.setLayoutY(100 * scale);

        endDate.setLayoutX(115 * scale);
        endDate.setLayoutY(100 * scale);

        progress.setLayoutX(5 * scale);
        progress.setLayoutY(130 * scale);

        invis.setLayoutX(200 * scale);
        invis.setLayoutY(150 * scale);

        EventHandler<MouseEvent> event1 = (MouseEvent e)
                ->
        {
            if (e.getClickCount() == 2)
            {
                //goToOverview(po, dpt);
            }
        };

        orderPane.setOnMousePressed(event1);

        orderPane.getChildren().addAll(circle, orderNum, startDate, endDate, customer, invis);

        return orderPane;

    }
/**
 * denne metode åbner vores orderoverview.????????????
 * @param po
 * @param dpt 
 */
    private void goToOrderOverview(IProductionOrder po, IDepartmentTask dpt)
    {//skal nok også bruge en order eller noget, så vi kan få alt relevant information med 

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/pkg2ndsemesterexamproject/gui/view/OrderOverView.fxml"));
        try
        {
            loader.load();
        } catch (IOException ex)
        {
            System.out.println("Error" + ex);
        }
        OrderOverViewController display = loader.getController();
        //display.startClock();
        //display.setData(dpt, po);
        //display.setOrder(dpt, po);
        Parent p = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(p));
        stage.showAndWait();
        //display.closeWindow();
        stage.close();
    }
/**
 * Denne metode sørger for at alle de panes der bliver sat ind i vores orderoverview,
 * sættes pænt op med det ønskede mellemrum imellem hinanden.
 */
    public void placeOrderInOverView()
    {
        List<Pane> panes = new ArrayList();
        for (IDepartmentTask orders : selectedProductionOrder.getDepartmentTasks())
        {
            
           panes.add(createOrderInOverView(selectedProductionOrder, orders, 1.0));
        }
        int counter = 0;
        for (Pane pane : panes)
        {
            orderOverviewAnchor.getChildren().add(pane);
            pane.setLayoutX(minMargenEdgeX + counter * (orderPaneWidth + minMargenX));
            pane.setLayoutY(250);            
                    
                    
                    counter++;
        }
        
    }
}
