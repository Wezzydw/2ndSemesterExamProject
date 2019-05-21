/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.gui;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import pkg2ndsemesterexamproject.be.IDepartmentTask;
import pkg2ndsemesterexamproject.be.IProductionOrder;
import pkg2ndsemesterexamproject.bll.PassThrough;
import pkg2ndsemesterexamproject.gui.controller.OrderOverViewController;
import pkg2ndsemesterexamproject.gui.controller.ProjectOverViewController;

/**
 *
 * @author marce
 */
public class ManagerModel
{

    private PassThrough managerPassThrough;
    private TableView tableView;

    public ManagerModel() throws IOException
    {
       
        managerPassThrough = new PassThrough();
    }

    public List<IProductionOrder> getProductionOrders() throws SQLException
    {
        return managerPassThrough.getAllProductionOrders();

    }


    public StringProperty stringConverter(String string)
    {
        return new SimpleStringProperty(string);
    }

    public ObservableList<IProductionOrder> getObservableProductionOrders() throws SQLException
    {
        ObservableList managerOBS = FXCollections.observableArrayList();
        managerOBS.addAll(getProductionOrders());
        return managerOBS;
    }


    public void scanFolderForNewFiles() {
        managerPassThrough.scanFolderForNewFiles();
    }
 
          
   

        
          

}
