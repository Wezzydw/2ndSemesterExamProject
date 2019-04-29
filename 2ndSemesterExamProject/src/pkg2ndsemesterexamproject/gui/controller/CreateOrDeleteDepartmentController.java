/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.gui.controller;

import com.jfoenix.controls.JFXTextField;
import com.sun.javafx.scene.control.SelectedCellsMap;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import pkg2ndsemesterexamproject.be.Department;

/**
 * FXML Controller class
 *
 * @author marce
 */
public class CreateOrDeleteDepartmentController implements Initializable
{

    @FXML
    private JFXTextField createNewDepartment;
    @FXML
    private ComboBox<Department> comboCreateBox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }

    @FXML
    private void createDepartment(ActionEvent event)
    {
        boolean equalChecks = false;
        if (!createNewDepartment.getText().isEmpty())
        {
            for (int i = 0; i < comboCreateBox.getItems().size(); i++)
            {
                if (createNewDepartment.getText().equals(comboCreateBox.getItems().get(i).getName()))
                {
                    equalChecks = true;
                }
            }
        }
        {
            if (!equalChecks)
            {
                Department dep1 = new Department(createNewDepartment.getText());
                createNewDepartment.clear();
                comboCreateBox.getItems().add(dep1);
            }
        }
    }

    @FXML
    private void deleteDepartment(ActionEvent event)
    {
        Department dep1 = comboCreateBox.getSelectionModel().getSelectedItem();
        comboCreateBox.getItems().remove(dep1);
    }

}
