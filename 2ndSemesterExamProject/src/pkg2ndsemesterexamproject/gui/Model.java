/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.gui;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import pkg2ndsemesterexamproject.be.Department;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
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
import javafx.util.Duration;
import jdk.nashorn.internal.objects.NativeDate;
import pkg2ndsemesterexamproject.be.DepartmentTask;
import pkg2ndsemesterexamproject.be.IDepartmentTask;
import pkg2ndsemesterexamproject.be.IWorker;
import pkg2ndsemesterexamproject.be.Order;
import pkg2ndsemesterexamproject.bll.PassThrough;
import pkg2ndsemesterexamproject.gui.controller.ProjectOverViewController;
import pkg2ndsemesterexamproject.bll.IPassthrough;

/**
 *
 * @author andreas
 */
public class Model {

    private IPassthrough ptl;
    private final double orderPaneWidth = 200;
    private final double orderPaneHeigth = 150;
    private final int tmpListSize = 200;
    private final int minMargenEdgeX = 25;
    private final int minMargenEdgeY = 10;
    private final int minMargenX = 20;
    private final int minMargenY = 10;
    private long lastTime = 0;
    private final Timeline animation;
    private AnchorPane anchorPane;
    private BorderPane borderPane;
    private List<Pane> panes;

    public Model() throws IOException {
        panes = new ArrayList();
        ptl = new PassThrough();
        animation = new Timeline(new KeyFrame(Duration.seconds(0.1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                anchorPane.getChildren().clear();
                extentAnchorPaneX(anchorPane, borderPane);
                extentAnchorPaneY(anchorPane);
                placeOrderInUI(anchorPane);

            }
        }));
        animation.setCycleCount(1);
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
        //Indtil vi har faktiske ordrer
        if (panes.isEmpty()) {
            System.out.println("Generation");
            for (int i = 0; i < tmpListSize; i++) {
                panes.add(createOrderInGUI());
            }
        }
        double viewHeight = departmentView.getPrefHeight();
        double viewWidth = departmentView.getPrefWidth();

        double numberOfPanes = viewWidth / (orderPaneWidth + minMargenX);
        int xNumberOfPanes = (int) (numberOfPanes);
        double rest = (numberOfPanes - xNumberOfPanes) * orderPaneWidth - minMargenEdgeX * 2;
        int counter = 0;

        outerloop:
        for (int k = 0; k < panes.size(); k++) {

            for (int j = 0; j < xNumberOfPanes; j++) {
                //Pane pane = createOrderInGUI();
                panes.get(counter).setLayoutX(minMargenEdgeX + j * (orderPaneWidth + minMargenX));
                panes.get(counter).setLayoutY(minMargenEdgeY + k * (orderPaneHeigth + minMargenY));
                departmentView.getChildren().add(panes.get(counter));
                if (counter == panes.size() - 1) {
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
        double numberOfPanes = viewWidth / (orderPaneWidth + minMargenX);
        int xNumberOfPanes = (int) (numberOfPanes);
        int yNumberOfPanes = (int) (panes.size() / xNumberOfPanes);
        yNumberOfPanes += 1;
        System.out.println("Number of panes: " + yNumberOfPanes + " calcheight : " + (yNumberOfPanes * orderPaneHeigth + minMargenY * yNumberOfPanes));
        anchorP.setPrefHeight(yNumberOfPanes * orderPaneHeigth + minMargenY * yNumberOfPanes);
        
    }

    public List<IWorker> updateListViewWorkersAssigned() throws IOException, SQLException {
        return ptl.getWorkersFromDB();
    }

    public void msOnDepartmentView(AnchorPane departmentView, BorderPane borderPane) {
        anchorPane = departmentView;
        this.borderPane = borderPane;
        animation.play();
    }

    public void ChangeColour(Circle circle) {
        List<IDepartmentTask> departmentTask = new ArrayList();
        for (IDepartmentTask IdepartmentTask : departmentTask) {
            if (IdepartmentTask.getFinishedOrder() == true) {
                circle.setFill(Paint.valueOf("Green"));
            } else {

                circle.setFill(Paint.valueOf("Red"));
            }
        }
    }
}

//        long timeDiff = 0;
//        long currentTime = System.currentTimeMillis();
//        if (lastTime != 0 && currentTime != 0) {
//            
//            timeDiff = currentTime - lastTime;
//            System.out.println(timeDiff);
//            if (timeDiff >= 25) {
//                System.out.println(timeDiff);
//            }
//        }
//        lastTime = currentTime;
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
