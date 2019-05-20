
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
import pkg2ndsemesterexamproject.dal.ReadConfig;

/**
 *
 * @author Wezzy Laptop
 */
public class DataHandler implements IDataHandler {

    private PassThrough passThrough;
    private List<IProductionOrder> all;
    private Search searcher;

    public DataHandler() throws IOException {
        passThrough = new PassThrough();
        searcher = new Search();
    }

    public List<IProductionOrder> getAllRelevantProductionOrders(String departmentName, String searchString, ISortStrategy strategy) throws SQLException, IOException {

        LocalDate today = LocalDate.now();
        List<IProductionOrder> returnList = new ArrayList();

        List<IProductionOrder> all = passThrough.getAllProductionOrders();
        loop:
        for (IProductionOrder iProductionOrder : all) {
            for (IDepartmentTask departmentTask : iProductionOrder.getDepartmentTasks()) {
                if (departmentName.equals(departmentTask.getDepartment().getName())
                        && !departmentTask.getFinishedOrder()
                        && (departmentTask.getStartDate().minusDays(ReadConfig.getOffsetFromDepartmentName(departmentName)).isBefore(today)
                        || departmentTask.getStartDate().minusDays(ReadConfig.getOffsetFromDepartmentName(departmentName)).isEqual(today)
                        || departmentTask.getStartDate().isEqual(today))) {

                    returnList.add(iProductionOrder);
                    continue loop;
                }
            }
        }
        return strategy.sort(searcher.searchAllProductionOrders(searchString, returnList, departmentName.toLowerCase()), departmentName);
    }

    public IDepartmentTask getTaskForDepartment(IProductionOrder po, String departmentName) {
        for (IDepartmentTask departmentTask : po.getDepartmentTasks()) {
            if (departmentTask.getDepartment().getName().equals(departmentName)) {
                return departmentTask;
            }
        }
        return null;
    }

}
