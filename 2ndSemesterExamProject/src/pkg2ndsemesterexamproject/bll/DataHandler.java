
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.bll;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import pkg2ndsemesterexamproject.be.IDepartmentTask;
import pkg2ndsemesterexamproject.be.IProductionOrder;
import pkg2ndsemesterexamproject.dal.ReadConfig;

/**
 *
 * @author Wezzy Laptop
 */
public class DataHandler {

    private final PassThrough passThrough;
    private final Search searcher;
    private int oldHash = 0;
    private boolean isNewData = true;
    private List<IProductionOrder> data;
    private String oldSearchString = null;
    private ISortStrategy oldSortStrategy = null;
    private List<IProductionOrder> oldData;

    public DataHandler() throws IOException, SQLException {
        passThrough = new PassThrough();
        searcher = new Search();
        data = new ArrayList();
        oldData = new ArrayList();
        runDataCheck();
    }

    /**
     * Hvis data listens hashværdi ikke er det samme som oldHash, sættes
     * isNewData til true, hvis den er det samme som oldHash, sættes den false.
     *
     * @throws SQLException
     */
    public void runDataCheck() throws SQLException {
        data = passThrough.getAllProductionOrders();

        if (data.hashCode() != oldHash) {
            isNewData = true;
        } else {
            isNewData = false;
        }
        oldHash = data.hashCode();
        
        
    }

    /**
     * Denne behandles af runDataCheck
     * @return isNewData true/false efter hvad runDataCheck() sætter den til.
     */
    public boolean isThereNewData() {
        return isNewData;
    }

    /**
     * Denne metode sørger få alle relavante ProductionOrders frem. Dataen fra
     * databasen bliver kørt igennem et tjek for om departmentname matcher, og for
     * at dpt ikke er færdig, og om startdato er inden for kriterierne for at blive
     * vist i viewet. 
     * @param departmentName Afdelingsnavnet forespørgsel bliver foretaget i.
     * @param searchString Er inputtet fra søgefeltet.
     * @param strategy Er den valgte strategy fra comboboxen.
     * @return En liste af ProductionOrder
     * @throws SQLException
     * @throws IOException
     */
    public List<IProductionOrder> getAllRelevantProductionOrders(String departmentName, String searchString, ISortStrategy strategy) throws SQLException, IOException {
        if (oldSortStrategy != null && oldSearchString != null) {
            if (oldSearchString.equals(searchString) && oldSortStrategy.equals(strategy) && !isNewData) {
                return oldData;
            }
        } else {
            oldSortStrategy = strategy;
            oldSearchString = searchString;
        }
        oldSearchString = searchString;
        oldSortStrategy = strategy;
        LocalDate today = LocalDate.now();
        List<IProductionOrder> returnList = new ArrayList();
        if (data.get(0) != null) {
            loop:
            for (IProductionOrder iProductionOrder : data) {
                for (IDepartmentTask departmentTask : iProductionOrder.getDepartmentTasks()) {
                    if (departmentName.equals(departmentTask.getDepartment().getName())
                            && !departmentTask.getFinishedOrder()
                            && (departmentTask.getStartDate().minusDays(ReadConfig.getOffsetFromDepartmentName(departmentName)).isBefore(today)
                            || departmentTask.getStartDate().minusDays(ReadConfig.getOffsetFromDepartmentName(departmentName)).isEqual(today)
                            || departmentTask.getStartDate().isEqual(today))) {

                        returnList.add(iProductionOrder);
                        continue loop;
                    }
                }
            }
        }
        return oldData = strategy.sort(searcher.searchAllProductionOrders(searchString, returnList, departmentName.toLowerCase()), departmentName);

    }
    
    /**
     * Itererer gennem listen af DepartmentTasks som en ProductionOrder indeholder
     * og returnerer en DepartmentTask for pågældende Department.
     * @param po Et objekt ProductionOrder.
     * @param departmentName Afdelingsnavnet forespørgslen bliver foretaget i.
     * @return DepartmentTask()
     */
    public IDepartmentTask getTaskForDepartment(IProductionOrder po, String departmentName) {
        for (IDepartmentTask departmentTask : po.getDepartmentTasks()) {
            if (departmentTask.getDepartment().getName().equals(departmentName)) {
                return departmentTask;
            }
        }
        return null;
    }

}
