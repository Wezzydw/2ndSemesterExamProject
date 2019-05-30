/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.gui;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import pkg2ndsemesterexamproject.be.IDepartment;
import pkg2ndsemesterexamproject.bll.PassThrough;

/**
 *
 * @author Wezzy
 */
public class StartScreenModel {

    private final PassThrough ptl;

    public StartScreenModel() throws IOException {
        ptl = new PassThrough();
    }

    /**
     * Dene metode retunere en liste af departments
     * @return Liste Departments
     * @throws SQLException
     */
    public List<IDepartment> getAllDepartments() throws SQLException {
        return ptl.getAllDepartments();
    }
}
