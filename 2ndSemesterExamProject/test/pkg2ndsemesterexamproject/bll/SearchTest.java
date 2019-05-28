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
public class SearchTest {
    
    public SearchTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    @Test
    public void testSearchAllProductionOrders() throws Exception {
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
        String searchString = "3";
        List<IProductionOrder> orders = new ArrayList();
        orders.add(po1);
        orders.add(po2);
        orders.add(po3);
        String departmentName = "";
        Search instance = new Search();
        List<IProductionOrder> expResult = new ArrayList();
        expResult.add(po1);
        expResult.add(po2);
        expResult.add(po3);
        List<IProductionOrder> result = instance.searchAllProductionOrders(
                searchString, orders, departmentName);
        assertEquals(expResult, result);
    }
    
}
