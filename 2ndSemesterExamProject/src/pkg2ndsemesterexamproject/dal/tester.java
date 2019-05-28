/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.dal;

import java.io.IOException;
import java.sql.SQLException;

/**
 *
 * @author Wezzy
 */
public class tester
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, SQLException
    {
       ScanFolder sf = new ScanFolder();
       sf.updateFiles();
       
    }
    
}
