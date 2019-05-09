
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.gui;

import java.io.IOException;
import java.sql.SQLException;

import java.util.ArrayList;
import pkg2ndsemesterexamproject.be.Department;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import pkg2ndsemesterexamproject.be.IDepartment;
import pkg2ndsemesterexamproject.be.IDepartmentTask;
import pkg2ndsemesterexamproject.be.IOrder;
import pkg2ndsemesterexamproject.be.IProductionOrder;
import pkg2ndsemesterexamproject.be.IWorker;
import pkg2ndsemesterexamproject.bll.DataHandler;
import pkg2ndsemesterexamproject.bll.PassThrough;
import pkg2ndsemesterexamproject.gui.controller.ProjectOverViewController;
import pkg2ndsemesterexamproject.bll.IPassthrough;
import pkg2ndsemesterexamproject.bll.Search;

/**
 *
 * @author andreas
 */
public class Model {

    private IPassthrough ptl;
    private final double orderPaneWidth = 200;
    private final double orderPaneHeigth = 150;
    private final int minMargenEdgeX = 25;
    private final int minMargenEdgeY = 10;
    private final int minMargenX = 20;
    private final int minMargenY = 10;
    private long lastTime = 0;
    private final Timeline guiUpdateLimit;
    private AnchorPane anchorPane;
    private BorderPane borderPane;
    private List<Pane> stickyNotes;
    private DataHandler dataHandler;
    private String selectedDepartmentName;
    private Search search;
    private String searchString = "";

    public Model() throws IOException {
        stickyNotes = new ArrayList();
        ptl = new PassThrough();
        dataHandler = new DataHandler();
        guiUpdateLimit = initializeGUIUpdateLimit();
        guiUpdateLimit.setCycleCount(1);
        search = new Search();
    }

