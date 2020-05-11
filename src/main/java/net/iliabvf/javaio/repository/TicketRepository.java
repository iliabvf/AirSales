package net.iliabvf.javaio.repository;

import net.iliabvf.javaio.exceptions.CreationException;
import net.iliabvf.javaio.exceptions.DeleteException;
import net.iliabvf.javaio.exceptions.ReadingException;
import net.iliabvf.javaio.model.Route;
import net.iliabvf.javaio.model.Ticket;
import net.iliabvf.javaio.model.TicketType;

public abstract class TicketRepository implements GenericRepository {

    abstract public Ticket addModelToFile(Ticket model) throws CreationException, ReadingException;

    abstract public Ticket findByName(String searchRoute) throws ReadingException;

    abstract public Boolean removeModelFromFile(Ticket model) throws DeleteException;

    abstract public Ticket findById(Integer id) throws ReadingException;

    abstract public Ticket findByDateRouteType(String date, Route route, TicketType type) throws ReadingException;

}


