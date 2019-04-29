/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.be;

/**
 *
 * @author Wezzy Laptop
 */
public class Worker implements IWorker {

    private String name;
    private String initials;
    private int salaryNumber;

    @Override
    public String toString() {
        return "Worker{" + "initials=" + initials + " name=" + name + ", salaryNumber=" + salaryNumber + '}';
    }

    public Worker(String name, String initials, int salaryNumber) {
        this.name = name;
        this.initials = initials;
        this.salaryNumber = salaryNumber;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getInitials() {
        return initials;
    }

    @Override
    public int getSalaryNumber() {
        return salaryNumber;
    }

}
