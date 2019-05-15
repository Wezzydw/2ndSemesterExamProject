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
public class SortCustomer implements ISortStrategy, Comparator<IProductionOrder>
{

    @Override
    public List<IProductionOrder> sort(List<IProductionOrder> list, String departmentName)
    {
        List<IProductionOrder> list1 = list;
        Collections.sort(list1, this);
        return list1;
    }

    @Override
    public int compare(IProductionOrder o1, IProductionOrder o2)
    {
        return o1.getCustomer().getName().compareTo(o2.getCustomer().getName());
    }

    @Override
    public String toString()
    {
        return "Customer";
    }
    
}
