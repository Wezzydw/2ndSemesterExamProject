
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
import pkg2ndsemesterexamproject.be.Order;
import pkg2ndsemesterexamproject.be.ProductionOrder;

/**
 *
 * @author andreas
 */
public class ProductionOrderDAO
{

    private DatabaseConnection conProvider;

    public ProductionOrderDAO() throws IOException
    {
        try
        {
            conProvider = new DatabaseConnection();
        } catch (IOException ex)
        {
            throw new IOException("No database connection established " + ex);
        }
    }

    /**
     * laver connection til databasen og henter de efterspurgte information
     * derfra.
     *
     * @return en liste af productionorders med orderid customername og
     * deliverydate
     * @throws SQLException
     */
    public List<IProductionOrder> getProductionOrders() throws SQLException
    {
        List<IProductionOrder> po = new ArrayList();

        try (Connection con = conProvider.getConnection())
        {
            String a = "SELECT * FROM ProductionOrder;";
            PreparedStatement prst = con.prepareStatement(a);
            ResultSet rs = prst.executeQuery();

            while (rs.next())
            {
                String orderNumber = rs.getString("orderId");
                String cust = rs.getString("customerName");
                String deliveryDate = rs.getString("deliveryDate");
                IOrder order = new Order(orderNumber);
                LocalDate ld = LocalDate.parse(deliveryDate);
                IDelivery delivery = new Delivery(ld);
                ICustomer customer = new Customer(cust);
                List<IDepartmentTask> tasks = getAllTasksForProductionOrder(orderNumber);
                po.add(new ProductionOrder(order, delivery, customer, tasks));
            }
        } catch (SQLException ex)
        {
            throw new SQLException("No data from getProductionOrders" + ex);
        }
        return po;
    }

    /**
     * laver connection til databasen og henter de efterspurgte informationer
     * derfra.
     *
     * @param orderNum
     * @return en liste af departmenttask som indeholder information og ordrens
     * status samt start og endDate og hvilken department tasken er fra.
     * @throws SQLException
     */
    public List<IDepartmentTask> getAllTasksForProductionOrder(String orderNum) throws SQLException
    {
        List<IDepartmentTask> tasks = new ArrayList();
        try (Connection con = conProvider.getConnection())
        {
            String a = "SELECT * From DepartmentTask WHERE orderNumber = ?;";
            PreparedStatement prst = con.prepareStatement(a);
            prst.setString(1, orderNum);
            ResultSet rs = prst.executeQuery();

            while (rs.next())
            {
                boolean done = rs.getBoolean("isFinished");
                String sDate = rs.getString("startDate");
                String eDate = rs.getString("endDate");
                String department = rs.getString("department");
                LocalDate endDate = LocalDate.parse(eDate);
                LocalDate startDate = LocalDate.parse(sDate);

                Department dpart = new Department(department);
                tasks.add(new DepartmentTask(dpart, done, startDate, endDate));
            }
        } catch (SQLException ex)
        {
            throw new SQLException("No data from getAllTasks" + ex);
        }
        return tasks;
    }

    /**
     * Denne metode laver connection til databasen og tager fat i alle
     * departments som strings og laver en ny department i en liste
     *
     * @return en liste af departments DET HER SKAL NOK RETTES TIL JEG MARC ER
     * DUM
     * @throws SQLException
     */
    public List<IDepartment> getAllDepartments() throws SQLException
    {
        List<IDepartment> departments = new ArrayList();
        try (Connection con = conProvider.getConnection())
        {
            String a = "SELECT * FROM Department;";
            PreparedStatement prst = con.prepareStatement(a);
            ResultSet rs = prst.executeQuery();

            while (rs.next())
            {
                String department = rs.getString("dname");

                departments.add(new Department(department));
            }
        } catch (SQLException ex)
        {
            throw new SQLException("No data from getAllDepartments" + ex);
        }
        return departments;
    }

