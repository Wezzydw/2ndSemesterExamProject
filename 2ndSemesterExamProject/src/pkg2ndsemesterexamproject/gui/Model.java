/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.gui;

import java.io.IOException;
import static java.lang.System.exit;
import pkg2ndsemesterexamproject.be.Department;
import java.util.List;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import pkg2ndsemesterexamproject.be.Order;
import pkg2ndsemesterexamproject.be.Worker;
import pkg2ndsemesterexamproject.bll.IBLL;
import pkg2ndsemesterexamproject.bll.PassThrough;
import pkg2ndsemesterexamproject.gui.controller.ProjectOverViewController;

/**
 *
 * @author andreas
 */

public class Model
{


    private IBLL ptl;
    private final double orderPaneWidth = 200;
    private final double orderPaneHeigth = 150;

    public Model() {
        ptl = new PassThrough();
    }


    public List<Department> getAllDepartments()
    {

        return null;
    }

    public void setMenuItems(MenuButton MenuButton, List<Department> allDepartments) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void orderIsDone(Order order) {
        ptl.sendOrderIsDone();
    }


    //public Pane createOrderInGUI(int orederNum, String startDate, String endDate){
    public Pane createOrderInGUI()
    {

        Pane orderPane = new Pane();
        orderPane.setMaxSize(200, 150);
        orderPane.setStyle("-fx-background-color: Yellow");
        Circle circle = new Circle(13);
        circle.setFill(Paint.valueOf("Green"));
        Label orderNum = new Label("Ordernumber: " + 12321312);
        Label customer = new Label ("Customer: " + "Karl Kalashnikov");
        Label startDate = new Label("29-04-2019");
        Label endDate = new Label("09-05-2019");


        Pane progress = new Pane();
        progress.setMaxSize(175, 15);
        Canvas canvas = new Canvas();
        canvas.setHeight(15);
        canvas.setWidth(200);
        canvas.setLayoutX(13);
        canvas.setLayoutY(130);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.GREEN);
        gc.fillRect(0, 0, 160, 15);
        gc.setStroke(Color.BLACK);
        gc.strokeRect(0, 0, 175, 15);

        orderPane.getChildren().add(canvas);

        circle.setLayoutX(180);
        circle.setLayoutY(20);


        orderNum.setLayoutX(50);
        orderNum.setLayoutY(15);

        startDate.setLayoutX(20);
        startDate.setLayoutY(100);


        orderNum.setLayoutX(25);
        orderNum.setLayoutY(40);
        
        customer.setLayoutX(25);
        customer.setLayoutY(60);

        startDate.setLayoutX(15);
        startDate.setLayoutY(100);

        endDate.setLayoutX(125);

        endDate.setLayoutY(100);

        progress.setLayoutX(5);
        progress.setLayoutY(130);

        EventHandler<MouseEvent> event1 = (MouseEvent e) ->
        {

            goToOverview();
        };
        orderPane.setOnMousePressed(event1);


        orderPane.getChildren().addAll(circle, orderNum, startDate, endDate, customer);

        return orderPane;
    
    }
    private void goToOverview(){//skal nok også bruge en order eller noget, så vi kan få alt relevant information med 


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/pkg2ndsemesterexamproject/gui/view/ProjectOverView.fxml"));
        try {
            loader.load();
        } catch (IOException ex) {
            System.out.println("Error" + ex);
        }
        ProjectOverViewController display = loader.getController();
        display.startClock();
        Parent p = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(p));
        stage.showAndWait();
        display.closeWindow();
        stage.close();
        
    }


    public void placeOrderInUI(AnchorPane departmentView)
    {
        int i = 50;
        double viewHeight = departmentView.getPrefHeight();
        double viewWidth = departmentView.getPrefWidth();

        double numberOfPanes = viewWidth / orderPaneWidth;
        int xNumberOfPanes = (int) (numberOfPanes);

        int counter = 0;

        outerloop:
        for (int k = 0; k < i; k++)
        {

            for (int j = 0; j < xNumberOfPanes; j++)
            {
                Pane pane = createOrderInGUI();
                pane.setLayoutX(10 + j * (orderPaneWidth + 10));
                pane.setLayoutY(10 + k * (orderPaneHeigth + 10));
                departmentView.getChildren().add(pane);
                if (counter == i-1)
                {
                    break outerloop;
                }

                counter++;

            }

        }

    }
    public void extentAnchorPaneY(AnchorPane departmentAnchorPane, BorderPane borderP){
        departmentAnchorPane.setTopAnchor(borderP, orderPaneWidth);
        departmentAnchorPane.setLeftAnchor(borderP, orderPaneWidth);
        departmentAnchorPane.setRightAnchor(borderP, orderPaneWidth);
        departmentAnchorPane.setBottomAnchor(borderP, orderPaneWidth);
        borderP.getChildren().addAll();  
    }
    
    
    public void extentAnchorPaneX(){
        
        
    }
    
    
    
    public List<Worker> updateListViewWorkersAssigned() throws IOException {
        return ptl.getWorkersFromDB();
    }

   
}
