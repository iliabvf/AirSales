package net.iliabvf.javaio.model;

import java.util.Date;

public class Ticket extends BaseModel {
    private TicketType ticketType;
    private String date;
    private Route route;

    public Ticket(Integer id, TicketType ticketType, String date, Route route) {
        super(id);
        this.ticketType = ticketType;
        this.date = date;
        this.route = route;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    @Override
    public String toString() {
        return id.toString() + "," + ticketType.getNumVal() + "," + date + "," + route.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ticket ticket = (Ticket)o;
        return id == ticket.getId()
                && ticketType.equals(ticket.getTicketType())
                && date.equals(ticket.getDate())
                && route.equals(ticket.getRoute());

    }
}
