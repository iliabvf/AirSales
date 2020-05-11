package net.iliabvf.javaio.controller;

import net.iliabvf.javaio.exceptions.CreationException;
import net.iliabvf.javaio.exceptions.DeleteException;
import net.iliabvf.javaio.exceptions.ReadingException;
import net.iliabvf.javaio.model.*;
import net.iliabvf.javaio.repository.TicketRepository;
import net.iliabvf.javaio.repository.io.JavaIOTicketRepositoryImpl;

public class TicketController extends BaseController  {
    TicketRepository ticketRepository = new JavaIOTicketRepositoryImpl();

    public Ticket findByDateRouteType(String date, Route route, TicketType type) throws ReadingException {
        return ticketRepository.findByDateRouteType(date, route, type);
    }

    public Ticket addModelToFile(Ticket ticket) throws CreationException, ReadingException {
        return ticketRepository.addModelToFile(ticket);
    }

    public Boolean removeModelFromFile(Ticket ticket) throws DeleteException {
        return ticketRepository.removeModelFromFile(ticket);
    }

}
