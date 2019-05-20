package pkg2ndsemesterexamproject.dal;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pkg2ndsemesterexamproject.be.IDepartment;
import pkg2ndsemesterexamproject.be.IDepartmentTask;
import pkg2ndsemesterexamproject.be.IProductionOrder;
import pkg2ndsemesterexamproject.be.IWorker;

/**
 *
 * @author mpoul
 */
public class JsonToDb {

    private DatabaseConnection conProvider;

    public JsonToDb() throws IOException {
        this.conProvider = new DatabaseConnection();
    }

    public static void main(String[] args) throws SQLServerException, IOException {
        JsonToDb bla = new JsonToDb();

        try {
            bla.dataDumper();
        } catch (SQLException ex) {
            Logger.getLogger(JsonToDb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void dataDumper() throws IOException, SQLException {
        
        File f = new File("data/");
        System.out.println(f.isDirectory());
        JSONFormater jf = new JSONFormater();
        GetData getData = new GetData();
        List<IProductionOrder> productionOrdersFromDB = getData.getAllProductionOrders();
        List<IProductionOrder> productionOrdersFromFile = jf.extractProductionOrdersFromJSON();
        List<IProductionOrder> nonDublicateOrders = new ArrayList();
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

        List<IDepartment> departments = new ArrayList();
        for (IProductionOrder iProductionOrder : nonDublicateOrders) {
            for (IDepartmentTask departmentTask : iProductionOrder.getDepartmentTasks()) {
                departments.add(departmentTask.getDepartment());
            }
        }
        
        System.out.println("tester");
        for (IProductionOrder iProductionOrder : nonDublicateOrders) {
            System.out.println(iProductionOrder.getOrder().getOrderNumber());
        }

//        writeProductionOrderToDB(productionOrdersFromFile);
//        writeDepartmentToDB(departments);
//        
//        writeWorkerToDB(jf.extractWorkersFromJSON());
//
//        for (IProductionOrder po : jf.extractProductionOrdersFromJSON()) {
//            writeDepartmentTaskToDB(po);
//        }
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
        }
    }

    private void writeDepartmentTaskToDB(IProductionOrder po) throws SQLException {
        try (Connection con = conProvider.getConnection()) {
            String query = "INSERT INTO DepartmentTask(isFinished, startDate, endDate, department, orderNumber) VALUES(?,?,?,?,?);";
            PreparedStatement prst = con.prepareStatement(query);
            List<IDepartmentTask> dTask = po.getDepartmentTasks();
            for (IDepartmentTask departmentTask : dTask) {
//                if (departmentTask.getFinishedOrder())
//                {
//                    prst.setInt(1, 1);
//                } else
//                {
//                    prst.setInt(1, 0);
//                }
                prst.setBoolean(1, departmentTask.getFinishedOrder());
                prst.setString(2, departmentTask.getStartDate().toString());
                prst.setString(3, departmentTask.getEndDate().toString());
                prst.setString(4, departmentTask.getDepartment().toString());
                prst.setString(5, po.getOrder().toString());
                prst.addBatch();
            }
            prst.executeBatch();
        } catch (SQLException ex) {
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
        }
    }

}
