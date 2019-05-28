/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.bll;

import java.util.ArrayList;
import java.util.List;
import pkg2ndsemesterexamproject.be.IProductionOrder;

/**
 *
 * @author andreas
 */
public class SortReady implements ISortStrategy {

    @Override
    public List<IProductionOrder> sort(List<IProductionOrder> list, String departmentName) {
        return readySort(list, departmentName);
    }

    private List<IProductionOrder> readySort(List<IProductionOrder> list, String departmentName) {
        List<IProductionOrder> done = new ArrayList();
        List<IProductionOrder> notDone = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).getDepartmentTasks().size(); j++) {
                if (list.get(i).getDepartmentTasks().get(j).getDepartment().getName().equals(departmentName)) {
                    if (j - 1 == -1 || list.get(i).getDepartmentTasks().get(j - 1).getFinishedOrder()) {
                        done.add(list.get(i));
                    } else {
                        notDone.add(list.get(i));
                    }
                }
            }
        }
        done.addAll(notDone);
        return done;
    }

}
