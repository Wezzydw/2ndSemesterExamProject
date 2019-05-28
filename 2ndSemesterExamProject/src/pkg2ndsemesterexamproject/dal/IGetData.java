/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.dal;

import java.sql.SQLException;
import java.util.List;
import pkg2ndsemesterexamproject.be.IDepartment;
import pkg2ndsemesterexamproject.be.IDepartmentTask;
import pkg2ndsemesterexamproject.be.IProductionOrder;
import pkg2ndsemesterexamproject.be.IWorker;

/**
 *
 * @author andreas
 */
public interface IGetData
{
    List<IDepartment> getAllDepartments() throws SQLException;
    
    void sendOrderIsDone(IDepartmentTask dt, IProductionOrder po) throws SQLException;
    
    List<IWorker> getAllWorkers()throws SQLException;
    
    List<IProductionOrder> getAllProductionOrders() throws SQLException;
}
