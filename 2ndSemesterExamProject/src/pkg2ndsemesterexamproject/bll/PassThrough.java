
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
import java.util.logging.Level;
import java.util.logging.Logger;
import pkg2ndsemesterexamproject.be.IDepartment;
import pkg2ndsemesterexamproject.be.IDepartmentTask;
import pkg2ndsemesterexamproject.be.IProductionOrder;
import pkg2ndsemesterexamproject.be.IWorker;
import pkg2ndsemesterexamproject.dal.GetData;
import pkg2ndsemesterexamproject.dal.ScanFolder;

/**
 *
 * @author andreas
 */
public class PassThrough implements IPassthrough
{

    GetData getDataFromDB;
    ScanFolder scanFolder;

    public PassThrough() throws IOException
    {
        getDataFromDB = new GetData();
        scanFolder = new ScanFolder();
    }
/**
 * 
 * @return metoden returnere en liste af Workers
 * @throws SQLException 
 */
    @Override
    public List<IWorker> getWorkersFromDB() throws SQLException
    {
        return getDataFromDB.getAllWorkers();
    }

    /**
     * Denne metode retunere en liste af departments som den får fra
     * getDataFromDB.getAllDepartments()
     * @return List<IDepartment>
     * @throws SQLException
     */
    @Override
    public List<IDepartment> getAllDepartments() throws SQLException
    {
        return getDataFromDB.getAllDepartments();
    }
    /**
     * 
     * @return returnere en liste af workers
     * @throws SQLException 
     */
    @Override
    public List<IWorker> getAllWorkers() throws SQLException
    {
        return getDataFromDB.getAllWorkers();
    }
    /**
     * 
     * @return retunere en liste af productionsorders fra db
     * @throws SQLException 
     */
    @Override
    public List<IProductionOrder> getAllProductionOrders() throws SQLException
    {
        return getDataFromDB.getAllProductionOrders();
    }
    /**
     * Metoden kalder getdatafromdb metoden
     * @param dt
     * @param po
     * @throws SQLException 
     */
    @Override
    public void sendOrderIsDone(IDepartmentTask dt, IProductionOrder po) throws SQLException
    {
        getDataFromDB.sendOrderIsDone(dt, po);
    }
    /**
     * Metoden kalder scanfolder metoden
     * @throws IOException
     * @throws SQLException 
     */
    @Override
    public void scanFolderForNewFiles() throws IOException, SQLException
    {
        try
        {
            scanFolder.updateFiles();
        } catch (IOException | SQLException ex)
        {
            throw ex;
        }
    }

}