    /**
     * metoden laver connection til databasen laver en liste af productionorders
     * og task hvori den gemmer alle informationer vedrørerende dem.
     *
     * @return en liste af productionorders
     * @throws SQLException
     */
    public List<IProductionOrder> getAllInfo() throws SQLException
    {
        List<IProductionOrder> po = new ArrayList();
        List<IDepartmentTask> tasks = new ArrayList();
        IProductionOrder test = null;
        try (Connection con = conProvider.getConnection())
        {
            String a = "SELECT orderNumber, department, startDate, endDate, isFinished, customerName, deliveryDate\n"
                    + "  FROM [DepartmentTask] JOIN ProductionOrder\n"
                    + "    ON [DepartmentTask].orderNumber = ProductionOrder.orderId\n"
                    + " ORDER BY orderNumber";
            PreparedStatement prst = con.prepareStatement(a);
            ResultSet rs = prst.executeQuery();
            while (rs.next())
            {
                String cust = rs.getString("customerName");
                String deliveryDate = rs.getString("deliveryDate");
                boolean done = rs.getBoolean("isFinished");
                String sDate = rs.getString("startDate");
                String eDate = rs.getString("endDate");
                String department = rs.getString("department");
                String orderNumber = rs.getString("orderNumber");

                LocalDate endDate = LocalDate.parse(eDate);
                LocalDate startDate = LocalDate.parse(sDate);
                IOrder order = new Order(orderNumber);
                LocalDate ld = LocalDate.parse(deliveryDate);

                IDelivery delivery = new Delivery(ld);
                ICustomer customer = new Customer(cust);

                if (test == null)
                {

                    test = new ProductionOrder(order, delivery, customer, tasks);
                }

                if (test.getOrder().getOrderNumber().equals(order.getOrderNumber()))
                {
                    Department dpart = new Department(department);
                    test.getDepartmentTasks().add(new DepartmentTask(dpart, done, startDate, endDate));

                } else
                {

                    po.add(test);
                    test = new ProductionOrder(order, delivery, customer, tasks);
                    Department dpart = new Department(department);
                    test.getDepartmentTasks().add(new DepartmentTask(dpart, done, startDate, endDate));

                }
            }
            po.add(test);
        } catch (SQLException ex)
        {
            throw new SQLException("No data from getProductionOrders" + ex);
        }

        return po;
    }

    /**
     * denne metode skaber connection til databasen og tager fat i den/de ordre
     * der er blevet markeret som finished ved at tjekke ordernumber og
     * department og opdatere statussen derpå
     *
     * @param dt objekt departmenttask hvis status der skal tjekkes
     * @param po objekt productionorder hvis status der skal tjekkes
     * @throws SQLException
     */
    public void updateOrderToDone(IDepartmentTask dt, IProductionOrder po) throws SQLException
    {
        try (Connection con = conProvider.getConnection())
        {
            String a = "UPDATE DepartmentTask SET isFinished = ? WHERE (orderNumber = ? AND department = ?);";
            PreparedStatement prst = con.prepareStatement(a);
            prst.setBoolean(1, true);
            prst.setString(2, po.getOrder().getOrderNumber());
            prst.setString(3, dt.getDepartment().getName());

            prst.execute();
        } catch (SQLException ex)
        {
            throw new SQLException("Error updating order to finished " + po.getOrder().getOrderNumber() + " " + ex);
        }
    }

    /**
     * tager information fra fra departmentstask og productionsordre status og
     * logger dem til databasen og gemmer logtid, department, action og
     * logordernumber derpå.
     *
     * @param dt objekt departmenttask hvis data der skal logges til DB
     * @param po objekt productionorder hvis data der skal logges til DB
     * @throws SQLException
     */
    void logToDB(IDepartmentTask dt, IProductionOrder po) throws SQLException
    {
        try (Connection con = conProvider.getConnection())
        {
            String a = "INSERT INTO Log (logDateTime, logDepartment, "
                    + "logAction, logOrderNumber) VALUES(?,?,?,?);";
            PreparedStatement prst = con.prepareStatement(a);
            prst.setString(1, LocalDateTime.now().toString());
            prst.setString(2, dt.getDepartment().getName());
            prst.setString(3, "Der er trykket på 'Task er færdig'");
            prst.setString(4, po.getOrder().getOrderNumber());
            prst.execute();
        } catch (SQLException ex)
        {
            throw new SQLException("Error logging to DB " + ex);
        }
    }
}
