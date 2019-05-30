package pkg2ndsemesterexamproject.bll;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import pkg2ndsemesterexamproject.be.IDepartment;
import pkg2ndsemesterexamproject.be.IDepartmentTask;
import pkg2ndsemesterexamproject.be.IProductionOrder;
import pkg2ndsemesterexamproject.be.IWorker;

public interface IPassthrough {

    public List<IWorker> getWorkersFromDB() throws SQLException;

    List<IDepartment> getAllDepartments() throws SQLException;

    void sendOrderIsDone(IDepartmentTask dt, IProductionOrder po) throws SQLException;

    List<IWorker> getAllWorkers() throws SQLException;

    List<IProductionOrder> getAllProductionOrders() throws SQLException;

    public void scanFolderForNewFiles() throws IOException, SQLException;
}
