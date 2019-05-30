package pkg2ndsemesterexamproject.bll;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import pkg2ndsemesterexamproject.be.IDepartment;
import pkg2ndsemesterexamproject.be.IDepartmentTask;
import pkg2ndsemesterexamproject.be.IProductionOrder;

public class OrdersPerDepartment {

    /**
     * Genererer en liste af ProductionOrder ud fra Department der er sendt med
     *
     * @param department et objekt af Department, der skal laves en liste af
     * ProductionOrder til.
     * @param po en liste af alle ProductionOrder
     * @return En liste af ProductionOrder ud fra Department der er sendt med
     */
    public List<IProductionOrder> getAllProductionOrdersRelatedToDpartment(IDepartment department, List<IProductionOrder> po) {
        List<IProductionOrder> rele = new ArrayList();
        for (IProductionOrder iProductionOrder : po) {
            List<IDepartmentTask> tasks = iProductionOrder.getDepartmentTasks();
            for (int i = 0; i < tasks.size(); i++) {
                tasks.get(i);
                if (tasks.get(i).getDepartment().getName().equals(department.getName())
                        && !tasks.get(i).getFinishedOrder()
                        && tasks.get(i).getStartDate().isAfter(LocalDate.now())) {
                    rele.add(iProductionOrder);
                }
            }
        }
        return rele;
    }

    /**
     * Denne metode laver en liste af alle DepartmentTasks i en ProductionOrder
     * som kom før den DepartmentTask som er inde i denne Department.
     *
     * @param po Et objekt af ProductionOrder som DepartmentTasks skal hentes ud
     * af.
     * @param department Afdelingen som forespørgslen bliver foretaget i.
     * @return En liste af DepartmentTasks
     */
    public List<IDepartmentTask> getAlltasksBefore(IProductionOrder po, IDepartment department) {
        List<IDepartmentTask> before = new ArrayList();
        List<IDepartmentTask> tasks = po.getDepartmentTasks();
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getDepartment().getName().equals(department.getName())) {
                for (int j = 0; j < i - 1; j++) {
                    before.add(tasks.get(j));
                }
            }
        }
        return before;
    }

    /**
     * Finder en DepartmentTask ud fra det departmentName man sender ind, ud fra
     * listen af DepartmentTasks en ProductionOrder indeholder.
     *
     * @param po indeholder en liste af DepartmentTasks
     * @param departmentName Department man skal finde DepartmentTasks til.
     * @return En DepartmentTask ud fra det departmentName man sender ind.
     */
    public IDepartmentTask getTasksForDepartment(IProductionOrder po, String departmentName) {
        for (IDepartmentTask departmentTask : po.getDepartmentTasks()) {
            if (departmentTask.getDepartment().getName().equals(departmentName)) {
                return departmentTask;
            }
        }
        return null;
    }
}
