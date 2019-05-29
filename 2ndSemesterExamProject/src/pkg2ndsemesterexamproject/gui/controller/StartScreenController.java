
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.gui.controller;

import java.io.IOException;
import pkg2ndsemesterexamproject.be.Department;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import pkg2ndsemesterexamproject.be.IDepartment;
import pkg2ndsemesterexamproject.gui.StartScreenModel;

/**
 * FXML Controller class
 *
 * @author andreas
 */
public class StartScreenController implements Initializable
{

    @FXML
    private MenuButton menuButton;
    @FXML
    private AnchorPane startAnchor;

    private StartScreenModel model;
    private List<IDepartment> allDepartments;
    @FXML
    private BorderPane bp;

    /**
     * Initializes the controller class. Den laver en ny model, og derefter
     * prøver den at få alle departments fra model.getAllDepartments hvis den
     * fanger en SQLExeption laver den et pop up vindue til sidst kaldes
     * setUpMenuButtons
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        startAnchor.getStyleClass().add("backgroundPicture");
        Text txt = new Text("Choose a department or manager:");
        txt.getStyleClass().add("startScreenText");
        txt.setTextAlignment(TextAlignment.CENTER);
        bp.setBottom(txt);

        try
        {
            model = new StartScreenModel();
            allDepartments = model.getAllDepartments();
        } catch (IOException | SQLException ex)
        {
            popUpScreen();
            System.out.println("IOExeption: " + ex);
        }
        setUpMenuButtons();
    }

    /**
     * Denne metode laver et event og laver menuItems for hver department der
     * er, disse Items gives onAction den event som er blevet lavet, til sidst
     * bliver alle knapperne tilføjet til menuButton
     */
    private void setUpMenuButtons()
    {
        EventHandler<ActionEvent> event1 = (ActionEvent e) ->
        {
            Department temp;
            MenuItem selectedItem = (MenuItem) e.getSource();
            String selectedItemName = selectedItem.getText();
            for (IDepartment allDepartment : allDepartments)
            {
                if (allDepartment.getName().equals(selectedItemName))
                {
                    temp = (Department) allDepartment;
                    selectDepartment(temp);
                }
            }
        };
        List<MenuItem> departmentBtns = new ArrayList();
        if (allDepartments != null)
        {
            allDepartments.add(new Department("Manager"));
            for (IDepartment depar : allDepartments)
            {
                MenuItem item = new MenuItem(depar.getName());//label skal være allDepartment.getName() fra BE laget 
                item.setOnAction(event1);
                departmentBtns.add(item);
            }
        }
        menuButton.getItems().addAll(departmentBtns);
    }

    /**
     * Denne metode skifter fra den nuværende FXML fil og kører ManagerOverview
     * FXML og dens controller
     */
    private void goToManagerScreen()
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/pkg2ndsemesterexamproject/gui/view/ManagerOverview.fxml"));
        try
        {
            loader.load();
        } catch (IOException ex)
        {
            System.out.println("Error" + ex);
        }
        ManagerOverviewController display = loader.getController();

        Parent p = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(p));
        Stage stage1 = (Stage) menuButton.getScene().getWindow();
        stage.showAndWait();
//        stage1.close();
    }

    /**
     * Denne metodes skifter fra den nuværende FXML fil og kører
     * DepartmentScreenView FXML og dens controller Den kører metoden
     * setDepartment() inde fra DepartmentScreenViewController med department
     * som parameter
     *
     * @param department som brugeren har valgt
     */
    private void goToDepartmentScreen(Department department)
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/pkg2ndsemesterexamproject/gui/view/DepartmentScreenView.fxml"));
        try
        {
            loader.load();
        } catch (IOException ex)
        {
            System.out.println("Error" + ex);
        }
        DepartmentScreenViewController display = loader.getController();
        display.setDepartment(department);
        Parent p = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(p));
        stage.getIcons().add(new Image("pkg2ndsemesterexamproject/gui/view/css/logo-13.jpg"));
        stage.setTitle("Start Screen");
        Stage stage1 = (Stage) menuButton.getScene().getWindow();
        stage.setFullScreen(true);
        stage.showAndWait();
//        stage1.close();
    }

    /**
     * Denne metode tager en department og ud fra dens navn kører metoden
     * goToManagerScreen() hvis dens navn er manager, ellers kører den metoden
     * goToDepartmentScreen() med department som en parameter
     *
     * @param department som brugeren vælger
     */
    private void selectDepartment(Department department)
    {
        if (department.getName().equals("Manager"))
        {
            goToManagerScreen();
        } else
        {
            goToDepartmentScreen(department);
        }
    }

    /**
     * Denne metode laver et pop up vindue, som fortæller at der ikke er
     * forbindelse til databasen
     */
    private void popUpScreen()
    {
        Button btnOk = new Button("Ok");

        Label lblNoConnection = new Label("There is no connection to the DB");
        lblNoConnection.setLayoutX(40);
        lblNoConnection.setLayoutY(80);
        btnOk.setLayoutX(100);
        btnOk.setLayoutY(125);

        Pane root2 = new Pane();
        root2.getChildren().addAll(btnOk, lblNoConnection);

        Scene scene = new Scene(root2, 300, 250);
        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        btnOk.setOnAction((ActionEvent event) ->
        {
            primaryStage.close();
        });
        primaryStage.setAlwaysOnTop(true);
        primaryStage.show();
    }
}
