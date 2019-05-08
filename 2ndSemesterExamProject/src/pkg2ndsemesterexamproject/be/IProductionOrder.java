/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.be;

import java.util.List;

/**
 *
 * @author Wezzy Laptop
 */
public interface IProductionOrder {

    List<IDepartmentTask> getDepartmentTasks();

    void addDepartmentTask(IDepartmentTask task);

    void removeDepartmentTask(IDepartmentTask task);

    IOrder getOrder();

    IDelivery getDelivery();

    ICustomer getCustomer();

}
