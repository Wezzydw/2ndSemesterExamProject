package pkg2ndsemesterexamproject.bll;

import java.util.List;
import pkg2ndsemesterexamproject.be.IDepartmentTask;
import pkg2ndsemesterexamproject.be.IProductionOrder;

public class SortStartDate implements ISortStrategy {

    private List<IProductionOrder> list1;

    /**
     * Kalder Klassens sortStart() metode, og sender listen af afdelingens
     * ProductionOrders med.
     *
     * @param list Listen af afdelingens ProductionOrders.
     * @param departmentName Kommer fra den Department forespørgsel bliver
     * lavet.
     * @return Listen den får retur fra sortByEnd().
     */
    @Override
    public List<IProductionOrder> sort(List<IProductionOrder> list, String departmentName) {
        list1 = list;
        return sortStart(list1, departmentName);
    }

    /**
     * Sorterer listen af ProductionOrders for de forskellige tasks startDate.
     * Fra tidligst til senest.
     *
     * @param list Listen den henter oppe fra sort() metoden.
     * @param departmentName Kommer fra den Department forespørgsel bliver
     * lavet.
     * @return en sorteret liste af startDates fra tidligst til senest.
     */
    private List<IProductionOrder> sortStart(List<IProductionOrder> list1, String departmentName) {
        IDepartmentTask temp = null;
        IProductionOrder temp2 = null;
        for (int k = 0; k < list1.size(); k++) {
            for (int i = 0; i < list1.size(); i++) {
                for (int j = 0; j < list1.get(i).getDepartmentTasks().size(); j++) {
                    if (list1.get(i).getDepartmentTasks().get(j).getDepartment().getName().equals(departmentName)) {
                        if (temp != null && temp2 != null) {
                            if (list1.get(i).getDepartmentTasks().get(j).getStartDate().isBefore(temp.getStartDate())) {
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
     *
     * @param i index i listen.
     * @param j index i listen.
     */
    private void swapPlaces(int i, int j) {
        if (list1 != null) {
            IProductionOrder temp = list1.get(i);
            list1.set(i, list1.get(j));
            list1.set(j, temp);
        }
    }

    @Override
    public String toString() {
        return "Start Date";
    }
}
