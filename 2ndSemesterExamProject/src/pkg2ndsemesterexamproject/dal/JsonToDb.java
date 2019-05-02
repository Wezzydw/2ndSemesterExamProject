package pkg2ndsemesterexamproject.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import pkg2ndsemesterexamproject.be.ICostumer;
import pkg2ndsemesterexamproject.be.IDelivery;
import pkg2ndsemesterexamproject.be.IDepartment;
import pkg2ndsemesterexamproject.be.IDepartmentTask;
import pkg2ndsemesterexamproject.be.IProductionOrder;

/**
 *
 * @author mpoul
 */
public class JsonToDb {
    private DatabaseConnection conProvider;
    
    private void fuckingEtEllerAndet(List<IProductionOrder> po){
        
        for (IProductionOrder iProductionOrder : po) {
            
        }
    }
    
    private void writeCustomerToDB(ICostumer costumer){
        try (Connection con = conProvider.getConnection()){
            String query = "INSERT INTO Costumer (cName) VALUES(?);";
            PreparedStatement prst = con.prepareStatement(query);
            prst.setString(1, costumer.getName());
        } catch (SQLException ex) {
        }
    
}
    
    private void writeDeliveryToDB(IDelivery deliverydate){
    try (Connection con = conProvider.getConnection()){
            String query = "INSERT INTO Delivery (deliveryDate) VALUES(?);";
            PreparedStatement prst = con.prepareStatement(query);
            prst.setString(1, deliverydate.getDeliveryTime().toString());
        } catch (SQLException ex) {
        }
}
    
    private void writeDepartmentToDB(IDepartment department){
    try (Connection con = conProvider.getConnection()){
            String query = "INSERT INTO Department (dName) VALUES(?);";
            PreparedStatement prst = con.prepareStatement(query);
            prst.setString(1, department.getName());
        } catch (SQLException ex) {
        }
}
    
    private void writeDepartmentTaskToDB(IDepartmentTask departmentTask){
    try (Connection con = conProvider.getConnection()){
            String query = "INSERT INTO DepartmentTask(workers, isFinished, startDate, endDate) VALUES(?,?,?,?);";
            PreparedStatement prst = con.prepareStatement(query);
            prst.setString(1, departmentTask.getActiveWorkers().toString());
            prst.setBoolean(2, departmentTask.getFinishedOrder());
            prst.setString(3, departmentTask.getStartDate().toString());
            prst.setString(4, departmentTask.getEndDate().toString());
        } catch (SQLException ex) {
        }
}
    
    private void writeOrderToDB(){
    
}
    
    private void writeProductionOrderToDB(){
    
}
    
    private void writeWorkerToDB(){
    
}
    
}
