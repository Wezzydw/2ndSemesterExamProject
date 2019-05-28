/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pkg2ndsemesterexamproject.be.IDepartmentTask;
import pkg2ndsemesterexamproject.be.IProductionOrder;
import pkg2ndsemesterexamproject.be.IWorker;
import pkg2ndsemesterexamproject.gui.OverViewModel;

/**
 * FXML Controller class
 *
 * @author andreas
 */
public class ProjectOverViewController implements Initializable {

    private ObservableList<IWorker> allWorkers = FXCollections.observableArrayList();
    private IDepartmentTask departmentTask;
    private IProductionOrder productionOrder;

    private OverViewModel model;
    private Label lblOrder;
    @FXML
    private Label lblClock;
    @FXML
    private BorderPane borderPane;
    private ExecutorService executor;
    @FXML
    private ListView<IWorker> lstView;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private TabPane tabPane;
    private Text txtCustomer;
    private Text txtOrder;
    private Text txtStartDate;
    private Text txtEndDate;
    private Text txtActual;

    /**
     * Initializes the controller class.
     */
    @Override

    public void initialize(URL url, ResourceBundle rb) {
        borderPane.setStyle("-fx-background-color:grey");
        try {

            model = new OverViewModel();
        } catch (IOException ex) {
            Logger.getLogger(ProjectOverViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        updateListViewWorkersAssigned();

    }

    /**
     * denne metode kalder clockUpdate så at vores clock i viewet bliver sat, og
     * den rigtige tid vises.
     */
    public void startClock() {
        executor = Executors.newSingleThreadExecutor();
        executor.submit(()
                -> {
            clockUpdate();
        });
    }

    /**
     * denne metode stopper tråden der holder vores clock kørende
     */
    public void closeWindow() {
        executor.shutdownNow();
    }

    @FXML
    private void orderIsDone(ActionEvent event) {
        Button btnYes = new Button("Yes");
        Button btnNo = new Button("No");
        Label lblTxt = new Label("Are you 100% sure that you are done with\n this DepartmentTask");
        lblTxt.setLayoutX(40);
        lblTxt.setLayoutY(80);
        btnYes.setLayoutX(100);
        btnYes.setLayoutY(125);
        btnNo.setLayoutX(200);
        btnNo.setLayoutY(125);

        Pane root = new Pane();
        root.getChildren().addAll(btnYes, btnNo, lblTxt);

        Scene scene = new Scene(root, 300, 250);
        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        btnYes.setOnAction((ActionEvent event1)
                -> {
            try {
                model.orderIsDone(departmentTask, productionOrder);
                primaryStage.close();
                Stage stage11 = (Stage) lblOrder.getScene().getWindow();
                stage11.close();

            } catch (SQLException ex) {
                Button btnOk = new Button("Ok");

                Label lblNoConnection = new Label("There is no connection to the DB");
                lblNoConnection.setLayoutX(40);
                lblNoConnection.setLayoutY(80);
                btnOk.setLayoutX(100);
                btnOk.setLayoutY(125);

                Pane root2 = new Pane();
                root2.getChildren().addAll(btnOk, lblNoConnection);

                Scene scene2 = new Scene(root2, 300, 250);
                Stage primaryStage2 = new Stage();
                primaryStage2.setScene(scene2);
                btnOk.setOnAction((ActionEvent event3)
                        -> {
                    primaryStage2.close();
                    Stage stage1 = (Stage) lblTxt.getScene().getWindow();
                    stage1.close();
                });
                primaryStage2.show();

                Logger.getLogger(ProjectOverViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        btnNo.setOnAction((ActionEvent event2)
                -> {
            primaryStage.close();
        });
        primaryStage.show();
    }

    /**
     * denne metode tager fat i localdatetime og får herved localtime. If
     * statementsne, sørger for at vi får tiden vist med de rigtige decimaler.
     */
    private void clockUpdate() {
        try {
            while (true) {
                Platform.runLater(()
                        -> {
                    String sec = "";
                    String min = "";
                    String hour = "";
                    int hours = LocalDateTime.now().getHour();
                    int minute = LocalDateTime.now().getMinute();
                    int second = LocalDateTime.now().getSecond();
                    sec = "" + second;
                    min = "" + minute;
                    hour = "" + hours;
                    if (second < 10) {
                        sec = "0" + second;
                    }
                    if (minute < 10) {
                        min = "0" + minute;
                    }
                    if (hours < 10) {
                        hour = "0" + hours;
                    }
                    String clock = hour + ":" + min + ":" + sec;
                    lblClock.setText(clock);
                });
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (InterruptedException ex) {
            System.out.println("Closed Window");
        }
    }

    /**
     *
     */
    private void updateListViewWorkersAssigned() {
        allWorkers.clear();
        try {
            for (IWorker iWorker : model.updateListViewWorkersAssigned()) {
                allWorkers.add(iWorker);
            }
        } catch (IOException ex) {
            Logger.getLogger(ProjectOverViewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ProjectOverViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        lstView.setItems(allWorkers);
    }

    public void setOrder(IDepartmentTask dt, IProductionOrder po) {
        this.departmentTask = dt;
        this.productionOrder = po;
    }

    public void setData(IDepartmentTask dt, IProductionOrder po) {
        txtCustomer = new Text("Customer: " + po.getCustomer().toString());
        txtOrder = new Text("Order number: " + po.getOrder().toString());
        txtStartDate = new Text(dt.getStartDate().format(DateTimeFormatter.ofPattern("dd/MM/YYYY")));
        txtEndDate = new Text(dt.getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/YYYY")));
        txtActual = new Text("Estimated progress");

        txtCustomer.setLayoutX(5);
        txtCustomer.setLayoutY(195);
        txtOrder.setLayoutX(5);
        txtOrder.setLayoutY(140);
        txtStartDate.setLayoutX(5);
        txtStartDate.setLayoutY(370);
        txtEndDate.setLayoutX(495);
        txtEndDate.setLayoutY(370);
        txtActual.setLayoutX(230);
        txtActual.setLayoutY(370);

        txtCustomer.getStyleClass().add("fancytext");
        txtOrder.getStyleClass().add("fancytext");
        txtStartDate.getStyleClass().add("fancytext2");
        txtEndDate.getStyleClass().add("fancytext2");
        txtActual.getStyleClass().add("fancytext2");

        int indexOfDepartment = 0;
        int counter = 0;

        for (IDepartmentTask tasks : po.getDepartmentTasks()) {
            if (tasks.equals(dt)) {
                indexOfDepartment = counter;
            }
            counter++;
        }
        for (int i = 0; i <= indexOfDepartment; i++) {
            Tab ta = new Tab();
            ta.setText(po.getDepartmentTasks().get(i).getDepartment().toString());
            if (po.getDepartmentTasks().get(i).getFinishedOrder()) {
                ta.setStyle("-fx-background-color: Green;");

            } else {
                ta.setStyle("-fx-background-color: Red;");
            }
            tabPane.getTabs().add(ta);
        }

        Pane progress = new Pane();
        progress.setMaxSize(585, 20);
        Canvas canvas = new Canvas();
        canvas.setHeight(20);
        canvas.setWidth(585);
        canvas.setLayoutX(5);
        canvas.setLayoutY(376);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.GREEN);
        Long daysBetween = ChronoUnit.DAYS.between(dt.getStartDate(), dt.getEndDate());
        int progressInterval = (int) (585 / daysBetween);
        LocalDateTime todayIs = LocalDateTime.now();
        Long startToNow = ChronoUnit.DAYS.between(dt.getStartDate(), todayIs);
        gc.fillRect(0, 0, progressInterval * startToNow, 20);
        gc.setStroke(Color.BLACK);
        gc.strokeRect(0, 0, 585, 20);
        gc.setFill(Color.WHITE);
        gc.fillRect(progressInterval * startToNow, 0, 585, 20);

        mainPane.getChildren().add(canvas);
        mainPane.getChildren().addAll(txtCustomer, txtEndDate, txtStartDate, txtOrder, txtActual);
    }
}
