/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.bll;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import pkg2ndsemesterexamproject.be.Department;
import pkg2ndsemesterexamproject.be.IDepartment;
import pkg2ndsemesterexamproject.be.IProductionOrder;
import pkg2ndsemesterexamproject.be.IWorker;
import pkg2ndsemesterexamproject.be.Order;

/**
 *
 * @author andreas
 */
public interface IPassthrough
{
    public String loadData () throws FileNotFoundException, IOException;
    
    public void extractWorkersFromJSON()throws FileNotFoundException, IOException;

    public void sendOrderIsDone();

    public List<IWorker> getWorkersFromDB()throws SQLException;
       
    List<IDepartment> getAllDepartments() throws SQLException;
    
    void sendOrderIsDone(Order order, Department department) throws SQLException;
    
    List<IWorker> getAllWorkers()throws SQLException;
    
    List<IProductionOrder> getAllProductionOrders() throws SQLException;
}