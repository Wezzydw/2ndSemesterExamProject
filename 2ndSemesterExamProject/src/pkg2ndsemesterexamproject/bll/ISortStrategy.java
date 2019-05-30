package pkg2ndsemesterexamproject.bll;

import java.util.List;
import pkg2ndsemesterexamproject.be.IProductionOrder;

public interface ISortStrategy {

    public List<IProductionOrder> sort(List<IProductionOrder> list, String departmentName);
}
