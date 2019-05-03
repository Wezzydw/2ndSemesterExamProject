package pkg2ndsemesterexamproject.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import pkg2ndsemesterexamproject.be.ICostumer;
import pkg2ndsemesterexamproject.be.IDelivery;
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

    private void dataDumper(List<IProductionOrder> po) {

        for (IProductionOrder iProductionOrder : po) {

        }
    }

    private void writeDepartmentToDB(List<IDepartment> d) {
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

    private void writeDepartmentTaskToDB(List<IDepartmentTask> dTask) {
        try (Connection con = conProvider.getConnection()) {
            String query = "INSERT INTO DepartmentTask(workers, isFinished, startDate, endDate) VALUES(?,?,?,?);";
            PreparedStatement prst = con.prepareStatement(query);

            for (IDepartmentTask departmentTask : dTask) {
                prst.setString(1, departmentTask.getActiveWorkers().toString());
                prst.setBoolean(2, departmentTask.getFinishedOrder());
                prst.setString(3, departmentTask.getStartDate().toString());
                prst.setString(4, departmentTask.getEndDate().toString());
                prst.addBatch();
            }
            prst.executeBatch();
        } catch (SQLException ex) {
        }
    }

    private void writeProductionOrderToDB(List<IProductionOrder> pos) {
        try (Connection con = conProvider.getConnection()) {
            String query = "INSERT INTO ProductionOrder (customerId, deliveryDate, orderId) VALUES(?,?,?);";
            PreparedStatement prst = con.prepareStatement(query);

            for (IProductionOrder po : pos) {
                prst.setString(1, po.getCostumer().toString());
                prst.setString(2, po.getDelivery().toString());
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
