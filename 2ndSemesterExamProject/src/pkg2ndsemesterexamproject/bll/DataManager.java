/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.bll;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Wezzy Laptop
 */
public class DataManager implements IBLL {

    public String loadJSON(File file) throws FileNotFoundException, IOException {
        FileReader filereader = new FileReader(new File("./data/JSON.txt"));
        BufferedReader bufferedReader = new BufferedReader(filereader);
        String data = "";
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            data += line;
        }
        return data;
    }

    @Override
    public void extractDataFromJSON() throws FileNotFoundException, IOException {
        String[] array = loadData().split("//[");
        List<String> workersString = new ArrayList();
        
        
        
    }

    @Override
    public String loadData() throws FileNotFoundException, IOException {
        FileReader filereader = new FileReader(new File("./data/JSON.txt"));
        BufferedReader bufferedReader = new BufferedReader(filereader);
        String data = "";
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            data += line;
        }
        return data;
    }

}
