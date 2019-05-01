/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.dal;
import java.io.IOException;
import java.sql.SQLException;
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



    
    OrderDAO odao;
    WorkerDAO wDAO ;

    public GetData() throws IOException
    {
        odao = new OrderDAO();
        wDAO = new WorkerDAO();
    }
    
    
            
            

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
    public void sendOrderIsDone(Order order, Department department) throws SQLException
    {
        odao.orderIsDone(order, department);
    }

@Override
    public List<IWorker> getAllWorkers() throws SQLException{
        return wDAO.getAllWorkers();
    }
}
