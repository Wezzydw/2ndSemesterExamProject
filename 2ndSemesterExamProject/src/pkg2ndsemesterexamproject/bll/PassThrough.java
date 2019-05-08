
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
import pkg2ndsemesterexamproject.be.IDepartmentTask;
import pkg2ndsemesterexamproject.be.IProductionOrder;
import pkg2ndsemesterexamproject.be.IWorker;
import pkg2ndsemesterexamproject.be.Order;
import pkg2ndsemesterexamproject.dal.GetData;

/**
 *
 * @author andreas
 */
public class PassThrough implements IPassthrough {

    GetData getDataFromDB;

    public PassThrough() throws IOException {
        getDataFromDB = new GetData();
    }

    @Override
    public String loadData() throws FileNotFoundException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void extractWorkersFromJSON() throws FileNotFoundException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<IWorker> getWorkersFromDB() throws SQLException {
        return getDataFromDB.getAllWorkers();
    }

    @Override
    public List<IDepartment> getAllDepartments() throws SQLException {
        return getDataFromDB.getAllDepartments();
    }

    @Override
    public List<IWorker> getAllWorkers() throws SQLException {
        return getDataFromDB.getAllWorkers();
    }

    @Override
    public List<IProductionOrder> getAllProductionOrders() throws SQLException {
        return getDataFromDB.getAllProductionOrders();
    }

    @Override
    public void sendOrderIsDone(IDepartmentTask dt, IProductionOrder po) throws SQLException {
        getDataFromDB.sendOrderIsDone(dt, po);
    }

    public void getCircleColour() {
        getDataFromDB.getCircleColour();
    }

}
