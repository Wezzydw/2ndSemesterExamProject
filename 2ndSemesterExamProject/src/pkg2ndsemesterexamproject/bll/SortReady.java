package pkg2ndsemesterexamproject.bll;

import java.util.ArrayList;
import java.util.List;
import pkg2ndsemesterexamproject.be.IProductionOrder;

public class SortReady implements ISortStrategy {

    /**
     * Denne metode kalder readySort og sender en liste af ProductionOrder med
     * ned.
     *
     * @param list Listen af afdelingens ProductionOrders.
     * @param departmentName Kommer fra den Department forespørgsel bliver
     * lavet.
     * @return En sorteret liste af af ProductionOrder, alt efter om en
     * ProductionOrder er sat til Ready(om afdelingen kan påbegynde deres task).
     */
    @Override
    public List<IProductionOrder> sort(List<IProductionOrder> list, String departmentName) {
        return readySort(list, departmentName);
    }

    /**
     * Denne metode sørger for at sortere listen af ProductionOrder, alt efter
     * om en ProductionOrder er sat til Ready(om afdelingen kan påbegynde deres
     * task)
     *
     * @param list Listen af ProductionOrder den henter oppe fra sort metoden.
     * @param departmentName Kommer fra den Department forespørgsel bliver
     * lavet.
     * @return En sorteret liste af af ProductionOrder, alt efter om en
     * ProductionOrder er sat til Ready(om afdelingen kan påbegynde deres task).
     */
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

    @Override
    public String toString() {
        return "By Ready";
    }
}
