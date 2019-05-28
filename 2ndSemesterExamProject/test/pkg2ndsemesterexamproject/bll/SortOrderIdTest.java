/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.bll;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pkg2ndsemesterexamproject.be.Customer;
import pkg2ndsemesterexamproject.be.Delivery;
import pkg2ndsemesterexamproject.be.IProductionOrder;
import pkg2ndsemesterexamproject.be.Order;
import pkg2ndsemesterexamproject.be.ProductionOrder;

/**
 *
 * @author mpoul
 */
public class SortOrderIdTest {
    
    public SortOrderIdTest() {
    }
    
   

    @Test
    public void testSort() {
        
        IProductionOrder po1 = new ProductionOrder(
                new Order("123456"), 
                new Delivery(LocalDate.now()), 
                new Customer("Føtex"), 
                new ArrayList());
        IProductionOrder po2 = new ProductionOrder(
                new Order("673482"), 
                new Delivery(LocalDate.now()), 
                new Customer("Bilka"), 
                new ArrayList());
        IProductionOrder po3 = new ProductionOrder(
                new Order("134533"), 
                new Delivery(LocalDate.now()), 
                new Customer("Fakta"), 
                new ArrayList());
                
        List<IProductionOrder> list = new ArrayList();
        list.add(po1);
        list.add(po2);
        list.add(po3);
        String departmentName = "";
        SortOrderId instance = new SortOrderId();
        List<IProductionOrder> expResult = new ArrayList();
        expResult.add(po1);
        expResult.add(po3);
        expResult.add(po2);
        List<IProductionOrder> result = instance.sort(list, departmentName);
        assertEquals(expResult, result);
    }

    @Test
    public void testSortDifferentOrderLengths() {
        
        IProductionOrder po1 = new ProductionOrder(
                new Order("123-456"), 
                new Delivery(LocalDate.now()), 
                new Customer("Føtex"), 
                new ArrayList());
        IProductionOrder po2 = new ProductionOrder(
                new Order("6734-8234"), 
                new Delivery(LocalDate.now()), 
                new Customer("Bilka"), 
                new ArrayList());
        IProductionOrder po3 = new ProductionOrder(
                new Order("1345-332"), 
                new Delivery(LocalDate.now()), 
                new Customer("Fakta"), 
                new ArrayList());
                
        List<IProductionOrder> list = new ArrayList();
        list.add(po1);
        list.add(po2);
        list.add(po3);
        String departmentName = "";
        SortOrderId instance = new SortOrderId();
        List<IProductionOrder> expResult = new ArrayList();
        expResult.add(po1);
        expResult.add(po3);
        expResult.add(po2);
        List<IProductionOrder> result = instance.sort(list, departmentName);
        assertEquals(expResult, result);
    }
    
}
