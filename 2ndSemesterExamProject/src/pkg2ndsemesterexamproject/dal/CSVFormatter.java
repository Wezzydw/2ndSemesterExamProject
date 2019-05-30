package pkg2ndsemesterexamproject.dal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import pkg2ndsemesterexamproject.be.Customer;
import pkg2ndsemesterexamproject.be.Delivery;
import pkg2ndsemesterexamproject.be.Department;
import pkg2ndsemesterexamproject.be.DepartmentTask;
import pkg2ndsemesterexamproject.be.ICustomer;
import pkg2ndsemesterexamproject.be.IDelivery;
import pkg2ndsemesterexamproject.be.IDepartment;
import pkg2ndsemesterexamproject.be.IDepartmentTask;
import pkg2ndsemesterexamproject.be.IOrder;
import pkg2ndsemesterexamproject.be.IProductionOrder;
import pkg2ndsemesterexamproject.be.IWorker;
import pkg2ndsemesterexamproject.be.Order;
import pkg2ndsemesterexamproject.be.ProductionOrder;
import pkg2ndsemesterexamproject.be.Worker;

/**
 *
 * @author Wezzy Laptop
 */
public class CSVFormatter implements IFormatter {

    /**
     * Denne metode sørger for at trække alle relevante informationer ud af en
     * CSV-fil, som er nødvendig for at lave en ProductionOrder.
     *
     * @param file CSV-filen der skal læses igennem, og hentes info ud af.
     * @return En liste af ProductionOrders.
     * @throws FileNotFoundException
     * @throws IOException
     */
    @Override
    public List<IProductionOrder> extractProductionOrders(File file) throws FileNotFoundException, IOException {
        List<IProductionOrder> productionOrders = new ArrayList();
        boolean isFirstRun = true;
        for (String string : fileToStringArray(file)) {
            int startIndex = 0;
            int endIndex = 0;
            if (string.contains("ProductionOrder:")) {
                startIndex = string.indexOf("Customer:");
                startIndex = string.indexOf(',', startIndex);
                endIndex = string.indexOf(",", startIndex + 1);
                String customerName = string.substring(startIndex + 2, endIndex - 1);

                startIndex = string.indexOf(",", endIndex + 1);
                endIndex = string.indexOf(",", startIndex + 1);
                String deliveryDate = string.substring(startIndex + 2, endIndex - 1);

                startIndex = string.indexOf("Department:");
                startIndex = string.indexOf(",", startIndex + 1);
                endIndex = string.indexOf(",", startIndex + 1);
                String departmentName = string.substring(startIndex + 2, endIndex - 1);

                startIndex = endIndex;
                endIndex = string.indexOf(",", startIndex + 1);
                String endDate = string.substring(startIndex + 2, endIndex - 1);

                startIndex = endIndex;
                endIndex = string.indexOf(",", startIndex + 1);
                String done = string.substring(startIndex + 2, endIndex - 1);
                boolean isDone = false;
                if (!done.equals("False")) {
                    isDone = true;
                }

                startIndex = endIndex;
                endIndex = string.indexOf(",", startIndex + 1);
                String startDate = string.substring(startIndex + 2, endIndex - 1);

                startIndex = string.indexOf(",", endIndex + 1);
                endIndex = string.length() - 1;
                String orderNumber = string.substring(startIndex + 2, endIndex);

                String[] array = deliveryDate.split("/");
                int dateMonth = Integer.parseInt(array[0]);
                int dateDay = Integer.parseInt(array[1]);
                int dateYear = Integer.parseInt(array[2].substring(0, 4));

                IOrder order = new Order(orderNumber);
                IDelivery delivery = new Delivery(LocalDate.of(dateYear, dateMonth, dateDay));
                ICustomer customer = new Customer(customerName);
                IDepartment department = new Department(departmentName);

                array = startDate.split("/");
                dateMonth = Integer.parseInt(array[0]);
                dateDay = Integer.parseInt(array[1]);
                dateYear = Integer.parseInt(array[2].substring(0, 4));

                List<IDepartmentTask> tasks = new ArrayList();
                LocalDate ldtStartDate = LocalDate.of(dateYear, dateMonth, dateDay);

                array = endDate.split("/");
                dateMonth = Integer.parseInt(array[0]);
                dateDay = Integer.parseInt(array[1]);
                dateYear = Integer.parseInt(array[2].substring(0, 4));

                LocalDate ldtEndDate = LocalDate.of(dateYear, dateMonth, dateDay);
                tasks.add(new DepartmentTask(department, isDone, ldtStartDate, ldtEndDate));
                IProductionOrder IPO = new ProductionOrder(order, delivery, customer, tasks);
                productionOrders.add(IPO);
            } else if (!isFirstRun) {
                startIndex = string.indexOf("Department:");
                startIndex = string.indexOf(",", startIndex + 1);
                endIndex = string.indexOf(",", startIndex + 1);
                String departmentName = string.substring(startIndex + 2, endIndex - 1);

                startIndex = endIndex;
                endIndex = string.indexOf(",", startIndex + 1);
                String endDate = string.substring(startIndex + 2, endIndex - 1);

                startIndex = endIndex;
                endIndex = string.indexOf(",", startIndex + 1);
                String done = string.substring(startIndex + 2, endIndex - 1);
                boolean isDone = false;
                if (!done.equals("False")) {
                    isDone = true;
                }

                startIndex = endIndex;
                endIndex = string.indexOf(",", startIndex + 1);
                String startDate = string.substring(startIndex + 2, endIndex - 1);

                IDepartment department = new Department(departmentName);

                String[] array = startDate.split("/");
                int dateMonth = Integer.parseInt(array[0]);
                int dateDay = Integer.parseInt(array[1]);
                int dateYear = Integer.parseInt(array[2].substring(0, 4));

                LocalDate ldtStartDate = LocalDate.of(dateYear, dateMonth, dateDay);

                array = endDate.split("/");
                dateMonth = Integer.parseInt(array[0]);
                dateDay = Integer.parseInt(array[1]);
                dateYear = Integer.parseInt(array[2].substring(0, 4));

                LocalDate ldtEndDate = LocalDate.of(dateYear, dateMonth, dateDay);
                IDepartmentTask dTask = new DepartmentTask(department, isDone, ldtStartDate, ldtEndDate);
                productionOrders.get(productionOrders.size() - 1).addDepartmentTask(dTask);
            } else {
                isFirstRun = false;
            }
        }
        return productionOrders;
    }

