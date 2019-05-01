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

/**
 *
 * @author andreas
 */
public interface IBLL
{
    public String loadData () throws FileNotFoundException, IOException;
    
    public void extractWorkersFromJSON()throws FileNotFoundException, IOException;

    public void sendOrderIsDone();

    public List<Worker> getWorkersFromDB();
       
}
