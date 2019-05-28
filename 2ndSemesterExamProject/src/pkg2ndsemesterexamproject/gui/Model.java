
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
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import pkg2ndsemesterexamproject.be.IDepartmentTask;
import pkg2ndsemesterexamproject.be.IProductionOrder;
import pkg2ndsemesterexamproject.bll.DataHandler;
import pkg2ndsemesterexamproject.bll.PassThrough;
import pkg2ndsemesterexamproject.gui.controller.ProjectOverViewController;
import pkg2ndsemesterexamproject.bll.IPassthrough;
import pkg2ndsemesterexamproject.bll.ISortStrategy;
import pkg2ndsemesterexamproject.bll.Search;

/**
 *
 * @author andreas
 */
public class Model {

    private double orderPaneWidth = 200;
    private double orderPaneHeigth = 150;
    private final int minMargenEdgeX = 25;
    private final int minMargenEdgeY = 10;
    private final int minMargenX = 20;
    private final int minMargenY = 10;

    private final AnchorPane anchorPane;
    private List<Pane> stickyNotes;
    private final DataHandler dataHandler;
    private String selectedDepartmentName;
    private String searchString = "";
    private ISortStrategy strategy;
    private double scalePost = 1;
    private List<IProductionOrder> orders = null;
    private int oldHash = 0;

    public Model(AnchorPane anchorPane) throws IOException, SQLException {
        this.anchorPane = anchorPane;
        stickyNotes = new ArrayList();
        dataHandler = new DataHandler();
    }

    public void setSelectedDepartmentName(String name) {
        selectedDepartmentName = name;
    }

    /*
    Metoden registerer om ordren er færdig og sender den videre ned igennem lagene
     */
 /*
    Denne metode laver vores departmentview/panes samt tilføjelser labels med de 
    nødvendige informationer, en cirkel med status på ordre, samt en bar over 
    projectets nuværende situation
     */
    //public Pane createOrderInGUI(int orederNum, String startDate, String endDate){
    public void zoomControl(double sliderVal) {
        double scale = sliderVal / 100;
        orderPaneHeigth = 150 * scale;
        orderPaneWidth = 200 * scale;
        scalePost = scale;
    }

    /*
    skifter fra det generelle overview over alle departmenttask til en specific 
    departmenttask.
     */
    private void goToOverview(IProductionOrder po, IDepartmentTask dpt) throws IOException {//skal nok også bruge en order eller noget, så vi kan få alt relevant information med 

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/pkg2ndsemesterexamproject/gui/view/ProjectOverView.fxml"));
        try {
            loader.load();
        } catch (IOException ex) {
            throw new IOException(ex);
        }
        ProjectOverViewController display = loader.getController();
        display.startClock();
        display.setData(dpt, po);
        display.setOrder(dpt, po);
        Parent p = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(p));
        stage.setResizable(false);
        stage.showAndWait();
        display.closeWindow();
        stage.close();
    }

    /*
    Denne metode sørge for at vi kan placere alle departmentTask/panes i vores
    departmentview uden begrænsninger, samt gør designet mere brugervenligt.
     */
    public void placeOrderInUI() throws SQLException {
        if (selectedDepartmentName != null && stickyNotes != null) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        orders = dataHandler.getAllRelevantProductionOrders(selectedDepartmentName, searchString, strategy);
                    } catch (SQLException | IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            if (orders.hashCode() != oldHash) {
                                fillStickyNotes(orders);
                                anchorPane.getChildren().clear();
                                placeStickyNotes(true);

                            } else {
                                placeStickyNotes(false);
                            }
                            anchorPane.setPrefHeight(calcAnchorPaneY(anchorPane.getWidth()));
                            oldHash = orders.hashCode();
                        }
                    });
                }
            });
            t.start();
        }
    }

    public void fillStickyNotes(List<IProductionOrder> orders) {
        stickyNotes.clear();
        for (IProductionOrder productionOrders : orders) {
            Pane p = CreatePane.createOrderInGUI(productionOrders, dataHandler.getTaskForDepartment(productionOrders, selectedDepartmentName), scalePost);
            EventHandler<MouseEvent> event1 = (MouseEvent e)
                    -> {
                if (e.getClickCount() == 2) {
                    try {
                        goToOverview(productionOrders, dataHandler.getTaskForDepartment(productionOrders, selectedDepartmentName));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            };
            p.setOnMousePressed(event1);
            stickyNotes.add(p);
        }
    }

    public void placeStickyNotes(boolean isToBeAdded) {
        int counter = 0;
        outerloop:
        for (int k = 0; k < stickyNotes.size(); k++) {
            for (int j = 0; j < calcNumberOfXPanes(anchorPane.getWidth()); j++) {
                stickyNotes.get(counter).setLayoutX(minMargenEdgeX + j * (orderPaneWidth + minMargenX));
                stickyNotes.get(counter).setLayoutY(minMargenEdgeY + k * (orderPaneHeigth + minMargenY));

                if (isToBeAdded) {
                    anchorPane.getChildren().add(stickyNotes.get(counter));
                }

                if (counter == stickyNotes.size() - 1) {
                    break outerloop;
                }

                counter++;

            }

        }
    }

    /*
    denne metode justere på vores y-akse, således at programmet udvider sig selv,
    hvis nødvendigt for at få alle efterspurgte stickyNotes puttes ind i viewet.
     */
    public double calcAnchorPaneY(double anchorWidth) {
        int xNumberOfPanes = calcNumberOfXPanes(anchorWidth);
        if (xNumberOfPanes == 0) {
            xNumberOfPanes = 1;
        }

        int yNumberOfPanes = (int) (stickyNotes.size() / xNumberOfPanes);
        if (stickyNotes.size() % xNumberOfPanes != 0) {
            yNumberOfPanes += 1;
        }
        return (yNumberOfPanes * orderPaneHeigth + minMargenY * yNumberOfPanes);
    }

    public int calcNumberOfXPanes(double anchorWidth) {
        double viewWidth = anchorWidth;
        double numberOfPanes = viewWidth / (orderPaneWidth + minMargenX);
        int xNumberOfPanes = (int) (numberOfPanes);
        return xNumberOfPanes;
    }

    /*
    returnere alle workers fra databasen, så vi kan finde ud af hvilke projekter,
    de er tilmeldt.
     */
 /*
    Metoden gør at vi kan flowcontrolle, så der max kan blive opdateret 10 gange
    i sekundet, for at undgå konstante updates, der ville skabe delay i programmet
     */
    public void setSortStrategy(ISortStrategy strategy) {
        this.strategy = strategy;
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
        }
    }

    public void setSearchString(String string) {
        searchString = string;
    }

    public void runDataCheckInDataHandler() throws SQLException {
        dataHandler.runDataCheck();
    }

    public void resizeStickyNotes() {
        fillStickyNotes(orders);
        anchorPane.getChildren().clear();
        placeStickyNotes(true);
    }
}
