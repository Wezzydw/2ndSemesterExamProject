/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.gui;

import com.jfoenix.controls.JFXProgressBar;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private Thread thread = null;

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

    public void scanFolderForNewFiles()
    {
        managerPassThrough.scanFolderForNewFiles();
    }

    public void checkScanFolder(JFXProgressBar scanProgress)
    {
   
        if (thread == null || !thread.isAlive())
        {

            thread = new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    for (int i = 1; i <= 100; i++)
                    {
                        scanProgress.setProgress(0.01 * i);
                        try
                        {
                            Thread.sleep(1500 / 100);
                        } catch (InterruptedException ex)
                        {
                            throw new RuntimeException(ex);
                        }
                    }
                    scanProgress.setVisible(false);
                }
            });
        }
        if (!thread.isAlive())
        {
            scanProgress.setVisible(true);
            thread.start();
        }

    }

    }
