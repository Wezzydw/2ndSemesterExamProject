/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.be;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Wezzy Laptop
 */
public class Delivery implements IDelivery {

    private LocalDate localDate;

    public Delivery(LocalDate localDate) {
        this.localDate = localDate;
    }

    @Override
    public LocalDate getDeliveryTime() {
        return localDate;
    }

    @Override
    public String toString() {
        return localDate.format(DateTimeFormatter.ofPattern("d/MM/YYYY"));
    }
}
