/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.be;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Wezzy Laptop
 */
public class DepartmentTask implements IDepartmentTask {

    private List<IWorker> activeWorkers;
    private Department department;
    private Boolean finishedOrder;
    private LocalDate startDate;
    private LocalDate endDate;

    public DepartmentTask(List<IWorker> activeWorkers, Department department, Boolean finishedOrder, LocalDate startDate, LocalDate endDate) {
        this.activeWorkers = activeWorkers;
        this.department = department;
        this.finishedOrder = finishedOrder;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public List<IWorker> getActiveWorkers() {
        return activeWorkers;
    }

    @Override
    public Department getDepartment() {
        return department;
    }

    @Override
    public Boolean getFinishedOrder() {
        return finishedOrder;
    }

    @Override
    public LocalDate getStartDate() {
        return startDate;
    }

    @Override
    public LocalDate getEndDate() {
        return endDate;
    }

    @Override
    public void addWorker(IWorker worker) {
        activeWorkers.add(worker);
    }

    @Override
    public void removeWorker(IWorker worker) {
        activeWorkers.remove(worker);
    }

}
