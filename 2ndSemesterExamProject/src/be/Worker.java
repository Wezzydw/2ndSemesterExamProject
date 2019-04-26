/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be;

/**
 *
 * @author Wezzy Laptop
 */
public class Worker {

    private String name;
    private String initials;
    private String salaryNumber;

    @Override
    public String toString() {
        return "Worker{" + "initials=" + initials + " name=" + name + ", salaryNumber=" + salaryNumber + '}';
    }

    public Worker(String name, String initials, String salaryNumber) {
        this.name = name;
        this.initials = initials;
        this.salaryNumber = salaryNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getSalaryNumber() {
        return salaryNumber;
    }

    public void setSalaryNumber(String salaryNumber) {
        this.salaryNumber = salaryNumber;
    }

}
