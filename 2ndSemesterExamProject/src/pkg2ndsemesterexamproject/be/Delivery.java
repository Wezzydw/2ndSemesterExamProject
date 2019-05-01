/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.be;

import java.time.LocalDate;

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

}
