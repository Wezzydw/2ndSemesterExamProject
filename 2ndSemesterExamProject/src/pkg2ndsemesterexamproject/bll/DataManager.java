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
import pkg2ndsemesterexamproject.be.Worker;

/**
 *
 * @author Wezzy Laptop
 */
public class DataManager implements IBLL {

    private int jsonWorkerIndex = 2;
    private int initialsIndexLength = 11;
    private int nameIndexLength = 7;
    private int saleryNumberIndexLength = 14;

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
    public void extractWorkersFromJSON() throws FileNotFoundException, IOException {
        String[] array = loadData().split("\\[");
        String workerString = array[1];
        String[] workersString = workerString.split("\\{");
        List<Worker> workers = new ArrayList();
        System.out.println(workersString[0]);
        for (String string : workersString) {
            if (!string.isEmpty()) {
                int index = 0;
                System.out.println(string);
                index = string.indexOf(":");
                index = string.indexOf(":", index + 1);
                index = string.indexOf(":", index + 1);
                System.out.println(index);
                int indexStart = index + 2;
                int indexEnd = string.indexOf('"', indexStart + 1);

                String initials = string.substring(indexStart, indexEnd);

                index = string.indexOf(":", indexEnd);
                indexStart = index + 2;
                indexEnd = string.indexOf('"', indexStart);

                String name = string.substring(indexStart, indexEnd);

                index = string.indexOf(":", indexEnd);
                indexStart = index + 1;
                indexEnd = string.indexOf('}', indexStart);

                String saleryNumber = string.substring(indexStart, indexEnd);

//            int initialsStartIndex = string.indexOf("Initials", 0);
//            int initialsEndIndex = string.indexOf(",", initialsStartIndex);
//            String initials = string.substring(initialsStartIndex + initialsIndexLength, initialsEndIndex - 1);
//
//            int nameStartIndex = string.indexOf("Name", initialsEndIndex);
//            int nameEndIndex = string.indexOf(",", nameStartIndex);
//            String name = string.substring(nameStartIndex + nameIndexLength, nameEndIndex-1);
//            int saleryNumberStartIndex = string.indexOf("Name", nameEndIndex);
//            int saleryEndIndex = string.indexOf(",", saleryNumberStartIndex);
//            String saleryNumber = string.substring(saleryNumberStartIndex + saleryNumberIndexLength, saleryEndIndex-1);
                workers.add(new Worker(name, initials, saleryNumber));
            }
        }
        for (Worker worker : workers) {
            System.out.println(worker);
        }
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
