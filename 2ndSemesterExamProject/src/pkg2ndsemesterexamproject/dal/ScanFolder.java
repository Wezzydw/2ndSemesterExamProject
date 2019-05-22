/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.dal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mpoul
 */
public class ScanFolder {

    private List<String> allFiles;

    public ScanFolder() throws IOException {
        allFiles = loadSavedFile();
    }

    public void updateFiles() throws IOException {
        File files = new File("data/");
        File[] listOfFiles = files.listFiles();
        List<String> filePaths = new ArrayList<>();
        for (File file : listOfFiles) {
            if (file.isFile()) {
                if ((file.getName().endsWith(".xlsx") || file.getName().endsWith(".csv") || file.getName().endsWith(".txt")) && !hasFileBeenSaved(file.getName())) {
                    filePaths.add(file.getName());
                    writeSavedFile(file.getName());
                    allFiles.add(file.getName());
                }
            }
        }

    }

    public List<String> loadSavedFile() throws FileNotFoundException, IOException {
        FileReader fileReader = new FileReader(new File("data/readfiles.txt"));
        BufferedReader br = new BufferedReader(fileReader);
        List<String> readFile = new ArrayList();
        String line;

        while ((line = br.readLine()) != null) {
            readFile.add(line);
        }
        return readFile;
    }

    public void writeSavedFile(String linesToWrite) throws IOException {
        FileWriter writer = new FileWriter(new File("data/readfiles.txt"), true);
        BufferedWriter bw = new BufferedWriter(writer);
        bw.newLine();
        bw.append(linesToWrite);
        bw.close();
    }

    public boolean hasFileBeenSaved(String filepaths) throws IOException {
        List<String> filesToRead = allFiles;
        if(filepaths.equals("readfiles.txt")){
            return true;
        }
        for (String string : filesToRead) {
            
                if (string.equals(filepaths)) {
                    
                    return true;
                
            }
        }
        return false;
    }

}
