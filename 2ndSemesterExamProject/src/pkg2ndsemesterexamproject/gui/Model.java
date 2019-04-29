/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.gui;

import pkg2ndsemesterexamproject.be.Department;
import java.util.List;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
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
    //public Pane createOrderInGUI(int orederNum, String startDate, String endDate){
    public Pane createOrderInGUI(){
        Pane orderPane = new Pane();
        orderPane.setMaxSize(200, 150);
        Circle circle = new Circle(13);
        circle.setFill(Paint.valueOf("Green"));
        Label orderNum = new Label("Ordernummber: " + 12321312);
        Label startDate = new Label("29-04-2019");
        Label endDate = new Label("09-05-2019");
        ProgressBar progress = new ProgressBar();
        ProgressBar expectedProgress = new ProgressBar();
        circle.setLayoutX(180);
        circle.setLayoutY(20);
        
        orderNum.setLayoutX(50);
        orderNum.setLayoutY(15);
        
        startDate.setLayoutX(20);
        startDate.setLayoutY(100);
        
        endDate.setLayoutX(130);
        endDate.setLayoutY(100);
        
        progress.setLayoutX(5);
        progress.setLayoutY(130);
        
        expectedProgress.setLayoutX(5);
        expectedProgress.setLayoutY(130);
        
        orderPane.getChildren().addAll(circle, orderNum, startDate, endDate, progress, expectedProgress);
        
//        orderPane.getChildren().add(orderNum);
//        orderPane.getChildren().add(startDate);
//        orderPane.getChildren().add(endDate);
//        orderPane.getChildren().add(progress);
//        orderPane.getChildren().add(expectedProgress);
        return orderPane;
    }
    
    public void placeOrderInUI(){
    
    }
}
