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
 * @author andreas
 */
public class Order implements IOrder {

    private final String number;

    public Order(String number) {
        this.number = "" + number;
    }

    @Override
    public String getOrderNumber() {
        return number.toString();
    }

    @Override
    public String toString() {
        return number;
    }

    public StringProperty getOrderProperty() {
        return new SimpleStringProperty(getOrderNumber());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.number);
        return hash;
    }

}
