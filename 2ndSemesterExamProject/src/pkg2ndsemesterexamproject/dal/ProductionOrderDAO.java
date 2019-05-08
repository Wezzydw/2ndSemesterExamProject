
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
import java.util.Iterator;
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
import pkg2ndsemesterexamproject.be.Order;
import pkg2ndsemesterexamproject.be.ProductionOrder;

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
    
       public void updateCircleColour(){
                try (Connection con = conProvider.getConnection()) {

//            prst.execute();
            System.out.println("DILLERBANG");
        } catch(SQLException ex){
            
        }
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
    public List<IProductionOrder> getAllInfo() throws SQLException {
        List<IProductionOrder> po = new ArrayList();
        List<IDepartmentTask> dt = new ArrayList();
        List<IDepartmentTask> tasks = new ArrayList();
        IProductionOrder test = null;
        try (Connection con = conProvider.getConnection()) {
            String a = "SELECT orderNumber, department, startDate, endDate, isFinished, customerName, deliveryDate\n"
                    + "  FROM [DepartmentTask] JOIN ProductionOrder\n"
                    + "    ON [DepartmentTask].orderNumber = ProductionOrder.orderId\n"
                    + " ORDER BY orderNumber";
            PreparedStatement prst = con.prepareStatement(a);
            ResultSet rs = prst.executeQuery();
            boolean tmp = false;
            while ((tmp = rs.next())) {
                String cust = rs.getString("customerName");
                String deliveryDate = rs.getString("deliveryDate");
                boolean done = rs.getBoolean("isFinished");
                String sDate = rs.getString("startDate");
                String eDate = rs.getString("endDate");
                String department = rs.getString("department");
                String orderNumber = rs.getString("orderNumber");

                LocalDate eld = LocalDate.parse(eDate);
                LocalDate sld = LocalDate.parse(sDate);
                LocalDateTime endDate = eld.atStartOfDay();
                LocalDateTime startDate = sld.atStartOfDay();
                IOrder order = new Order(orderNumber);
                LocalDate ld = LocalDate.parse(deliveryDate);
                LocalDateTime ldt = ld.atStartOfDay();
                IDelivery delivery = new Delivery(ldt);
                ICustomer customer = new Customer(cust);
//                if (po.isEmpty()) {
//                    IProductionOrder test = new ProductionOrder(order, delivery, customer, tasks);
//                    Department dpart = new Department(department);
//                    test.getDepartmentTasks().add(new DepartmentTask(dpart, done, startDate, endDate));
//                } 

                if (test == null) {
 
                    test = new ProductionOrder(order, delivery, customer, tasks);
                }

                if (test.getOrder().getOrderNumber().equals(order.getOrderNumber())) {
                    Department dpart = new Department(department);
                    test.getDepartmentTasks().add(new DepartmentTask(dpart, done, startDate, endDate));

                } else {

                    po.add(test);
                    test = new ProductionOrder(order, delivery, customer, tasks);
                    Department dpart = new Department(department);
                    test.getDepartmentTasks().add(new DepartmentTask(dpart, done, startDate, endDate));
                    

                }

//
                //                Iterator<IProductionOrder> it = po.iterator();
                //
                //                while (it.hasNext()) {
                //                    IProductionOrder att = it.next();
                //
                //                    if (!att.getOrder().getOrderNumber().equals(order.getOrderNumber())) {
                //
                //                        IProductionOrder test = new ProductionOrder(order, delivery, customer, tasks);
                //                        Department dpart = new Department(department);
                //                        test.getDepartmentTasks().add(new DepartmentTask(dpart, done, startDate, endDate));
                //                        po.add(test);
                //                    } else {
                //                        Department dpart = new Department(department);
                //                        att.addDepartmentTask(new DepartmentTask(dpart, done, startDate, endDate));
                //                    }
                //                }
                //
                //                if (po.isEmpty()) {
                //                    IProductionOrder test = new ProductionOrder(order, delivery, customer, tasks);
                //                    Department dpart = new Department(department);
                //                    test.getDepartmentTasks().add(new DepartmentTask(dpart, done, startDate, endDate));
                //                    po.add(test);
                //                }
                //                if (!po.contains(order)) {
                //                    IProductionOrder test = new ProductionOrder(order, delivery, customer, tasks);
                //                    Department dpart = new Department(department);
                //                    test.getDepartmentTasks().add(new DepartmentTask(dpart, done, startDate, endDate));
                ////                    po.add(test);
                //            }else {
                //                    for (IProductionOrder ass : po) {
                //                        if (ass.getOrder().equals(order)) {
                //                            Department dpart = new Department(department);
                //                            ass.addDepartmentTask(new DepartmentTask(dpart, done, startDate, endDate));
                //                        }
                //                    }
                //                }
                {

                }
            }
            po.add(test);
        } catch (SQLException ex) {
            throw new SQLException("No data from getProductionOrders" + ex);
        }

        for (int i = 1;
                i < po.size();
                i++) {
            if (po.get(i).getOrder().getOrderNumber().equals(po.get(i - 1).getOrder().getOrderNumber())) {
                po.remove(po.get(i));
            }
        }
        for (IDepartmentTask task : tasks) {
            for (IProductionOrder iProductionOrder : po) {
//                if(iProductionOrder.getOrder().getOrderNumber().equals(task.)){
//                    
//                }
            }
        }
        return po;
    }

    public void updateOrderToDone(IDepartmentTask dt, IProductionOrder po) {
        try (Connection con = conProvider.getConnection()) {
//            String a = "UPDATE DepartmentTask SET isFinished = ? WHERE (orderNumber = ? AND department = ?);";
//            PreparedStatement prst = con.prepareStatement(a);
//            prst.setBoolean(1, true);
//            prst.setString(2, po.getOrder().getOrderNumber());
//            prst.setString(3, dt.getDepartment().getName());
//            
//            prst.execute();
            System.out.println("DILLERBANG");
        } catch (SQLException ex) {

        }
}

}
