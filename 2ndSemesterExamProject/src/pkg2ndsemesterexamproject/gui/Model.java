/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.gui;

import java.io.IOException;
import java.sql.SQLException;
import pkg2ndsemesterexamproject.be.Department;
import java.util.List;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import jdk.nashorn.internal.objects.NativeDate;
import pkg2ndsemesterexamproject.be.IWorker;
import pkg2ndsemesterexamproject.be.Order;
import pkg2ndsemesterexamproject.bll.IBLL;
import pkg2ndsemesterexamproject.bll.PassThrough;
import pkg2ndsemesterexamproject.gui.controller.ProjectOverViewController;

/**
 *
 * @author andreas
 */
public class Model {

    private IBLL ptl;
    private final double orderPaneWidth = 200;
    private final double orderPaneHeigth = 150;
    private final int tmpListSize = 100;
    private final int minMargenEdgeX = 25;
    private final int minMargenEdgeY = 10;
    private final int minMargenX = 20;
    private final int minMargenY = 10;
    private long lastTime = 0;

    public Model() throws IOException {
        ptl = new PassThrough();
    }

    public List<Department> getAllDepartments() {

        return null;
    }

    public void setMenuItems(MenuButton MenuButton, List<Department> allDepartments) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void orderIsDone(Order order) {
        ptl.sendOrderIsDone();
    }

    //public Pane createOrderInGUI(int orederNum, String startDate, String endDate){
    public Pane createOrderInGUI() {

        Pane orderPane = new Pane();
        orderPane.setMaxSize(200, 150);
        orderPane.setStyle("-fx-background-color: Yellow");
        Circle circle = new Circle(13);
        circle.setFill(Paint.valueOf("Green"));
        Label orderNum = new Label("Ordernumber: " + 12321312);
        Label customer = new Label("Customer: " + "Karl Kalashnikov");
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

        EventHandler<MouseEvent> event1 = (MouseEvent e)
                -> {

            goToOverview();
        };
        orderPane.setOnMousePressed(event1);

        orderPane.getChildren().addAll(circle, orderNum, startDate, endDate, customer);

        return orderPane;

    }

    private void goToOverview() {//skal nok også bruge en order eller noget, så vi kan få alt relevant information med 

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

    public void placeOrderInUI(AnchorPane departmentView) {
        double viewHeight = departmentView.getPrefHeight();
        double viewWidth = departmentView.getPrefWidth();

        double numberOfPanes = viewWidth / (orderPaneWidth + minMargenX);
        int xNumberOfPanes = (int) (numberOfPanes);
        double rest = (numberOfPanes - xNumberOfPanes) * orderPaneWidth - minMargenEdgeX * 2;
        System.out.println("Rest " + rest);
        int counter = 0;

        outerloop:
        for (int k = 0; k < tmpListSize; k++) {

            for (int j = 0; j < xNumberOfPanes; j++) {
                Pane pane = createOrderInGUI();
                pane.setLayoutX(minMargenEdgeX + j * (orderPaneWidth + minMargenX));
                pane.setLayoutY(minMargenEdgeY + k * (orderPaneHeigth + minMargenY));
                departmentView.getChildren().add(pane);
                if (counter == tmpListSize - 1) {
                    break outerloop;
                }

                counter++;

            }

        }

    }

    public void extentAnchorPaneX(AnchorPane anchorP, BorderPane borderP) {
        anchorP.setPrefWidth(borderP.getWidth() - 50);

    }

    public void extentAnchorPaneY(AnchorPane anchorP) {

        double viewWidth = anchorP.getPrefWidth();
        double numberOfPanes = viewWidth / orderPaneWidth;
        int yNumberOfPanes = (int) (numberOfPanes);
        yNumberOfPanes += 1;
        anchorP.setPrefHeight(yNumberOfPanes * orderPaneHeigth + 50 * yNumberOfPanes);

    }

    public List<IWorker> updateListViewWorkersAssigned() throws IOException, SQLException {

        return ptl.getWorkersFromDB();
    }

    public void msOnDepartmentView(AnchorPane departmentView, BorderPane borderPane) {
//        long timeDiff = 0;
//        long currentTime = System.currentTimeMillis();
//        if (lastTime != 0 && currentTime != 0) {
//            
//            timeDiff = currentTime - lastTime;
//            System.out.println(timeDiff);
//            if (timeDiff >= 25) {
//                System.out.println(timeDiff);
        departmentView.getChildren().clear();
        placeOrderInUI(departmentView);
        extentAnchorPaneX(departmentView, borderPane);
        extentAnchorPaneY(departmentView);
//            }

//        }
//        lastTime = currentTime;
    }

//        public void songListClicks(Song song)
//    {
//        long timeDiff = 0;
//        long currentTime = System.currentTimeMillis();
//
//        if (lastTime != 0 && currentTime != 0)
//        {
//            timeDiff = currentTime - lastTime;
//            if (timeDiff <= 215)
//            {
//                playNowSelectedSong(song);
//            }
//        }
//        lastTime = currentTime;
//    }
//    
}
