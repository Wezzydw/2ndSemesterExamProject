/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.bll;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author andreas
 */
public interface IBLL
{
    public String loadData () throws FileNotFoundException, IOException;
    
    public void extractDataFromJSON()throws FileNotFoundException, IOException;
            
}
