/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.gui;

import com.jfoenix.controls.JFXProgressBar;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pkg2ndsemesterexamproject.be.IProductionOrder;
import pkg2ndsemesterexamproject.bll.PassThrough;

/**
 *
 * @author marce
 */
public class ManagerModel {

    private PassThrough managerPassThrough;
    private Thread thread = null;
    private static final int progressRuntime = 1500;
    private static final int progressRuns = 100;

    public ManagerModel() throws IOException {

        managerPassThrough = new PassThrough();
    }
/** kalder managerpassthrough metoden 
 * @return en liste af alle productionsorders
 * @throws SQLException 
 */
    public List<IProductionOrder> getProductionOrders() throws SQLException {
        return managerPassThrough.getAllProductionOrders();

    }
/**
 * converter strings til simplestringproperty
 * @param string
 * @return en ny simplestringproperty
 */
    public StringProperty stringConverter(String string) {
        return new SimpleStringProperty(string);
    }
/**
 * metoden tager fat i listen af productionsorders og laver den om til en observeablelist
 * @return en observeablelist managerOBS
 * @throws SQLException 
 */
    public ObservableList<IProductionOrder> getObservableProductionOrders() throws SQLException {
        ObservableList managerOBS = FXCollections.observableArrayList();
        managerOBS.addAll(getProductionOrders());
        return managerOBS;
    }
/**
 * metoden managerpassThrough kaldes på scanfolderfornewfiles
 * @throws IOException
 * @throws SQLException 
 */
    public void scanFolderForNewFiles() throws IOException, SQLException {
        managerPassThrough.scanFolderForNewFiles();
    }
/**
 * metoden laver en progressbar der kører hvis scanfolder for files bliver aktiveret
 * @param scanProgress 
 */
    public void setProgressBarToScanFolder(JFXProgressBar scanProgress) {

        if (thread == null || !thread.isAlive()) {

            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 1; i <= progressRuns; i++) {
                        scanProgress.setProgress(0.01 * i);
                        try {
                            Thread.sleep(progressRuntime / progressRuns);
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    scanProgress.setVisible(false);
                }
            });
        }
        if (!thread.isAlive()) {
            scanProgress.setVisible(true);
            thread.start();
        }
    }

}
