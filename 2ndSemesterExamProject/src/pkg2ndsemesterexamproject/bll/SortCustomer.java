package pkg2ndsemesterexamproject.bll;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import pkg2ndsemesterexamproject.be.IProductionOrder;

public class SortCustomer implements ISortStrategy, Comparator<IProductionOrder> {

    /**
     * Bruger klassens compare metode til at sortere listen af ProductionOrder
     *
     * @param list Listen af ProductionOrders der skal sorteres.
     * @param departmentName Kommer fra den Department forespørgsel bliver
     * lavet.
     * @return en sorterer en liste af ProductionOrder i alfabetisk rækkefølge
     * efter kunde navn.
     */
    @Override
    public List<IProductionOrder> sort(List<IProductionOrder> list, String departmentName) {
        List<IProductionOrder> list1 = list;
        Collections.sort(list1, this);
        return list1;
    }

    /**
     * Sammenligner Kunde navn fra 2 objekter af ProductionOrder
     *
     * @param o1 ProductionOrder objekt 1
     * @param o2 ProductionOrder objekt 2
     * @return en int-værdi alt efter om String(kunde navn) i o1 kommer før
     * eller efter String(Kunde navn) i o2.
     */
    @Override
    public int compare(IProductionOrder o1, IProductionOrder o2) {
        return o1.getCustomer().getName().compareTo(o2.getCustomer().getName());
    }

    @Override
    public String toString() {
        return "Customer";
    }
}
