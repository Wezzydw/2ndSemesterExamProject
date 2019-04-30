/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.gui.controller;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import pkg2ndsemesterexamproject.be.Department;

/**
 * FXML Controller class
 *
 * @author andreas
 */
public class DepartmentScreenViewController implements Initializable
{    
    private Department currentDepartment;
    
    @FXML
    private ComboBox<?> comboBox;
    @FXML
    private Label lblDate;
    @FXML
    private JFXTextField txtSearchfield;
    @FXML
    private AnchorPane departmentAnchorPane;
    @FXML
    private Label lblText;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        LocalDate date = LocalDate.now();
        lblDate.setText(date.toString());
        departmentAnchorPane.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println("Olde: " + oldValue + " New: " + newValue);
            }
        });{
        
    }
    }    

    @FXML
    private void searchBar(KeyEvent event) {
    }
    
    public void setDepartment(Department department){
        lblText.setText(department.getName());
    }
    
}
