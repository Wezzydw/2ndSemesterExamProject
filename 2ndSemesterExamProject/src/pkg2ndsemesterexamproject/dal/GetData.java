/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import pkg2ndsemesterexamproject.be.Department;
import pkg2ndsemesterexamproject.be.Order;
import java.util.List;
import pkg2ndsemesterexamproject.be.IWorker;
import pkg2ndsemesterexamproject.be.Worker;

/**
 *
 * @author andreas
 */
public class GetData implements IGetData
{
WorkerDAO wDAO = new WorkerDAO();
    @Override
    public List<Department> getAllDepartments()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Order> getAllOrders()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void sendOrderIsDone()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
@Override
    public List<IWorker> getAllWorkers() throws SQLException{
        return wDAO.getAllWorkers();
    }
}
