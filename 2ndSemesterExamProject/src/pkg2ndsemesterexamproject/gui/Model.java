package pkg2ndsemesterexamproject.gui;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import pkg2ndsemesterexamproject.be.IDepartmentTask;
import pkg2ndsemesterexamproject.be.IProductionOrder;
import pkg2ndsemesterexamproject.bll.DataHandler;
import pkg2ndsemesterexamproject.gui.controller.ProjectOverViewController;
import pkg2ndsemesterexamproject.bll.ISortStrategy;

public class Model {

    private double orderPaneWidth = 200;
    private double orderPaneHeigth = 150;
    private static final int minMargenEdgeX = 25;
    private static final int minMargenEdgeY = 10;
    private static final int minMargenX = 20;
    private static final int minMargenY = 10;

    private final AnchorPane anchorPane;
    private final DataHandler dataHandler;
    private double scalePost = 1;
    private int oldHash = 0;

    private List<Pane> stickyNotes;
    private List<IProductionOrder> orders = null;
    private String selectedDepartmentName;
    private String searchString = "";
    private ISortStrategy strategy;

    public Model(AnchorPane anchorPane) throws IOException, SQLException {
        this.anchorPane = anchorPane;
        stickyNotes = new ArrayList();
        dataHandler = new DataHandler();
    }

    /**
     * Sætter selectedDepartmentName til den param name
     *
     * @param name
     */
    public void setSelectedDepartmentName(String name) {
        selectedDepartmentName = name;
    }

    /**
     * Denne metode udregner scale værdien, ud fra slider-værdien.
     *
     * @param sliderVal slider værdi, der bliver sendt fra GUI'en når brugeren
     * trækker i slideren.
     */
    public void zoomControl(double sliderVal) {
        double scale = sliderVal / 100;
        orderPaneHeigth = 150 * scale;
        orderPaneWidth = 200 * scale;
        scalePost = scale;
    }

    /**
     * skifter fra det generelle overview over alle departmenttask til en
     * specific departmenttask.
     *
     * @param po et objekt af ProductionOrder
     * @param dpt et objekt af DepartmentTask
     * @throws IOException
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
        stage.getIcons().add(new Image("pkg2ndsemesterexamproject/gui/view/css/logo-13.jpg"));
        stage.setTitle("DepartmentTask: Ordernum: " + po.getOrder().getOrderNumber()
                + " Department: " + dpt.getDepartment().getName());
        stage.setResizable(false);
        stage.showAndWait();
        display.closeWindow();
        stage.close();
    }

    /**
     * Denne metode sørger for at vi kan placere alle departmentTask/panes i
     * vores departmentview uden begrænsninger, samt gør designet mere
     * brugervenligt.
     */
    public void placeOrderInUI() {
        if (selectedDepartmentName != null && stickyNotes != null) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        orders = dataHandler.getAllRelevantProductionOrders(
                                selectedDepartmentName, searchString, strategy);
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
                            anchorPane.setPrefHeight(calcAnchorPaneY(
                                    anchorPane.getWidth()));
                            oldHash = orders.hashCode();
                        }
                    });
                }
            });
            t.start();
        }
    }

    /**
     * metoden tager fat i vores liste af productionorders og indsætter alle de
     * stickynotes fra den ordre af.
     *
     * @param orders er det der indeholder den information der gør muligt at
     * vide hvor mange stickynotes der skal placeres
     */
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

    /**
     * denne metode gør at stickynotes bliver placeret med korrekt på skærmen,
     * som udregnes af skærmens størrelse, så layouttet er brugervenligt
     *
     * @param isToBeAdded hvis denne er true skal tilføjes stickynotes tilføjes
     * til skærmen. er den false bliver der kun flyttet rundt på notesne.
     */
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

    /**
     * denne metode justere på vores y-akse, således at programmet udvider sig
     * selv, hvis nødvendigt for at få alle efterspurgte stickyNotes puttes ind
     * i viewet.
     *
     * @param anchorWidth bestemmer det antal panes der er plads til.
     * @return antal af panes
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

    /**
     * denne metode beregner det antal panes der er plads til i vores view
     *
     * @param anchorWidth
     * @return det antal af panes
     */
    public int calcNumberOfXPanes(double anchorWidth) {
        double viewWidth = anchorWidth;
        double numberOfPanes = viewWidth / (orderPaneWidth + minMargenX);
        int xNumberOfPanes = (int) (numberOfPanes);
        return xNumberOfPanes;
    }

    /**
     * denne metode sætter this strategy
     *
     * @param strategy
     */
    public void setSortStrategy(ISortStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * sætter searchstring
     *
     * @param string
     */
    public void setSearchString(String string) {
        searchString = string;
    }

    /**
     * kalder dataHandler runDataCheck
     *
     * @throws SQLException
     */
    public void runDataCheckInDataHandler() throws SQLException {
        dataHandler.runDataCheck();
    }

    /**
     * metoden fylder sticky notes med alt information fra orders
     */
    public void resizeStickyNotes() {
        fillStickyNotes(orders);
        anchorPane.getChildren().clear();
        placeStickyNotes(true);
    }
}