    /**
     * Denne metode læser CSV-filen og trækker nødvendig info der skal udbruges
     * til at lave en Worker.
     *
     * @param file CSV-filen der trækkes info ud fra.
     * @return En liste af Workers læst ud fra filen.
     * @throws FileNotFoundException
     * @throws IOException
     */
    @Override
    public List<IWorker> extractWorkers(File file) throws FileNotFoundException, IOException {
        List<IWorker> workers = new ArrayList();
        boolean isAfterFirstRun = false;
        for (String string : fileToStringArray(file)) {
            int startIndex = 0;
            int endIndex = 0;
            if (string.startsWith("Worker:", 1)) {
                startIndex = string.indexOf(",");
                endIndex = string.indexOf(",", startIndex + 1);
                String initials = string.substring(startIndex + 2, endIndex - 1);

                startIndex = endIndex;
                endIndex = string.indexOf(",", startIndex + 1);
                String name = string.substring(startIndex + 2, endIndex - 1);

                startIndex = endIndex;
                endIndex = string.indexOf(",", startIndex + 1);
                String SN = string.substring(startIndex + 2, endIndex - 1);
                int sn = Integer.parseInt(SN);
                workers.add(new Worker(name, initials, sn));
            } else {
                if (isAfterFirstRun) {
                    break;
                }
                isAfterFirstRun = true;
            }
        }
        return workers;
    }

    /**
     * Denne metode sørger for at lave alt tekst i CSV-filen om til en liste af
     * Strings som behandles af extractProductionOrders og extractWorkers.
     *
     * @param file CSV-filen der omdannes til en liste af Strings.
     * @return En liste af Strings.
     * @throws FileNotFoundException
     * @throws IOException
     */
    @Override
    public List<String> fileToStringArray(File file) throws FileNotFoundException, IOException {
        FileReader filereader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(filereader);
        List<String> data = new ArrayList();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            data.add(line);
        }
        return data;
    }
}