    public Timeline initializeGUIUpdateLimit() {
        return new Timeline(new KeyFrame(Duration.seconds(0.1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //anchorPane.getChildren().clear();
                extentAnchorPaneX(anchorPane, borderPane);
                extentAnchorPaneY(anchorPane);
                try {
                    placeOrderInUI(anchorPane);
                } catch (SQLException ex) {
                    Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }));

    }

    public void setSelectedDepartmentName(String name) {
        selectedDepartmentName = name;
    }

    /*
    Henter alle departments fra databasen
     */
    public List<IDepartment> getAllDepartments() throws SQLException {
        return ptl.getAllDepartments();
    }

    /*
    
     */
    public void setMenuItems(MenuButton MenuButton, List<Department> allDepartments) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /*
    Metoden registerer om ordren er færdig og sender den videre ned igennem lagene
     */
    public void orderIsDone(IDepartmentTask dt, IProductionOrder po) throws SQLException {
        ptl.sendOrderIsDone(dt, po);
    }

    /*
    Denne metode laver vores departmentview/panes samt tilføjelser labels med de 
    nødvendige informationer, en cirkel med status på ordre, samt en bar over 
    projectets nuværende situation
     */
    //public Pane createOrderInGUI(int orederNum, String startDate, String endDate){
    public Pane createOrderInGUI(IProductionOrder po, IDepartmentTask dpt) {//IProductionOrder po, IDepartmentTask dp

        Label orderNum = new Label(po.getOrder().toString());
        Label customer = new Label("Customer: " + po.getCustomer().getName());
        Label startDate = new Label(dpt.getStartDate().toLocalDate().toString());
        Label endDate = new Label(dpt.getEndDate().toLocalDate().toString());
        Pane orderPane = new Pane();
        orderPane.setMaxSize(200, 150);
        orderPane.getStyleClass().add("pane");
        //orderPane.setStyle("-fx-background-color: Yellow");
        Circle circle = new Circle(13);
        circle.setFill(Paint.valueOf("Green"));
//        Label orderNum = new Label("Ordernumber: " + 12321312);
//        Label customer = new Label("Customer: " + "Karl Kalashnikov");
//        Label startDate = new Label("29-04-2019");
//        Label endDate = new Label("09-05-2019");
        orderNum.getStyleClass().add("label");
        customer.getStyleClass().add("label");
        startDate.getStyleClass().add("label");
        endDate.getStyleClass().add("label");

        Pane progress = new Pane();
        progress.setMaxSize(175, 15);
        Canvas canvas = new Canvas();
        canvas.setHeight(15);
        canvas.setWidth(175);
        canvas.setLayoutX(13);
        canvas.setLayoutY(130);

        GraphicsContext gc = canvas.getGraphicsContext2D();

//        Long daysBetween = ChronoUnit.DAYS.between(dt.getStartDate(), dt.getEndDate());
//        int progressInterval = (int) (175 / daysBetween);
//        LocalDateTime todayIs = LocalDateTime.now();
//        Long startToNow = ChronoUnit.DAYS.between(dt.getStartDate(), todayIs);
//        gc.fillRect(0, 0, progressInterval * startToNow, 20);
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

    /*
    skifter fra det generelle overview over alle departmenttask til en specific 
    departmenttask.
     */
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

    /*
    Denne metode sørge for at vi kan placere alle departmentTask/panes i vores
    departmentview uden begrænsninger, samt gør designet mere brugervenligt.
     */
    public void placeOrderInUI(AnchorPane departmentView) throws SQLException {

        if (selectedDepartmentName != null) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    List<IProductionOrder> orders = null;
                    try {
                        orders = dataHandler.getAllRelevantProductionOrders(selectedDepartmentName, searchString);
                    } catch (SQLException ex) {
                        System.out.println("Tester22");
                    }
                    if (orders != null) {
                        stickyNotes.clear();
                            for (IProductionOrder productionOrders : orders) {
                                stickyNotes.add(createOrderInGUI(productionOrders, dataHandler.getTaskForDepartment(productionOrders, selectedDepartmentName)));
                            
                        }

                        double viewHeight = departmentView.getPrefHeight();
                        double viewWidth = departmentView.getPrefWidth();

                        double numberOfPanes = viewWidth / (orderPaneWidth + minMargenX);
                        int xNumberOfPanes = (int) (numberOfPanes);
                        double rest = (numberOfPanes - xNumberOfPanes) * orderPaneWidth - minMargenEdgeX * 2;

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                int counter = 0;
                                departmentView.getChildren().clear();
                                outerloop:
                                for (int k = 0; k < stickyNotes.size(); k++) {

                                    for (int j = 0; j < xNumberOfPanes; j++) {
                                        //Pane pane = createOrderInGUI();
                                        stickyNotes.get(counter).setLayoutX(minMargenEdgeX + j * (orderPaneWidth + minMargenX));
                                        stickyNotes.get(counter).setLayoutY(minMargenEdgeY + k * (orderPaneHeigth + minMargenY));
                                        departmentView.getChildren().add(stickyNotes.get(counter));
                                        if (counter == stickyNotes.size() - 1) {
                                            break outerloop;
                                        }

                                        counter++;

                                    }

                                }
                            }
                        });
                    }
                }
            });
            t.start();
        } else {
            System.out.println("Else");
        }
    }

    /*
    Metoden her trækker -50 på borderpanede for at undgå en sidescroller så,
    vi altid har samme x-værdi på programmet, som medfører der altid er samme 
    antal stickyNotes henaf x-aksen.
     */
    public void extentAnchorPaneX(AnchorPane anchorP, BorderPane borderP) {
        anchorP.setPrefWidth(borderP.getWidth() - 50);

    }

    /*
    denne metode justere på vores y-akse, således at programmet udvider sig selv,
    hvis nødvendigt for at få alle efterspurgte stickyNotes puttes ind i viewet.
     */
    public void extentAnchorPaneY(AnchorPane anchorP) {
        double viewWidth = anchorP.getPrefWidth();
        double numberOfPanes = viewWidth / (orderPaneWidth + minMargenX);
        int xNumberOfPanes = (int) (numberOfPanes);
        if (xNumberOfPanes == 0) {
            xNumberOfPanes++;
        }

        int yNumberOfPanes = (int) (stickyNotes.size() / xNumberOfPanes);
        yNumberOfPanes += 1;
        anchorP.setPrefHeight(yNumberOfPanes * orderPaneHeigth + minMargenY * yNumberOfPanes);

    }

    /*
    returnere alle workers fra databasen, så vi kan finde ud af hvilke projekter,
    de er tilmeldt.
     */
    public List<IWorker> updateListViewWorkersAssigned() throws IOException, SQLException {
        return ptl.getWorkersFromDB();
    }

    /*
    Metoden gør at vi kan flowcontrolle, så der max kan blive opdateret 10 gange
    i sekundet, for at undgå konstante updates, der ville skabe delay i programmet
     */
    public void msOnDepartmentView(AnchorPane departmentView, BorderPane borderPane) {
        anchorPane = departmentView;
        this.borderPane = borderPane;
        guiUpdateLimit.play();
    }

    /*
    Denne metode sørge for at visuelt vise projectes tilstand i form at en farve
    beskrivelse på en cirkel, der viser hvad status er på projectet.
     */
    public void ChangeColour(Circle circle) {
        List<IDepartmentTask> departmentTask = new ArrayList();
        for (IDepartmentTask IdepartmentTask : departmentTask) {
            if (IdepartmentTask.getFinishedOrder() == true) {
                circle.setFill(Paint.valueOf("Green"));

            }
            //if (IdepartmentTask.getStartDate().isBefore(otherTime));
            //circle.setFill(Paint.valueOf("Yellow"));

//            else {
//                circle.setFill(Paint.valueOf("Red"));
//            }
//            
        }
    }

    /*
    denne metode viser brugeren om de har forbindelse til databasen, og hvis ikke
    sendes der en meddelse om at IT-service burde kontaktes.
     */
    public boolean checkConnection() {

        return true;
    }

    public void setSearchString(String string) {
        searchString = string;
    }
}
