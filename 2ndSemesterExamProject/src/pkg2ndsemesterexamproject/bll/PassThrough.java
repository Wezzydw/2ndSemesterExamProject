/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.bll;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import pkg2ndsemesterexamproject.be.Worker;
import pkg2ndsemesterexamproject.dal.GetData;

/**
 *
 * @author andreas
 */
public class PassThrough implements IBLL
{
    GetData getDataFromDB = new GetData();

    @Override
    public String loadData() throws FileNotFoundException, IOException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void extractWorkersFromJSON() throws FileNotFoundException, IOException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void sendOrderIsDone()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Worker> getWorkersFromDB() {
        return getDataFromDB.getAllWorkers();
    }
    
}
