/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.be;

import java.util.Objects;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Wezzy Laptop
 */
public class Customer implements ICustomer{

    private String name;

    public Customer(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name.toString();
    }

    @Override
    public String toString() {
        return name;
    }
    
        public StringProperty getCustomerProperty() {
        return new SimpleStringProperty(getName());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.name);
        return hash;
    }
}
