
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
public class ProductionOrder implements IProductionOrder {

    private Order order;
    private Delivery delivery;
    private Costumer costumer;
    private List<IDepartmentTask> departmenTask;

    public ProductionOrder(Order order, Delivery delivery, Costumer costumer) {
        this.order = order;
        this.delivery = delivery;
        this.costumer = costumer;
        this.departmenTask = new ArrayList();
    }


    @Override
    public List<IDepartmentTask> getDepartmentTasks() {
        return departmenTask;
    }

    @Override
    public void addDepartmentTask(IDepartmentTask task) {
        departmenTask.add(task);
    }

    @Override
    public void removeDepartmentTask(IDepartmentTask task) {
        departmenTask.remove(task);
    }
}
