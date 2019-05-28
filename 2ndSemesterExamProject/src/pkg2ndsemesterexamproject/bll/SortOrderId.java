/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.bll;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import pkg2ndsemesterexamproject.be.IProductionOrder;

/**
 *
 * @author andreas
 */
public class SortOrderId implements ISortStrategy, Comparator<IProductionOrder> {

    private String departmentName;

    @Override
    public List<IProductionOrder> sort(List<IProductionOrder> list, String departmentName) {
        List<IProductionOrder> list1 = list;
        this.departmentName = departmentName;
        Collections.sort(list1, new SortOrderId());
        return list1;
    }

    @Override
    public int compare(IProductionOrder o1, IProductionOrder o2) {
        if (o1.getOrder().getOrderNumber().length() > o2.getOrder().getOrderNumber().length()) {
            return 1;
        }
        return o1.getOrder().getOrderNumber().compareTo(o2.getOrder().getOrderNumber());
    }

}
