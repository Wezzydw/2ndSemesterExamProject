/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.dal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import pkg2ndsemesterexamproject.be.IProductionOrder;
import pkg2ndsemesterexamproject.be.IWorker;

/**
 *
 * @author Wezzy Laptop
 */
public class XLSXFormatter implements IFormatter {

    public List<String> testerString() throws FileNotFoundException, IOException {
        FileReader filereader = new FileReader(new File("./data/result.xlsx"));
        BufferedReader bufferedReader = new BufferedReader(filereader);
        List<String> data = new ArrayList();
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
            data.add(line);
        }

        return data;
    }

    @Override
    public List<IProductionOrder> extractProductionOrders(File file) throws FileNotFoundException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<IWorker> extractWorkers(File file) throws FileNotFoundException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> fileToStringArray(File file) throws FileNotFoundException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
