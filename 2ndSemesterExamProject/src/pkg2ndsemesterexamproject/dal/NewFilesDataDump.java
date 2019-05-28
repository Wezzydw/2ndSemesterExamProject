/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.dal;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pkg2ndsemesterexamproject.be.IDepartment;
import pkg2ndsemesterexamproject.be.IDepartmentTask;
import pkg2ndsemesterexamproject.be.IProductionOrder;
import pkg2ndsemesterexamproject.be.IWorker;

/**
 *
 * @author mpoul
 */
public class NewFilesDataDump {

    private DatabaseConnection conProvider;

    public NewFilesDataDump() throws IOException {
        this.conProvider = new DatabaseConnection();
    }

    public void WriteDataFromNewFilesToDb() throws IOException, SQLException {
        File files = new File("data/");
        File[] listOfFiles = files.listFiles();
        ScanFolder sf = new ScanFolder();
        JSONFormater jf = new JSONFormater();
        for (File file : listOfFiles) {
            if (file.isFile()) {
                if (sf.hasFileBeenSaved(file.getName()) != true) {
                    if (file.getName().endsWith(".txt")) {
                        List<IProductionOrder> nonDuplicateDataList = removeDuplicates(file, ".txt");
                        List<IDepartment> departments = new ArrayList();
                        writeProductionOrderToDB(nonDuplicateDataList);
                        for (IProductionOrder iProductionOrder : nonDuplicateDataList) {
                            writeDepartmentTaskToDB(iProductionOrder);
                            for (IDepartmentTask departmentTask : iProductionOrder.getDepartmentTasks()) {
                                departments.add(departmentTask.getDepartment());
                            }
                        }
                        writeDepartmentToDB(departments);
                        writeWorkerToDB(jf.extractWorkersFromJSON(file));

                    } else if (file.getName().endsWith(".csv")) {
                        List<IProductionOrder> nonDuplicateDataList = removeDuplicates(file, ".csv");
                        List<IDepartment> departments = new ArrayList();
                        writeProductionOrderToDB(nonDuplicateDataList);
                        for (IProductionOrder iProductionOrder : nonDuplicateDataList) {
                            writeDepartmentTaskToDB(iProductionOrder);
                            for (IDepartmentTask departmentTask : iProductionOrder.getDepartmentTasks()) {
                                departments.add(departmentTask.getDepartment());
                            }
                        }
                        writeDepartmentToDB(departments);
                        writeWorkerToDB(jf.extractWorkersFromJSON(file));
                    }
                }
            }
        }
    }

    public List<IProductionOrder> removeDuplicates(File f, String type) throws IOException, SQLException {
        JSONFormater jf = new JSONFormater();
        GetData getData = new GetData();
        CSVFormatter cf = new CSVFormatter();
        List<IProductionOrder> productionOrdersFromDB = getData.getAllProductionOrders();
        List<IProductionOrder> productionOrdersFromFile = new ArrayList();
        List<IProductionOrder> nonDublicateOrders = new ArrayList();
        if (type.equals(".txt")) {
            productionOrdersFromFile = jf.extractProductionOrdersFromJSON(f);
        }
        if (type.equals(".csv")) {
            productionOrdersFromFile = cf.extractProductionOrders(f);
        }
        outerLoop:
        for (IProductionOrder iProductionOrderDB : productionOrdersFromDB) {
            int count = 0;
            for (IProductionOrder iProductionOrderFile : productionOrdersFromFile) {
                if (iProductionOrderDB.getOrder().getOrderNumber().equals(iProductionOrderFile.getOrder().getOrderNumber())) {
                    continue outerLoop;
                } else if (count == productionOrdersFromFile.size() - 1) {
                    nonDublicateOrders.add(iProductionOrderFile);
                } else {
                    count++;
                }
            }
        }
        return nonDublicateOrders;
    }

    public void writeDepartmentToDB(List<IDepartment> d) throws SQLException {
        try (Connection con = conProvider.getConnection()) {
            String query = "INSERT INTO Department (dName) VALUES(?);";
            PreparedStatement prst = con.prepareStatement(query);

            for (IDepartment department : d) {
                prst.setString(1, department.getName());
                prst.addBatch();
            }

            prst.executeBatch();

        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
    }

    private void writeDepartmentTaskToDB(IProductionOrder po) throws SQLException {
        try (Connection con = conProvider.getConnection()) {
            String query = "INSERT INTO DepartmentTask(isFinished, startDate, endDate, department, orderNumber) VALUES(?,?,?,?,?);";
            PreparedStatement prst = con.prepareStatement(query);
            List<IDepartmentTask> dTask = po.getDepartmentTasks();
            for (IDepartmentTask departmentTask : dTask) {
                prst.setBoolean(1, departmentTask.getFinishedOrder());
                prst.setString(2, departmentTask.getStartDate().toString());
                prst.setString(3, departmentTask.getEndDate().toString());
                prst.setString(4, departmentTask.getDepartment().toString());
                prst.setString(5, po.getOrder().toString());
                prst.addBatch();
            }
            prst.executeBatch();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
    }

    private void writeProductionOrderToDB(List<IProductionOrder> pos) throws SQLException {
        try (Connection con = conProvider.getConnection()) {
            String query = "INSERT INTO ProductionOrder (customerName, deliveryDate, orderId) VALUES(?,?,?);";
            PreparedStatement prst = con.prepareStatement(query);

            for (IProductionOrder po : pos) {
                prst.setString(1, po.getCustomer().toString());
                prst.setString(2, po.getDelivery().getDeliveryTime().toString());
                prst.setString(3, po.getOrder().toString());
                prst.addBatch();
            }
            prst.executeBatch();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
    }

    private void writeWorkerToDB(List<IWorker> w) throws SQLException {
        try (Connection con = conProvider.getConnection()) {
            String query = "INSERT INTO Worker (name, initials, salaryNumber) VALUES(?,?,?);";
            PreparedStatement prst = con.prepareStatement(query);

            for (IWorker worker : w) {
                prst.setString(1, worker.getName());
                prst.setString(2, worker.getInitials());
                prst.setInt(3, worker.getSalaryNumber());
                prst.addBatch();
            }

            prst.executeBatch();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
    }

}
