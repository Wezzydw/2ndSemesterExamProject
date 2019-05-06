/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.bll;

import java.util.List;
import pkg2ndsemesterexamproject.be.IDepartmentTask;
import pkg2ndsemesterexamproject.be.IProductionOrder;

/**
 *
 * @author Wezzy Laptop
 */
public interface IDataHandler {

    public List<IProductionOrder> searchAllProductionOrders(String searchString);

}
