/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.dal;

import java.io.IOException;
import java.sql.SQLException;
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
    /**
     * Denne metode retunere ne liste af departments
     * som den får fra poDAO.getAllDepartments()
     * @return List<IDepartment>
     * @throws SQLException 
     */
    @Override
    public List<IDepartment> getAllDepartments() throws SQLException {
        return poDAO.getAllDepartments();
    }
/**
 * kalder poDAO for at update den ordre der sendes og logger informationen til DB
 * @param dt Det objekt af DepartmentTask som bliver markeret som færdig.
 * @param po Det objekt af ProductionOrder som indeholde DepartmentTasken.
 * @throws SQLException 
 */
    @Override
    public void sendOrderIsDone(IDepartmentTask dt, IProductionOrder po) throws SQLException {
        poDAO.updateOrderToDone(dt, po);
        poDAO.logToDB(dt, po);
    }
/**
 * returnere en liste af workers
 * @return workers
 * @throws SQLException 
 */
    @Override
    public List<IWorker> getAllWorkers() throws SQLException {
        return wDAO.getAllWorkers();
    }
/**
 * kalder poDAO for at få information om productionsorders
 * @return en liste af productionorders
 * @throws SQLException 
 */
    @Override
    public List<IProductionOrder> getAllProductionOrders() throws SQLException {
        return poDAO.getAllInfo();
    }
   
}
