/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.bll;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import pkg2ndsemesterexamproject.be.Customer;
import pkg2ndsemesterexamproject.be.Delivery;
import pkg2ndsemesterexamproject.be.Department;
import pkg2ndsemesterexamproject.be.DepartmentTask;
import pkg2ndsemesterexamproject.be.IDepartmentTask;
import pkg2ndsemesterexamproject.be.IProductionOrder;
import pkg2ndsemesterexamproject.be.Order;
import pkg2ndsemesterexamproject.be.ProductionOrder;

/**
 *
 * @author mpoul
 */
public class SortReadyTest {
    
    public SortReadyTest() {
    }

    @Test
    public void testSort() {
        System.out.println("Sort");
        
        DepartmentTask deptask1 = new DepartmentTask(new Department("Afdeling 1"), true, LocalDate.of(2019, Month.FEBRUARY, 07), LocalDate.of(2019, Month.MAY, 29));
        DepartmentTask deptask2 = new DepartmentTask(new Department("Afdeling 2"), false, LocalDate.of(2019, Month.MARCH, 10), LocalDate.of(2019, Month.JUNE, 20));
        DepartmentTask deptask3 = new DepartmentTask(new Department("Afdeling 1"), true, LocalDate.of(2019, Month.APRIL, 19), LocalDate.of(2019, Month.JULY, 13));
        DepartmentTask deptask4 = new DepartmentTask(new Department("Afdeling 2"), true, LocalDate.of(2019, Month.FEBRUARY, 07), LocalDate.of(2019, Month.MAY, 29));
        DepartmentTask deptask5 = new DepartmentTask(new Department("Afdeling 1"), false, LocalDate.of(2019, Month.MARCH, 10), LocalDate.of(2019, Month.JUNE, 20));
        DepartmentTask deptask6 = new DepartmentTask(new Department("Afdeling 2"), false, LocalDate.of(2019, Month.APRIL, 19), LocalDate.of(2019, Month.JULY, 13));
        
        List<IDepartmentTask> dt1 = new ArrayList();
        List<IDepartmentTask> dt2 = new ArrayList();
        List<IDepartmentTask> dt3 = new ArrayList();
        dt3.add(deptask1);
        dt3.add(deptask2);
        dt2.add(deptask3);
        dt2.add(deptask4);
        dt1.add(deptask5);
        dt1.add(deptask6);
        
        IProductionOrder po1 = new ProductionOrder(new Order("123456"), new Delivery(LocalDate.now()), new Customer("Føtex"), dt1);
        IProductionOrder po2 = new ProductionOrder(new Order("673482"), new Delivery(LocalDate.now()), new Customer("Bilka"), dt2);
        IProductionOrder po3 = new ProductionOrder(new Order("134533"), new Delivery(LocalDate.now()), new Customer("Fakta"), dt3);
                
        List<IProductionOrder> list = new ArrayList();
        list.add(po1);
        list.add(po2);
        list.add(po3);
        String departmentName = "Afdeling 2";
        SortReady instance = new SortReady();
        List<IProductionOrder> expResult = new ArrayList();
        expResult.add(po2);
        expResult.add(po3);
        expResult.add(po1);
        List<IProductionOrder> result = instance.sort(list, departmentName);
        assertEquals(expResult, result);
    }
    
}
