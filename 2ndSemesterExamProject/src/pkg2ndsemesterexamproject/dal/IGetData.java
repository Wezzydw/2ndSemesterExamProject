/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.dal;

import pkg2ndsemesterexamproject.be.Department;
import pkg2ndsemesterexamproject.be.Order;
import java.util.List;

/**
 *
 * @author andreas
 */
public interface IGetData
{
    List<Department> getAllDepartments();
    
    List<Order> getAllOrders();
    
    
            
}
