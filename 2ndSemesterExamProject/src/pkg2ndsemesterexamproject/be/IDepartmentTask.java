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
public interface IDepartmentTask {

    List<IWorker> getActiveWorkers();

    IDepartment getDepartment();

    Boolean getFinishedOrder();

    LocalDate getStartDate();

    LocalDate getEndDate();

    void addWorker(IWorker worker);

    void removeWorker(IWorker worker);

}
