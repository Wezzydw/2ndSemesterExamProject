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
import pkg2ndsemesterexamproject.be.IDepartment;
import pkg2ndsemesterexamproject.be.IDepartmentTask;
import pkg2ndsemesterexamproject.be.IProductionOrder;
import pkg2ndsemesterexamproject.be.IWorker;

/**
 *
 * @author andreas
 */
public class GetData implements IGetData {

    ProductionOrderDAO poDAO;
    WorkerDAO wDAO;

    public GetData() throws IOException {
        poDAO = new ProductionOrderDAO();
        wDAO = new WorkerDAO();
    }

    @Override
    public List<IDepartment> getAllDepartments() throws SQLException {
        return poDAO.getAllDepartments();
    }

    @Override
    public void sendOrderIsDone(IDepartmentTask dt, IProductionOrder po) throws SQLException {
        poDAO.updateOrderToDone(dt, po);
    }

    @Override
    public List<IWorker> getAllWorkers() throws SQLException {
        return wDAO.getAllWorkers();
    }

    @Override
    public List<IProductionOrder> getAllProductionOrders() throws SQLException {
        return poDAO.getAllInfo();
    }

//    @Override
    public List<Order> getAllOrders() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
