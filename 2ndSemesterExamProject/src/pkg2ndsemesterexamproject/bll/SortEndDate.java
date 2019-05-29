/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.bll;

import java.util.List;
import pkg2ndsemesterexamproject.be.IDepartmentTask;
import pkg2ndsemesterexamproject.be.IProductionOrder;

/**
 *
 * @author andreas
 */
public class SortEndDate implements ISortStrategy {

    private List<IProductionOrder> list1;
    
    /**
     * Kalder Klassens sortByEnd() metode, og sender listen af afdelingens
     * ProductionOrders med.
     * @param list Listen af afdelingens ProductionOrders.
     * @param departmentName Kommer fra den Department forespørgsel bliver lavet.
     * @return Listen den får retur fra sortByEnd().
     */
    @Override
    public List<IProductionOrder> sort(List<IProductionOrder> list, String departmentName) {
        list1 = list;
        return sortByEnd(list1, departmentName);
    }
    
    /**
     * Sorterer listen af ProductionOrders for de forskellige tasks endDate.
     * Fra tidligst til senest.
     * @param list Listen den henter oppe fra sort() metoden.
     * @param departmentName Kommer fra den Department forespørgsel bliver lavet.
     * @return en sorteret liste af endDates fra tidligst til senest.
     */
    private List<IProductionOrder> sortByEnd(List<IProductionOrder> list, String departmentName) {
        IDepartmentTask temp = null;
        IProductionOrder temp2 = null;
        for (int k = 0; k < list1.size(); k++) {
            for (int i = 0; i < list1.size(); i++) {
                for (int j = 0; j < list1.get(i).getDepartmentTasks().size(); j++) {
                    if (list1.get(i).getDepartmentTasks().get(j).getDepartment().getName().equals(departmentName)) {
                        if (temp != null && temp2 != null) {
                            if (list1.get(i).getDepartmentTasks().get(j).getEndDate().isBefore(temp.getEndDate())) {
                                if (i - 1 >= 0) {
                                    swapPlaces(i, i - 1);
                                }
                            }
                        }
                        temp = list1.get(i).getDepartmentTasks().get(j);
                        temp2 = list1.get(i);
                    }
                }
            }
        }
        return list1;
    }
    
    /**
     * Denne metode sørger for at skifte pladser for index i og j. 
     * @param i index i listen.
     * @param j index i listen.
     */
    private void swapPlaces(int i, int j) {
        IProductionOrder temp = list1.get(i);
        list1.set(i, list1.get(j));
        list1.set(j, temp);
    }
    
      @Override
    public String toString() {
        return "End Date";
    }

}
