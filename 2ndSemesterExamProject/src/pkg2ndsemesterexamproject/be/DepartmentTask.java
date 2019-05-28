/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.be;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Wezzy Laptop
 */
public class DepartmentTask implements IDepartmentTask {

    private List<IWorker> activeWorkers;
    private final IDepartment department;
    private final Boolean finishedOrder;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public DepartmentTask(IDepartment department, Boolean finishedOrder, LocalDate startDate, LocalDate endDate) {
        this.activeWorkers = activeWorkers;
        this.department = department;
        this.finishedOrder = finishedOrder;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "DepartmentTask{" + "department=" + department + ", finishedOrder=" + finishedOrder + ", startDate=" + startDate.toString() + ", endDate=" + endDate.toString() + '}';
    }

    @Override
    public List<IWorker> getActiveWorkers() {
        return activeWorkers;
    }

    @Override
    public IDepartment getDepartment() {
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.finishedOrder);
        return hash;
    }

}
