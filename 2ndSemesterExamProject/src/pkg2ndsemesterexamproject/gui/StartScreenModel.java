package pkg2ndsemesterexamproject.gui;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import pkg2ndsemesterexamproject.be.IDepartment;
import pkg2ndsemesterexamproject.bll.PassThrough;

public class StartScreenModel {

    private final PassThrough ptl;

    public StartScreenModel() throws IOException {
        ptl = new PassThrough();
    }

    /**
     * Dene metode retunere en liste af departments
     *
     * @return Liste Departments
     * @throws SQLException
     */
    public List<IDepartment> getAllDepartments() throws SQLException {
        return ptl.getAllDepartments();
    }
}
