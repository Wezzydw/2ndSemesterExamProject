package pkg2ndsemesterexamproject.dal;

import java.sql.SQLException;
import java.util.List;
import pkg2ndsemesterexamproject.be.IDepartment;
import pkg2ndsemesterexamproject.be.IDepartmentTask;
import pkg2ndsemesterexamproject.be.IProductionOrder;
import pkg2ndsemesterexamproject.be.IWorker;

public interface IGetData {

    List<IDepartment> getAllDepartments() throws SQLException;

    void sendOrderIsDone(IDepartmentTask dt, IProductionOrder po) throws SQLException;

    List<IWorker> getAllWorkers() throws SQLException;

    List<IProductionOrder> getAllProductionOrders() throws SQLException;
}
