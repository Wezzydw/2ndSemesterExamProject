
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

    private final IOrder order;
    private final IDelivery delivery;
    private final ICustomer customer;
    private final List<IDepartmentTask> departmenTask;

    public ProductionOrder(IOrder order, IDelivery delivery, ICustomer customer, List<IDepartmentTask> tasks) {
        this.order = order;
        this.delivery = delivery;
        this.customer = customer;
        this.departmenTask = new ArrayList();
        departmenTask.addAll(tasks);
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

    @Override
    public IOrder getOrder() {
        return order;
    }

    @Override
    public IDelivery getDelivery() {
        return delivery;
    }

    @Override
    public ICustomer getCustomer() {
        return customer;
    }

    @Override
    public String toString() {
        return order + delivery.toString() + customer + departmenTask;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + order.hashCode();
        hash = 41 * hash + customer.hashCode();
        hash = 41 * hash + departmenTask.hashCode();
        return hash;
    }
}
