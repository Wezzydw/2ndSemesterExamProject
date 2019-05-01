/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ndsemesterexamproject.be;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author Wezzy Laptop
 */
public class Delivery implements IDelivery {

    private LocalDateTime localDate;

    public Delivery(LocalDateTime localDate) {
        this.localDate = localDate;
    }

    @Override
    public LocalDateTime getDeliveryTime() {
        return localDate;
    }

}
