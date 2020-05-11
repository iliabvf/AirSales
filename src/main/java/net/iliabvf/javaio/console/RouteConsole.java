package net.iliabvf.javaio.console;

import net.iliabvf.javaio.AppRunner;
import net.iliabvf.javaio.controller.RouteController;
import net.iliabvf.javaio.exceptions.CreationException;
import net.iliabvf.javaio.exceptions.DeleteException;
import net.iliabvf.javaio.exceptions.ReadingException;
import net.iliabvf.javaio.model.Route;

public class RouteConsole {

    private final static String INPUT_ROUTE_STRING = "Please input route name (or Q to quit): ";
    private final static String ROUTE_ALLREADY_EXISTS_STRING = "Route allready exists!";
    private final static String ERR_CREATING_ROUTE_STRING = "Route allready exists!";
    private final static String ERR_READING_ROUTE_STRING = "Route allready exists!";
    private final static String ROUTE_NOT_FOUND_STRING =  "Route not found!";
    private final static String ROUTE_ERROR_ON_REMOVE_STRING =  "Error on remove route!";

    public void addRoute(){
        RouteController routeController = new RouteController();

        // Input route
        String searchRoute = "";
        Route foundRoute = null;
        while (true){
            searchRoute = AppRunner.userInput(INPUT_ROUTE_STRING);
            if (searchRoute.equalsIgnoreCase("q")){
                break;
            }
            if (searchRoute.length() == 0 || searchRoute == null){
                continue;
            }
            try {
                foundRoute = routeController.findByName(searchRoute);
            } catch (ReadingException e){
                System.err.println(ERR_READING_ROUTE_STRING);
                continue;
            }
            if (foundRoute != null){
                System.err.println(ROUTE_ALLREADY_EXISTS_STRING);
                continue;
            }
            break;
        }
        if (searchRoute.equalsIgnoreCase("q")){
            return;
        }

        if (foundRoute == null) {
            try {
                Route route = routeController.addModelToFile(new Route(0, searchRoute));
            } catch (CreationException e){
                System.err.println(ERR_CREATING_ROUTE_STRING);
            } catch (ReadingException e){
                System.err.println(ERR_READING_ROUTE_STRING);
            }
            return;
        }
    }

    public void removeRoute(){
        RouteController routeController = new RouteController();
        // Input route
        String searchRoute = "";
        Route foundRoute = null;
        while (true) {
            searchRoute = AppRunner.userInput(INPUT_ROUTE_STRING);
            if (searchRoute.equalsIgnoreCase("q")) {
                break;
            }
            if (searchRoute.length() == 0 || searchRoute == null) {
                continue;
            }
            try {
                foundRoute = routeController.findByName(searchRoute);
            } catch (ReadingException e){
                System.err.println(ERR_READING_ROUTE_STRING);
            }
            if (foundRoute == null) {
                System.err.println(ROUTE_NOT_FOUND_STRING);
                continue;
            }
            break;
        }
        if (searchRoute.equalsIgnoreCase("q")) {
            return;
        }

        if (foundRoute != null) {
            try {
                routeController.removeModelFromFile(foundRoute);
            } catch (DeleteException e){
                System.err.println(ROUTE_ERROR_ON_REMOVE_STRING);
                return;
            }
        }
    }

//    public void deleteByID(Long id) throws DeleteException, ReadingException {
//        routeRepository.deleteByID(id);
//    }

}
