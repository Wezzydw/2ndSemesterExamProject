package pkg2ndsemesterexamproject.gui;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import pkg2ndsemesterexamproject.be.IDepartmentTask;
import pkg2ndsemesterexamproject.be.IProductionOrder;
import pkg2ndsemesterexamproject.be.IWorker;
import pkg2ndsemesterexamproject.bll.PassThrough;

public class OverViewModel {

    private final PassThrough ptl;

    public OverViewModel() throws IOException {
        ptl = new PassThrough();
    }

    /**
     * kalder metoden ptl
     *
     * @return workers fra DB
     * @throws IOException
     * @throws SQLException
     */
    public List<IWorker> updateListViewWorkersAssigned() throws IOException, SQLException {
        return ptl.getWorkersFromDB();
    }

    /**
     * kalder metoden ptl sendorderisdone
     *
     * @param dt DepartmentTask der skal sættes til done.
     * @param po Det objekt af ProductionOrder som indeholder DepartmentTask.
     * @throws SQLException
     */
    public void orderIsDone(IDepartmentTask dt, IProductionOrder po) throws SQLException {
        ptl.sendOrderIsDone(dt, po);
    }
}
