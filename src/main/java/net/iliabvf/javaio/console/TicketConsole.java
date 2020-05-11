package net.iliabvf.javaio.console;

import net.iliabvf.javaio.AppRunner;
import net.iliabvf.javaio.controller.RouteController;
import net.iliabvf.javaio.controller.TicketController;
import net.iliabvf.javaio.exceptions.CreationException;
import net.iliabvf.javaio.exceptions.DeleteException;
import net.iliabvf.javaio.exceptions.ReadingException;
import net.iliabvf.javaio.model.Route;
import net.iliabvf.javaio.model.Ticket;
import net.iliabvf.javaio.model.TicketType;

public class TicketConsole {
    private final static String INPUT_ROUTE_STRING = "Please input route name (or Q to quit): ";
    private final static String ROUTE_NOT_FOUND_STRING =  "Route not found!";
    private final static String INPUT_DATE_STRING = "Please input date (mm/dd/yy or Q to quit): ";
    private final static String INPUT_TICKET_TYPE_STRING = "Please input ticket type (0 - economy, 1 - business or Q to quit): ";
    private final static String TICKET_TYPE_NOT_FOUND_STRING = "Ticket type not found!";
    private final static String TICKET_NOT_FOUND_STRING = "Ticket not found!";
    private final static String TICKET_FOUND_STRING = "Found ticket: ";
    private final static String TICKET_WRITING_ERROR_STRING = "Error on writing ticket to file!";
    private final static String TICKET_REMOVE_ERROR_STRING = "Error on ticket removal!";
    private final static String TICKET_RETURNED_STRING = "Ticket successfully returned ";

    public void searchTicket(){
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
                return;
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

        // Input date
        String searchDate = "";
        while (true) {
            searchDate = AppRunner.userInput(INPUT_DATE_STRING);
            if (searchDate.equalsIgnoreCase("q")) {
                break;
            }
            if (searchDate.length() != 8) {
                continue;
            }
            break;
        }
        if (searchDate.equalsIgnoreCase("q")) {
            return;
        }

        // Input type
        String searchType = "";
        TicketType foundType = null;
        while (true) {
            searchType = AppRunner.userInput(INPUT_TICKET_TYPE_STRING);
            if (searchType.equalsIgnoreCase("q")) {
                break;
            }
            if (searchType.length() != 1) {
                continue;
            }
            if (!searchType.equalsIgnoreCase("0") && !searchType.equalsIgnoreCase("1")) {
                System.err.println(TICKET_TYPE_NOT_FOUND_STRING);
                continue;
            }
            break;
        }
        if (searchType.equalsIgnoreCase("q")) {
            return;
        }
        foundType = TicketType.valueOf(Integer.parseInt(searchType));

        TicketController ticketController = new TicketController();

        Ticket foundTicket = null;
        try {
            foundTicket = ticketController.findByDateRouteType(searchDate, foundRoute, foundType);
        } catch (ReadingException e){
            return;
        }
        if (foundTicket == null) {
            System.err.println(TICKET_NOT_FOUND_STRING);
            return;
        }

        System.out.println(TICKET_FOUND_STRING + foundTicket);
    }

    public void buyTicket(){
        RouteController routeController = new RouteController();
        TicketController ticketController = new TicketController();
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
                return;
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

        // Input date
        String searchDate = "";
        while (true) {
            searchDate = AppRunner.userInput(INPUT_DATE_STRING);
            if (searchDate.equalsIgnoreCase("q")) {
                break;
            }
            if (searchDate.length() != 8) {
                continue;
            }
            break;
        }
        if (searchDate.equalsIgnoreCase("q")) {
            return;
        }

        // Input type
        String searchType = "";
        TicketType foundType = null;
        while (true) {
            searchType = AppRunner.userInput(INPUT_TICKET_TYPE_STRING);
            if (searchType.equalsIgnoreCase("q")) {
                break;
            }
            if (searchType.length() != 1) {
                continue;
            }
            if (!searchType.equalsIgnoreCase("0") && !searchType.equalsIgnoreCase("1")) {
                System.err.println(TICKET_TYPE_NOT_FOUND_STRING);
                continue;
            }
            break;
        }
        if (searchType.equalsIgnoreCase("q")) {
            return;
        }
        foundType = TicketType.valueOf(Integer.parseInt(searchType));

        Ticket newTicket = new Ticket(0, foundType, searchDate, foundRoute);
        try {
            ticketController.addModelToFile(newTicket);
        } catch (ReadingException e){
            return;
        } catch (CreationException e){
            return;
        }
    }

    public void returnTicket(){
        RouteController routeController = new RouteController();
        TicketController ticketController = new TicketController();

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
                return;
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

        // Input date
        String searchDate = "";
        while (true) {
            searchDate = AppRunner.userInput(INPUT_DATE_STRING);
            if (searchDate.equalsIgnoreCase("q")) {
                break;
            }
            if (searchDate.length() != 8) {
                continue;
            }
            break;
        }
        if (searchDate.equalsIgnoreCase("q")) {
            return;
        }

        // Input type
        String searchType = "";
        TicketType foundType = null;
        while (true) {
            searchType = AppRunner.userInput(INPUT_TICKET_TYPE_STRING);
            if (searchType.equalsIgnoreCase("q")) {
                break;
            }
            if (searchType.length() != 1) {
                continue;
            }
            if (!searchType.equalsIgnoreCase("0") && !searchType.equalsIgnoreCase("1")) {
                System.err.println(TICKET_TYPE_NOT_FOUND_STRING);
                continue;
            }
            break;
        }
        if (searchType.equalsIgnoreCase("q")) {
            return;
        }
        foundType = TicketType.valueOf(Integer.parseInt(searchType));

        Ticket foundTicket = null;
        try {
            foundTicket = ticketController.findByDateRouteType(searchDate, foundRoute, foundType);
        } catch (ReadingException e){
            return;
        }
        if (foundTicket == null) {
            System.err.println(TICKET_NOT_FOUND_STRING);
            return;
        }

        try {
            ticketController.removeModelFromFile(foundTicket);
        } catch (DeleteException e){
            System.err.println(TICKET_REMOVE_ERROR_STRING);
            return;
        }

        System.out.println(TICKET_RETURNED_STRING + foundTicket);
    }

}
