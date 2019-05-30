/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.bll;

import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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

    /**
     * Denne metode kigger på EndDate, StartDate, Customer navn og Ordrenummer i
     * en ProductionOrder, og returnerer den eller de ordre brugeren søger på.
     *
     * @param searchString Søgeordet brugeren skriver ind i textfeltet i viewet.
     * @param orders en liste med alle ProductionOrder.
     * @param departmentName Department som ProductionOrder tilhører.
     * @return en liste af den eller de ProductionOrder brugeren søger på:
     * EndDate, StartDate, Customer navn og Ordrenummer.
     * @throws SQLException
     */
    public List<IProductionOrder> searchAllProductionOrders(String searchString, List<IProductionOrder> orders, String departmentName) throws SQLException {
        if (searchString.isEmpty()) {
            return orders;
        }

        List< IProductionOrder> toReturn = new ArrayList();
        loop:
        for (IProductionOrder ipo : orders) {
            for (IDepartmentTask idt : ipo.getDepartmentTasks()) {

                WeekFields weekFields = WeekFields.of(Locale.getDefault());
                int weekNumber = idt.getEndDate().get(weekFields.weekOfWeekBasedYear());
                String txtEndDate = "[" + weekNumber + ":" + idt.getEndDate().getDayOfWeek().getValue() + "]";
                if (txtEndDate.contains(searchString) && idt.getDepartment().getName().toLowerCase().equals(departmentName)) {
                    toReturn.add(ipo);
                    continue loop;
                }
                weekNumber = idt.getStartDate().get(weekFields.weekOfWeekBasedYear());
                String txtStartDate = "[" + weekNumber + ":" + idt.getStartDate().getDayOfWeek().getValue() + "]";
                if (txtStartDate.contains(searchString) && idt.getDepartment().getName().toLowerCase().equals(departmentName)) {
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
