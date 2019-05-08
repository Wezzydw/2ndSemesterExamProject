
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.dal;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import pkg2ndsemesterexamproject.be.Customer;
import pkg2ndsemesterexamproject.be.Delivery;
import pkg2ndsemesterexamproject.be.Department;
import pkg2ndsemesterexamproject.be.DepartmentTask;
import pkg2ndsemesterexamproject.be.ICustomer;
import pkg2ndsemesterexamproject.be.IDelivery;
import pkg2ndsemesterexamproject.be.IDepartment;
import pkg2ndsemesterexamproject.be.IDepartmentTask;
import pkg2ndsemesterexamproject.be.IOrder;
import pkg2ndsemesterexamproject.be.IProductionOrder;
import pkg2ndsemesterexamproject.be.IWorker;
import pkg2ndsemesterexamproject.be.Order;
import pkg2ndsemesterexamproject.be.ProductionOrder;
import pkg2ndsemesterexamproject.be.Worker;

/**
 *
 * @author andreas
 */
public class ProductionOrderDAO {

    private DatabaseConnection conProvider;

    public ProductionOrderDAO() throws IOException {
        try {
            conProvider = new DatabaseConnection();
        } catch (IOException ex) {
            throw new IOException("No database connection established " + ex);
        }
    }

    public List<IProductionOrder> getProductionOrders() throws SQLException {
        List<IProductionOrder> po = new ArrayList();

        try (Connection con = conProvider.getConnection()) {
            String a = "SELECT * FROM ProductionOrder;";
            PreparedStatement prst = con.prepareStatement(a);
            ResultSet rs = prst.executeQuery();

            while (rs.next()) {
                String orderNumber = rs.getString("orderId");
                String cust = rs.getString("customerName");
                String deliveryDate = rs.getString("deliveryDate");
                IOrder order = new Order(orderNumber);
                LocalDate ld = LocalDate.parse(deliveryDate);
                LocalDateTime ldt = ld.atStartOfDay();
                IDelivery delivery = new Delivery(ldt);
                ICustomer customer = new Customer(cust);
                List<IDepartmentTask> tasks = getAllTasksForProductionOrder(orderNumber);
                po.add(new ProductionOrder(order, delivery, customer, tasks));
            }
        } catch (SQLException ex) {
            throw new SQLException("No data from getProductionOrders" + ex);
        }
        return po;
    }

    public List<IDepartmentTask> getAllTasksForProductionOrder(String orderNum) throws SQLException {
        List<IDepartmentTask> tasks = new ArrayList();
        try (Connection con = conProvider.getConnection()) {
//            String a = "SELECT * FROM DepartmentTask;";
            String a = "SELECT * From DepartmentTask WHERE orderNumber = ?;";
            PreparedStatement prst = con.prepareStatement(a);
            prst.setString(1, orderNum);
            ResultSet rs = prst.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                boolean done = rs.getBoolean("isFinished");
                String sDate = rs.getString("startDate");
                String eDate = rs.getString("endDate");
                String department = rs.getString("department");
                String orderNumber = rs.getString("orderNumber");
                LocalDate eld = LocalDate.parse(eDate);
                LocalDate sld = LocalDate.parse(sDate);
                LocalDateTime endDate = eld.atStartOfDay();
                LocalDateTime startDate = sld.atStartOfDay();
                Department dpart = new Department(department);
                tasks.add(new DepartmentTask(dpart, done, startDate, endDate));
            }
        } catch (SQLException ex) {
            throw new SQLException("No data from getAllTasks" + ex);
        }
        return tasks;
    }

    public List<IDepartment> getAllDepartments() throws SQLException {
        List<IDepartment> departments = new ArrayList();
        try (Connection con = conProvider.getConnection()) {
            String a = "SELECT * FROM Department;";
            PreparedStatement prst = con.prepareStatement(a);
            ResultSet rs = prst.executeQuery();

            while (rs.next()) {
                String department = rs.getString("dname");

                departments.add(new Department(department));
            }
        } catch (SQLException ex) {
            throw new SQLException("No data from getAllDepartments" + ex);
        }
        return departments;
    }

//    private List<IWorker> getAllWorkers() throws SQLException{
//        List<IWorker> workers = new ArrayList();
//        try (Connection con = conProvider.getConnection()) {
//            String a = "SELECT * FROM DepartmentTask;";
//            PreparedStatement prst = con.prepareStatement(a);
//            ResultSet rs = prst.executeQuery();
//
//            while (rs.next()) {
//                String workerName = rs.getString("name");
//                String initials = rs.getString("initials");
//                int salaryNumber = rs.getInt("salaryNumber");
//                workers.add(new Worker(workerName, initials, salaryNumber));
//            }
//        } catch (SQLException ex) {
//            throw new SQLException("No data from getAllWorkers" + ex);
//        }        
//        return workers;        
//    }
}
