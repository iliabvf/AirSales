package net.iliabvf.javaio.controller;

import net.iliabvf.javaio.exceptions.CreationException;
import net.iliabvf.javaio.exceptions.DeleteException;
import net.iliabvf.javaio.exceptions.ReadingException;
import net.iliabvf.javaio.model.Route;
import net.iliabvf.javaio.repository.RouteRepository;
import net.iliabvf.javaio.repository.io.JavaIORouteRepositoryImpl;

public class RouteController extends BaseController {
    RouteRepository routeRepository = new JavaIORouteRepositoryImpl();

    public Route addModelToFile(Route route) throws CreationException, ReadingException {
        return routeRepository.addModelToFile(route);
    }

    public Route findByName(String name) throws ReadingException {
        return routeRepository.findByName(name);
    }

    public Boolean removeModelFromFile(Route model) throws DeleteException {
        return routeRepository.removeModelFromFile(model);
    }

    public Route findById(Integer id) throws ReadingException {
        return routeRepository.findById(id);
    }

}
