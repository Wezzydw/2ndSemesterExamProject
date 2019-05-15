/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.bll;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import pkg2ndsemesterexamproject.be.IDepartment;
import pkg2ndsemesterexamproject.be.IDepartmentTask;
import pkg2ndsemesterexamproject.be.IProductionOrder;

/**
 *
 * @author andreas
 */
public class OrdersPerDepartment
{
    public List<IProductionOrder> getAllProductionOrdersRelatedToDpartment(IDepartment department, List<IProductionOrder> po){
        List<IProductionOrder> rele = new ArrayList();
        for (IProductionOrder iProductionOrder : po)
        {
            List<IDepartmentTask> tasks  = iProductionOrder.getDepartmentTasks();
            for (int i = 0; i < tasks.size(); i++)
            {
                tasks.get(i);
                if(tasks.get(i).getDepartment().getName().equals(department.getName()) 
                        && !tasks.get(i).getFinishedOrder()
                        && tasks.get(i).getStartDate().isAfter(LocalDate.now())){
                    rele.add(iProductionOrder);
                }
            }
        }
        return rele;
    }
    public List<IDepartmentTask> getAlltasksBefore(IProductionOrder po, IDepartment department){
        List<IDepartmentTask> before = new ArrayList();
        List<IDepartmentTask> tasks = po.getDepartmentTasks();
        for (int i = 0; i < tasks.size(); i++)
        {
            if(tasks.get(i).getDepartment().getName().equals(department.getName())){
                for (int j = 0; j < i-1; j++)
                    {
                        before.add(tasks.get(j));
                    }
            }
        }
        return before;
    }
    public IDepartmentTask getTasksForDepartment(IProductionOrder po, String departmentName){
        for (IDepartmentTask departmentTask : po.getDepartmentTasks())
        {
            if(departmentTask.getDepartment().getName().equals(departmentName)){
                return departmentTask;
            }
        }
        
//        List<IDepartmentTask> dat = new ArrayList();
//        for (IProductionOrder iProductionOrder : po)
//        {
//            for (int i = 0; i < iProductionOrder.getDepartmentTasks().size(); i++)
//            {
//                if(iProductionOrder.getDepartmentTasks().get(i).getDepartment().getName().equals(department.getName())){
//                   dat.add(iProductionOrder.getDepartmentTasks().get(i));
//                }
//            }
//        }
//        return dat;
        return null;
    }
}
