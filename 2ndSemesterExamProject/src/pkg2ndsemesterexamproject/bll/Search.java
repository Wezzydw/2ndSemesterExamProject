/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.bll;

import java.util.ArrayList;
import java.util.List;
import pkg2ndsemesterexamproject.be.IDepartmentTask;
import pkg2ndsemesterexamproject.be.IProductionOrder;
import pkg2ndsemesterexamproject.be.IWorker;

/**
 *
 * @author Wezzy Laptop
 */
public class Search {

    List<IProductionOrder> allProductionOrders;

    public List<IProductionOrder> searchAllProductionOrders(String searchString) {

        List<IProductionOrder> toReturn = new ArrayList();
        loop:
        for (IProductionOrder ipo : allProductionOrders) {
            for (IDepartmentTask idt : ipo.getDepartmentTasks()) {
                if (idt.getDepartment().getName().contains(searchString)) {
                    toReturn.add(ipo);
                    continue loop;
                }

                if (idt.getEndDate().toString().contains(searchString)) {
                    toReturn.add(ipo);
                    continue loop;
                }
                if (idt.getStartDate().toString().contains(searchString)) {
                    toReturn.add(ipo);
                    continue loop;
                }

                for (IWorker workers : idt.getActiveWorkers()) {
                    if (workers.getName().contains(searchString) || workers.getInitials().contains(searchString)) {
                        if (!toReturn.contains(idt)) {
                            toReturn.add(ipo);
                            continue loop;
                        }
                    }
                }

            }

            if (ipo.getCustomer().getName().contains(searchString)) {
                toReturn.add(ipo);
                continue loop;
            }
            if (ipo.getDelivery().getDeliveryTime().toString().contains(searchString)) {
                toReturn.add(ipo);
                continue loop;
            }
            if (ipo.getOrder().getOrderNumber().contains(searchString)) {
                toReturn.add(ipo);
                continue loop;
            }

        }
        return toReturn;
    }
}
