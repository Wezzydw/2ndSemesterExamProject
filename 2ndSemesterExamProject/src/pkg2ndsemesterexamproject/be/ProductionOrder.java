/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.be;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Wezzy Laptop
 */
public class ProductionOrder {

    private Order order;
    private Delivery delivery;
    private Costumer costumer;
    private List<DepartmentTask> departmenTask;

    public ProductionOrder(Order order, Delivery delivery, Costumer costumer) {
        this.order = order;
        this.delivery = delivery;
        this.costumer = costumer;
        this.departmenTask = new ArrayList();
    }

    public void addDepartmentTask(DepartmentTask departmentTask) {
        departmenTask.add(departmentTask);
    }
    
    public void removeDepartmentTask(DepartmentTask departmentTask)
    {
        departmenTask.remove(departmentTask);
    }
}
