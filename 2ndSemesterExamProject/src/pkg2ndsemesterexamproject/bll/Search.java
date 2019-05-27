/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.bll;

import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import pkg2ndsemesterexamproject.be.IDepartmentTask;
import pkg2ndsemesterexamproject.be.IProductionOrder;
import pkg2ndsemesterexamproject.dal.GetData;
import pkg2ndsemesterexamproject.dal.IGetData;

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
            return orders;
        }

        List< IProductionOrder> toReturn = new ArrayList();
        loop:
        for (IProductionOrder ipo : orders) {
            for (IDepartmentTask idt : ipo.getDepartmentTasks()) {

                if (idt.getEndDate().format(DateTimeFormatter.ofPattern("d/MM/YYYY")).contains(searchString) && idt.getDepartment().getName().toLowerCase().equals(departmentName)) {
                    toReturn.add(ipo);
                    continue loop;
                }
                if (idt.getStartDate().format(DateTimeFormatter.ofPattern("d/MM/YYYY")).contains(searchString) && idt.getDepartment().getName().toLowerCase().equals(departmentName)) {
                    toReturn.add(ipo);
                    continue loop;
                }

            }
            if (ipo.getCustomer().getName().toLowerCase().contains(searchString)) {
                toReturn.add(ipo);
                continue loop;
            }

            if (ipo.getOrder().getOrderNumber().toLowerCase().contains(searchString)) {
                toReturn.add(ipo);
                continue loop;
            }

        }
        return toReturn;
    }
}
