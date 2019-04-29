/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.gui;

import pkg2ndsemesterexamproject.be.Department;
import java.util.List;
import javafx.scene.control.MenuButton;
import pkg2ndsemesterexamproject.be.Order;
import pkg2ndsemesterexamproject.bll.IBLL;
import pkg2ndsemesterexamproject.bll.PassThrough;

/**
 *
 * @author andreas
 */
public class Model
{
    private IBLL ptl;

    public Model()
    {
        ptl = new PassThrough();
    }
    
    
    
    public List<Department> getAllDepartments(){
        return null;
    }

    public void setMenuItems(MenuButton MenuButton, List<Department> allDepartments)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void orderIsDone(Order order)
    {
        ptl.sendOrderIsDone();
    }
}
