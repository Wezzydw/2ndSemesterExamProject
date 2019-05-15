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

/**
 *
 * @author Wezzy Laptop
 */
public class ReadConfig {

    public int getOffsetFromDepartmentName(String name) throws FileNotFoundException, IOException {
        int offset = 0;
        File file = new File("lib/config.offset");
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line = "";
        while ((line = br.readLine()) != null) {
            if (line.contains(name)) {
                int index = line.indexOf("=") + 1;
                offset = Integer.parseInt(line.substring(index));
            }
        }
        return offset;
    }
}
