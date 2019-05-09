
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
    private List<IProductionOrder> all;

    public DataHandler() throws IOException {
        passThrough = new PassThrough();

    }

    public List<IProductionOrder> getAllRelevantProductionOrders(String departmentName) throws SQLException {

        LocalDate today = LocalDate.now();
        List<IProductionOrder> returnList = new ArrayList();

        long a = System.currentTimeMillis();
        List<IProductionOrder> all = passThrough.getAllProductionOrders();
        System.out.println("Right before loop " + (System.currentTimeMillis() - a) / 1000);

        int counter1 = 0;
        int counter2 = 0;
        System.out.println(all.size());

        loop:
        for (IProductionOrder iProductionOrder : all) {

            counter1++;
            for (IDepartmentTask departmentTask : iProductionOrder.getDepartmentTasks()) {
                counter2++;
                if (departmentName.equals(departmentTask.getDepartment().getName())
                        && !departmentTask.getFinishedOrder()
                        && (departmentTask.getStartDate().toLocalDate().isAfter(today)
                        || departmentTask.getStartDate().toLocalDate().isEqual(today))) {
                    returnList.add(iProductionOrder);
                    System.out.println("Continues");
                    continue loop;
                }
            }
        }
        System.out.println("counter1 " + counter1);
        System.out.println("counter2 " + counter2);
        System.out.println(returnList.size());
        return returnList;
    }
    
    public IDepartmentTask getTaskForDepartment(IProductionOrder po, String departmentName){
        for (IDepartmentTask departmentTask : po.getDepartmentTasks())
        {
            if(departmentTask.getDepartment().getName().equals(departmentName)){
                return departmentTask;
            }
        }
        return null;
    }
    
}
