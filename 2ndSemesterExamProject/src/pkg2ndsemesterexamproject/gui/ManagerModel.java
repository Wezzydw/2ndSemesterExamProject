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
    private final int progressRuntime = 1500;
    private final int progressRuns = 100;

    public ManagerModel() throws IOException {

        managerPassThrough = new PassThrough();
    }

    public List<IProductionOrder> getProductionOrders() throws SQLException {
        return managerPassThrough.getAllProductionOrders();

    }

    public StringProperty stringConverter(String string) {
        return new SimpleStringProperty(string);
    }

    public ObservableList<IProductionOrder> getObservableProductionOrders() throws SQLException {
        ObservableList managerOBS = FXCollections.observableArrayList();
        managerOBS.addAll(getProductionOrders());
        return managerOBS;
    }

    public void scanFolderForNewFiles() throws IOException {
        managerPassThrough.scanFolderForNewFiles();
    }

    public void checkScanFolder(JFXProgressBar scanProgress) {

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
