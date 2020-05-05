package net.iliabvf.javaio.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum TicketType {

    ECONOMY(0),
    BUSINESS(1);

    private Integer numVal;

    TicketType(Integer numVal) {
        this.numVal = numVal;
    }

    public Integer getNumVal() {
        return numVal;
    }

    public static TicketType valueOf(int numVal) {
        for (TicketType ticketType : TicketType.values()) {
            if (ticketType.numVal == numVal){
                return ticketType;
            }
        }

        return null;
    }

}
