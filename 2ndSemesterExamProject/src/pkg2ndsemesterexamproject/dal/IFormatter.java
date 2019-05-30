package pkg2ndsemesterexamproject.dal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import pkg2ndsemesterexamproject.be.IProductionOrder;
import pkg2ndsemesterexamproject.be.IWorker;

public interface IFormatter {

    public List<IProductionOrder> extractProductionOrders(File file) throws FileNotFoundException, IOException;

    public List<IWorker> extractWorkers(File file) throws FileNotFoundException, IOException;

    public List<String> fileToStringArray(File file) throws FileNotFoundException, IOException;

}
