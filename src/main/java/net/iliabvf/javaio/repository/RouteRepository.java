package net.iliabvf.javaio.repository;

import net.iliabvf.javaio.exceptions.CreationException;
import net.iliabvf.javaio.exceptions.DeleteException;
import net.iliabvf.javaio.exceptions.ReadingException;
import net.iliabvf.javaio.model.Route;

public abstract class RouteRepository implements GenericRepository {

    abstract public Route addModelToFile(Route model) throws CreationException, ReadingException;

    abstract public Route findByName(String searchRoute) throws ReadingException;

    abstract public Boolean removeModelFromFile(Route model) throws DeleteException;

    abstract public Route findById(Integer id) throws ReadingException;

}


