/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.be;

import java.time.LocalDate;

/**
 *
 * @author andreas
 */
public class Department implements IDepartment {

    private String name;

    public Department(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name.toString();
    }

    @Override
    public String getName() {
        return name;
    }

}
