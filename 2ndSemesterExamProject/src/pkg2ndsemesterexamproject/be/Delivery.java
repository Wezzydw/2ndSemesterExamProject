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

    private LocalDateTime localDateTime;

    public Delivery(LocalDateTime localDate) {
        this.localDateTime = localDate;
    }

    @Override
    public LocalDateTime getDeliveryTime() {
        return localDateTime;

    }
}
