


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
public class SortOrderId implements ISortStrategy, Comparator<IProductionOrder>
{

    private String departmentName;
    
    /**
     * Bruger klassens compare metode til at sortere listen af ProductionOrder 
     * efter OrderId: lastet -> højest.
     * @param list Listen af ProductionOrders der skal sorteres.
     * @param departmentName Kommer fra den Department forespørgsel bliver lavet.
     * @return en sorterer en liste af ProductionOrder i numerisk rækkefølge
     * efter OrderId. Lavest til højest
     */
    @Override
    public List<IProductionOrder> sort(List<IProductionOrder> list, String departmentName)
    {
        List<IProductionOrder> list1 = list;
        this.departmentName = departmentName;
        Collections.sort(list1, new SortOrderId());
        return list1;
    }
    
    /**
     * Sammenligner OrderId fra 2 objekter af ProductionOrder.
     * @param o1 ProductionOrder objekt 1
     * @param o2 ProductionOrder objekt 2
     * @return en int-værdi alt efter om OrderId i o1 kommer før eller
     * efter OrderId i o2.
     */
    @Override
    public int compare(IProductionOrder o1, IProductionOrder o2)
    {
        if (o1.getOrder().getOrderNumber().length() > o2.getOrder().getOrderNumber().length())
        {
            return 1;
        }
        return o1.getOrder().getOrderNumber().compareTo(o2.getOrder().getOrderNumber());
    }

    @Override
    public String toString()
    {
        return "Order ID";
    }
}
