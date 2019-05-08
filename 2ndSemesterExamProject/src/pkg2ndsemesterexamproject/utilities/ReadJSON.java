/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.utilities;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import pkg2ndsemesterexamproject.be.IProductionOrder;
import pkg2ndsemesterexamproject.bll.DataHandler;
import pkg2ndsemesterexamproject.dal.JsonFormater;
import pkg2ndsemesterexamproject.dal.ProductionOrderDAO;

/**
 *
 * @author Wezzy Laptop
 */
public class ReadJSON {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, SQLException {
        //JsonFormater dm = new JsonFormater();
        //dm.extractWorkersFromJSON();
        //dm.extractProductionOrdersFromJSON();
//        DataHandler hd = new DataHandler();
//        long a = System.currentTimeMillis();
//        hd.getAllRelevantProductionOrders("Maler");
//        System.out.println("Done1 - Time: " + (System.currentTimeMillis() - a)/1000);
//        hd.getAllRelevantProductionOrders("Bælg");
//        System.out.println("Done2 - Time: " + (System.currentTimeMillis() - a)/1000);
//        hd.getAllRelevantProductionOrders("Halv");
//        System.out.println("Done3 - Time: " + (System.currentTimeMillis() - a)/1000);

        ProductionOrderDAO p = new ProductionOrderDAO();
        for (IProductionOrder pa : p.getAllInfo()) {
            System.out.println(pa);
        }
    }

}
