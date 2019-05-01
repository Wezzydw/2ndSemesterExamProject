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
public class Costumer implements ICostumer{

    private String name;

    public Costumer(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name.toString();
    }

    @Override
    public String toString() {
        return "Costumer{" + "name=" + name.toString() + '}';
    }
}
