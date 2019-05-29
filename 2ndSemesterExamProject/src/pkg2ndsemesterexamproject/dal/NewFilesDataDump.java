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
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;
import pkg2ndsemesterexamproject.be.IDepartment;
import pkg2ndsemesterexamproject.be.IDepartmentTask;
import pkg2ndsemesterexamproject.be.IProductionOrder;
import pkg2ndsemesterexamproject.be.IWorker;

/**
 *
 * @author mpoul
 */
public class NewFilesDataDump {

    private GetData getData;
    private DatabaseConnection conProvider;

    public NewFilesDataDump() throws IOException {
        this.conProvider = new DatabaseConnection();
        getData = new GetData();
    }

    public void WriteDataFromNewFilesToDb(File file) throws IOException, SQLException {
        JSONFormatter jsonFormatter = new JSONFormatter();
        CSVFormatter csv = new CSVFormatter();
        List<IProductionOrder> productionOrders = new ArrayList();
        List<IWorker> workers = new ArrayList();
        if (file.getName().endsWith(".txt")) {
            workers = jsonFormatter.extractWorkersFromJSON(file);
            productionOrders = jsonFormatter.extractProductionOrdersFromJSON(file);
        }
        if (file.getName().endsWith(".csv")) {
            workers = csv.extractWorkers(file);
            productionOrders = csv.extractProductionOrders(file);
        }
        List<IProductionOrder> noDuplicatesProductionOrders = removeDuplicateFromProductionOrder(productionOrders);
        if (noDuplicatesProductionOrders == null) {
            noDuplicatesProductionOrders = productionOrders;
        }

        List<IDepartment> departments = new ArrayList();
        for (IProductionOrder noDuplicatesProductionOrder : noDuplicatesProductionOrders) {
            for (IDepartmentTask departmentTask : noDuplicatesProductionOrder.getDepartmentTasks()) {
                departments.add(departmentTask.getDepartment());
            }
        }
        writeProductionOrderToDB(noDuplicatesProductionOrders);
        writeDepartmentToDB(removeDuplicateFromDepartment(departments));
        writeWorkerToDB(removeDuplicateFromWorkers(workers));
        for (IProductionOrder noDuplicatesProductionOrder : noDuplicatesProductionOrders) {
            writeDepartmentTaskToDB(noDuplicatesProductionOrder);
        }
    }

    private List<IWorker> removeDuplicateFromWorkers(List<IWorker> workersFromFile) throws SQLException {
        List<IWorker> workersFromDB = getData.getAllWorkers();

        if (workersFromDB.isEmpty()) {
            return workersFromFile;
        }
        List<IWorker> nonDuplicateWorkers = new ArrayList();
        outerLoop:
        for (IWorker workerFromFile : workersFromFile) {
            int counter = 0;
            for (IWorker workerFromDB : workersFromDB) {
                if (workerFromFile.getSalaryNumber() == workerFromDB.getSalaryNumber()) {
                    continue outerLoop;
                } else if (counter == workersFromFile.size() - 1) {
                    nonDuplicateWorkers.add(workerFromFile);
                } else {
                    counter++;
                }
            }
        }
        return nonDuplicateWorkers;
    }

    private List<IDepartment> removeDuplicateFromDepartment(List<IDepartment> departmentsFromFile) throws SQLException {
        List<IDepartment> departmentsFromDB = getData.getAllDepartments();
        List<IDepartment> nonDuplicateDepartments = new ArrayList();

        if (departmentsFromDB.isEmpty()) {
            for (int i = 0; i < departmentsFromFile.size(); i++) {
                for (int j = i + 1; j < departmentsFromFile.size(); j++) {
                    if (departmentsFromFile.get(i).getName().equals(departmentsFromFile.get(j).getName())) {
                        departmentsFromFile.remove(j);
                        j--;
                    }
                }
            }
            nonDuplicateDepartments = departmentsFromFile;
            return nonDuplicateDepartments;
        }

        outerLoop:
        for (IDepartment iDepartmentFromFile : departmentsFromFile) {
            int counter = 0;
            for (IDepartment iDepartmentFromDB : departmentsFromDB) {
                if (iDepartmentFromFile.getName().equals(iDepartmentFromDB.getName())) {
                    continue outerLoop;
                } else if (counter == departmentsFromDB.size() - 1) {
                    nonDuplicateDepartments.add(iDepartmentFromFile);
                } else {
                    counter++;
                }
            }
        }

        return nonDuplicateDepartments;
    }

    private List<IProductionOrder> removeDuplicateFromProductionOrder(List<IProductionOrder> productionOrdersFromFile) throws IOException, SQLException {

        List<IProductionOrder> productionOrdersFromDB = getData.getAllProductionOrders();
        if (productionOrdersFromDB.get(0) == null) {
            return null;
        }
        List<IProductionOrder> nonDublicateOrders = new ArrayList();
        outerLoop:
        for (IProductionOrder iProductionOrderFile : productionOrdersFromFile) {
            int count = 0;
            for (IProductionOrder iProductionOrderDB : productionOrdersFromDB) {
                if (iProductionOrderDB.getOrder().getOrderNumber().equals(iProductionOrderFile.getOrder().getOrderNumber())) {
                    continue outerLoop;
                } else if (count == productionOrdersFromDB.size() - 1) {
                    nonDublicateOrders.add(iProductionOrderFile);
                } else {
                    count++;
                }
            }
        }

        return nonDublicateOrders;
    }

    private void writeDepartmentToDB(List<IDepartment> d) throws SQLException {
        try (Connection con = conProvider.getConnection()) {
            String query = "INSERT INTO Department (dName) VALUES(?);";
            PreparedStatement prst = con.prepareStatement(query);

            for (IDepartment department : d) {
                prst.setString(1, department.getName());
                prst.addBatch();
            }

            prst.executeBatch();
        } catch (SQLException ex) {
            throw new SQLException("Error writing department name to DB "+ex);
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
            throw new SQLException("Error writing departmentTask to DB "+ex);
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
            throw new SQLException("Error writing productionOrder to DB "+ex);
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
            throw new SQLException("Error writing worker to DB "+ex);
        }
    }

}
