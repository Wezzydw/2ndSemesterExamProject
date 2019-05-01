/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.dal;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import pkg2ndsemesterexamproject.be.Department;
import pkg2ndsemesterexamproject.be.Order;

/**
 *
 * @author andreas
 */
public class OrderDAL
{
    private DatabaseConnection conProvider;

    public OrderDAL() throws IOException
    {
        try {
            conProvider = new DatabaseConnection();
        } catch (IOException ex) {
            throw new IOException("No database connection established " + ex);
        }
    }
    
    
    public void orderIsDone(Order order, Department department) throws SQLException{
        try (Connection con = conProvider.getConnection()) {
//            String a = "UPDATE Attendance SET present = ? WHERE (studentId = ? AND date = ?);";
//            PreparedStatement prst = con.prepareStatement(a);
//            
//            prst.setString(1, attendance.getAbsense());
//            prst.setInt(2, student.getId());
//            prst.setString(3, attendance.getDate());
//            prst.execute();
//            
//            prst.close();
        } catch(SQLException ex){
            throw new SQLException("Can't edit attendance " + ex);
        }
    }
}
