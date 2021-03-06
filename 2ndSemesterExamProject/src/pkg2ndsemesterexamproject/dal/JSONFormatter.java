package pkg2ndsemesterexamproject.dal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import pkg2ndsemesterexamproject.be.Customer;
import pkg2ndsemesterexamproject.be.Delivery;
import pkg2ndsemesterexamproject.be.Department;
import pkg2ndsemesterexamproject.be.DepartmentTask;
import pkg2ndsemesterexamproject.be.IDelivery;
import pkg2ndsemesterexamproject.be.IDepartment;
import pkg2ndsemesterexamproject.be.IDepartmentTask;
import pkg2ndsemesterexamproject.be.IOrder;
import pkg2ndsemesterexamproject.be.IProductionOrder;
import pkg2ndsemesterexamproject.be.Order;
import pkg2ndsemesterexamproject.be.ProductionOrder;
import pkg2ndsemesterexamproject.be.Worker;
import pkg2ndsemesterexamproject.be.ICustomer;
import pkg2ndsemesterexamproject.be.IWorker;

public class JSONFormatter {

    private List<IDepartment> departments;
    private List<IDepartmentTask> departmenttasks;

    public JSONFormatter() {
        departments = new ArrayList();
        departmenttasks = new ArrayList();
    }

    /**
     * Trækker alle workers ud af JSON string fra loadData, og returnerer en
     * liste over disse
     *
     * @return En liste af alle Workers.
     * @throws FileNotFoundException
     * @throws IOException
     */
    public List<IWorker> extractWorkersFromJSON(File file) throws FileNotFoundException, IOException {
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        String data = "";
        while ((line = br.readLine()) != null) {
            data += line;
        }
        String[] array = data.split("\\[");
        String workerString = array[1];
        String[] workersString = workerString.split("\\{");
        List<IWorker> workers = new ArrayList();
        for (String string : workersString) {
            if (!string.isEmpty()) {
                int index = 0;
                index = string.indexOf(":");
                index = string.indexOf(":", index + 1);
                index = string.indexOf(":", index + 1);
                int indexStart = index + 2;
                int indexEnd = string.indexOf('"', indexStart + 1);

                String initials = string.substring(indexStart, indexEnd);

                index = string.indexOf(":", indexEnd);
                indexStart = index + 2;
                indexEnd = string.indexOf('"', indexStart);

                String name = string.substring(indexStart, indexEnd);

                index = string.indexOf(":", indexEnd);
                indexStart = index + 1;
                indexEnd = string.indexOf('}', indexStart);

                String saleryNumber = string.substring(indexStart, indexEnd);
                int s = Integer.parseInt(saleryNumber);
                workers.add(new Worker(name, initials, s));
            }
        }
        return workers;
    }

    /**
     * Tager string fra loadData() og trækker alle productionOrders ud af denne
     * Og samler alle i en liste med alt dets indhold.
     *
     * @return En liste af alle ProductionOrders.
     * @throws IOException
     */
    public List<IProductionOrder> extractProductionOrdersFromJSON(File file) throws IOException {
        String[] array = loadData(file).split("ProductionOrder:");
        List<ICustomer> customers = new ArrayList();
        List<IDelivery> deliveries = new ArrayList();
        List<IDepartmentTask> departmentTasks = new ArrayList();
        List<IOrder> orders = new ArrayList();
        List<IProductionOrder> productionOrders = new ArrayList();

        for (int i = 1; i < array.length; i++) {
            int start;
            int end;
            start = array[i].indexOf("Name") + 7;
            end = array[i].indexOf('"', start);
            String name = array[i].substring(start, end);
            ICustomer customer = new Customer(name);
            customers.add(customer);

            start = array[i].indexOf("DeliveryTime") + 22;
            end = array[i].indexOf("+", start);
            long timeInMilis = Long.parseLong(array[i].substring(start, end));
            LocalDateTime timeAt0 = LocalDateTime.of(1970, 1, 1, 0, 0);
            LocalDateTime deliveryDate = timeAt0.plus(timeInMilis, ChronoUnit.MILLIS);
            IDelivery delivery = new Delivery(deliveryDate.toLocalDate());
            deliveries.add(delivery);

            String[] departmentStringArray = array[i].split("Department:#ProductionMonitor");
            for (int j = 1; j < departmentStringArray.length; j++) {
                start = departmentStringArray[j].indexOf("Name") + 7;
                end = departmentStringArray[j].indexOf('"', start);
                String departmentName = departmentStringArray[j].substring(start, end);
                IDepartment department = new Department(departmentName);
                this.departments.add(department);

                start = departmentStringArray[j].indexOf("EndDate") + 17;
                end = departmentStringArray[j].indexOf('+', start);
                timeInMilis = Long.parseLong(departmentStringArray[j].substring(start, end));
                timeAt0 = LocalDateTime.of(1970, 1, 1, 0, 0);
                LocalDateTime endDate = timeAt0.plus(timeInMilis, ChronoUnit.MILLIS);

                start = departmentStringArray[j].indexOf("FinishedOrder") + 15;
                end = departmentStringArray[j].indexOf('"', start);
                String isTrue = departmentStringArray[j].substring(start, end);
                boolean isOrderFinished = false;
                if (isTrue.equals("true")) {
                    isOrderFinished = true;
                }

                start = departmentStringArray[j].indexOf("StartDate") + 19;
                end = departmentStringArray[j].indexOf('+', start);
                timeInMilis = Long.parseLong(departmentStringArray[j].substring(start, end));
                timeAt0 = LocalDateTime.of(1970, 1, 1, 0, 0);
                LocalDateTime startDate = timeAt0.plus(timeInMilis, ChronoUnit.MILLIS);
                IDepartmentTask departmentTask = new DepartmentTask(department, isOrderFinished, startDate.toLocalDate(), endDate.toLocalDate());
                this.departmenttasks.add(departmentTask);
                departmentTasks.add(departmentTask);
            }

            start = array[i].indexOf("OrderNumber") + 14;
            end = array[i].indexOf('}', start) - 1;
            String orderNumber = array[i].substring(start, end);
            IOrder order = new Order(orderNumber);
            orders.add(order);

            IProductionOrder productionOrder = new ProductionOrder(order, delivery, customer, departmentTasks);
            productionOrders.add(productionOrder);
            departmentTasks = new ArrayList();
        }
        return productionOrders;
    }

    /**
     * Læser linjerne fra JSON-filen og returnerer dem som en lang string
     *
     * @param file JSON-filen der skal læses fra.
     * @return Linjerne fra JSON-filen som én String.
     * @throws FileNotFoundException
     * @throws IOException
     */
    public String loadData(File file) throws FileNotFoundException, IOException {

        FileReader filereader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(filereader);
        String data = "";
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            data += line;
        }
        return data;
    }

    /**
     * @return Liste af Departments
     */
    public List<IDepartment> getDepartments() {
        return departments;
    }

    /**
     *
     * @return List<IDepartment>
     */
    public List<IDepartmentTask> getDepartmentTasks() {
        return departmenttasks;
    }

}
