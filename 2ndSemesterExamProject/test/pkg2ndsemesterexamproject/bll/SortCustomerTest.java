/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.bll;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
public class SortCustomerTest {
    
    public SortCustomerTest() throws IOException 
    {
    }
    
    @Test
    public void testSort() 
    {
        IProductionOrder po1 = new ProductionOrder(new Order("123456"), new Delivery(LocalDate.now()), new Customer("Føtex"), new ArrayList());
        IProductionOrder po2 = new ProductionOrder(new Order("6734824"), new Delivery(LocalDate.now()), new Customer("Bilka"), new ArrayList());
        IProductionOrder po3 = new ProductionOrder(new Order("1345346"), new Delivery(LocalDate.now()), new Customer("Fakta"), new ArrayList());
                
        List<IProductionOrder> list = new ArrayList();
        list.add(po1);
        list.add(po2);
        list.add(po3);
        String departmentName = "";
        SortCustomer instance = new SortCustomer();
        List<IProductionOrder> expResult = new ArrayList();
        expResult.add(po2);
        expResult.add(po3);
        expResult.add(po1);
        List<IProductionOrder> result = instance.sort(list, departmentName);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testNullSort() 
    {
        IProductionOrder po1 = new ProductionOrder(new Order("123456"), new Delivery(LocalDate.now()), new Customer("Føtex"), new ArrayList());
        IProductionOrder po2 = new ProductionOrder(new Order("6734824"), new Delivery(LocalDate.now()), new Customer("Bilka"), new ArrayList());
        IProductionOrder po3 = new ProductionOrder(new Order("1345346"), new Delivery(LocalDate.now()), new Customer("Fakta"), new ArrayList());
                
        List<IProductionOrder> list = null;
        String departmentName = "";
        SortCustomer instance = new SortCustomer();
        List<IProductionOrder> expResult = new ArrayList();
        expResult.add(po2);
        expResult.add(po3);
        expResult.add(po1);
        List<IProductionOrder> result = instance.sort(list, departmentName);
        assertEquals(expResult, result);
        assertNull(result);
    }
    
    @Test
    public void testSortSameObjects() 
    {
        IProductionOrder po1 = new ProductionOrder(new Order("123456"), new Delivery(LocalDate.now()), new Customer("Føtex"), new ArrayList());
        IProductionOrder po2 = new ProductionOrder(new Order("6734824"), new Delivery(LocalDate.now()), new Customer("Bilka"), new ArrayList());
        IProductionOrder po3 = new ProductionOrder(new Order("1345346"), new Delivery(LocalDate.now()), new Customer("Fakta"), new ArrayList());
                
        List<IProductionOrder> list = new ArrayList();
        list.add(po1);
        list.add(po1);
        list.add(po1);
        String departmentName = "";
        SortCustomer instance = new SortCustomer();
        List<IProductionOrder> expResult = new ArrayList();
        expResult.add(po1);
        expResult.add(po1);
        expResult.add(po1);
        List<IProductionOrder> result = instance.sort(list, departmentName);
        assertEquals(expResult, result);
    }
    
}
