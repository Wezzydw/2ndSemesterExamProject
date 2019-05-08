
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.bll;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import pkg2ndsemesterexamproject.be.IDepartmentTask;
import pkg2ndsemesterexamproject.be.IProductionOrder;
import pkg2ndsemesterexamproject.be.IWorker;

/**
 *
 * @author Wezzy Laptop
 */
public class DataHandler implements IDataHandler {

    private PassThrough passThrough;

    public DataHandler() throws IOException {
        passThrough = new PassThrough();
    }

    public List<IProductionOrder> getAllRelevantProductionOrders(String departmentName) throws SQLException {
        LocalDate today = LocalDate.now();
        List<IProductionOrder> returnList = new ArrayList();
        List<IProductionOrder> all = passThrough.getAllProductionOrders();
        for (IProductionOrder iProductionOrder : all) {
            for (IDepartmentTask departmentTask : iProductionOrder.getDepartmentTasks()) {
                System.out.println(departmentName);
                System.out.println(departmentTask.getDepartment().getName());
                
                if (departmentName.equals(departmentTask.getDepartment().getName())
                        && (departmentTask.getStartDate().toLocalDate().isAfter(today)
                        || departmentTask.getStartDate().toLocalDate().isEqual(today))) {
                    returnList.add(iProductionOrder);
                }
            }

        }
        return returnList;
    }

}
