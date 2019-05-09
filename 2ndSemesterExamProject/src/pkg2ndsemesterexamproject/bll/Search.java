/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.bll;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pkg2ndsemesterexamproject.be.IDepartmentTask;
import pkg2ndsemesterexamproject.be.IProductionOrder;
import pkg2ndsemesterexamproject.be.IWorker;
import pkg2ndsemesterexamproject.dal.GetData;
import pkg2ndsemesterexamproject.dal.IGetData;
import pkg2ndsemesterexamproject.dal.ProductionOrderDAO;

/**
 *
 * @author Wezzy Laptop
 */
public class Search {

    IGetData getData;

    public Search() throws IOException {
        getData = new GetData();

    }

    public List<IProductionOrder> searchAllProductionOrders(String searchString, List<IProductionOrder> orders, String departmentName) throws SQLException {
        if (searchString.isEmpty()) {
            System.out.println("Rerutrn");
            return orders;
        }

        List< IProductionOrder> toReturn = new ArrayList();
        loop:
        for (IProductionOrder ipo : orders) {
            for (IDepartmentTask idt : ipo.getDepartmentTasks()) {

//                if (idt.getDepartment().getName().contains(searchString)) {
//                    toReturn.add(ipo);
//                    System.out.println("added departemnet");
//                    continue loop;
//                }
                if (idt.getEndDate().toString().toLowerCase().contains(searchString) && idt.getDepartment().getName().toLowerCase().equals(departmentName)) {
                    System.out.println(idt.getEndDate().toString());
                    toReturn.add(ipo);
                    System.out.println("added enddate");
                    continue loop;
                }
                if (idt.getStartDate().toString().toLowerCase().contains(searchString) && idt.getDepartment().getName().toLowerCase().equals(departmentName)) {
                    toReturn.add(ipo);
                    System.out.println("added startdate");
                    continue loop;
                }

//                for (IWorker workers : idt.getActiveWorkers()) {
//                    if (workers.getName().contains(searchString) || workers.getInitials().contains(searchString)) {
//                        if (!toReturn.contains(idt)) {
//                            toReturn.add(ipo);
//                            continue loop;
//                        }
//                    }
//                }
            }
            if (ipo.getCustomer().getName().toLowerCase().contains(searchString)) {
                toReturn.add(ipo);
                System.out.println("added constumer");
                continue loop;
            }
//            if (ipo.getDelivery().getDeliveryTime().toString().toLowerCase().contains(searchString)) {
//                toReturn.add(ipo);
//                System.out.println("addedDeliveryTime");
//                continue loop;
//            }
            if (ipo.getOrder().getOrderNumber().toLowerCase().contains(searchString)) {
                toReturn.add(ipo);
                System.out.println("added ordernumber");
                continue loop;
            }

        }
        System.out.println(toReturn.size());
        return toReturn;
    }
}
