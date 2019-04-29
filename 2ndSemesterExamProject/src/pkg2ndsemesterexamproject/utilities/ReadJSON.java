/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.utilities;

import pkg2ndsemesterexamproject.bll.DataManager;
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
public class ReadJSON {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        DataManager dm = new DataManager();
        dm.extractWorkersFromJSON();
        
//        File f = new File("C:\\Users\\Wezzy Laptop\\Desktop\\JSON.txt");
//        FileReader fr = new FileReader("C:\\Users\\Wezzy Laptop\\Desktop\\JSON.txt");
//        BufferedReader br = new BufferedReader(fr);
//        String s = br.readLine();
//        System.out.println(s);
//        List<String> ls = new ArrayList();
//        char[] c = s.toCharArray();
//        int bo = 0;
//        int bc = 0;
//        int co = 0;
//        int cc = 0;
//        int index = 0;
//        String[] arrat = s.split("\\[");
//        System.out.println(s);
////        System.out.println(c.length);
//        for (int i = 0; i < c.length; i++) {
//            if (c[i] == '{') {
//                co++;
//            }
//
//            if (c[i] == '}') {
//                cc++;
//            }
//            if (c[i] == '[') {
//                bo++;
//            }
//            if (c[i] == ']') {
//                bo++;
//            }
//            if (bc == cc - 1) {
//                ls.add(s.substring(index, i+1));
//                index = i+1;
//                i++;
//                bc = 0;
//                cc = 0;
//            }
//        }
//        
//        for(int i = 0; i < arrat.length; i++)
//        {
//            System.out.println(arrat[i]);
//            System.out.println("-----------------");
//            System.out.println("-----------------");
//            System.out.println("-----------------");
//            System.out.println("-----------------");
//            System.out.println("-----------------");
//        }
//
    }

}
