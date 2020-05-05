package net.iliabvf.javaio;

import net.iliabvf.javaio.controller.RouteController;
import net.iliabvf.javaio.controller.TicketController;
import net.iliabvf.javaio.controller.UserController;
import net.iliabvf.javaio.model.*;

import java.util.*;

public class AppRunner {

    private final static String WELCOME_STRING =  "Welcome to AIRSALES MVC Java app v.0.1 (by Budeanu Vasile).";
    private final static String WELCOME_STRING2 =  "Please choose option:";
    private final static String MENU_STRING1 =  "1.Search ticket";
    private final static String MENU_STRING2 =  "2.Buy ticket";
    private final static String MENU_STRING3 =  "3.Return ticket";
    private final static String MENU_STRING4 =  "4.Add route";
    private final static String MENU_STRING5 =  "5.Remove route";
    private final static String MENU_STRING6 =  "6.Quit";

    private final static String INPUT_DATE_STRING = "Please input date (mm/dd/yy or Q to quit): ";
    private final static String INPUT_ROUTE_STRING = "Please input route name (or Q to quit): ";
    private final static String INPUT_TICKET_TYPE_STRING = "Please input ticket type (0 - economy, 1 - business or Q to quit): ";
    private final static String ROUTE_ALLREADY_EXISTS_STRING = "Route allready exists!";

    private final static String TICKET_WRITING_ERROR_STRING = "Error on writing ticket to file!";

    private final static String TICKET_RETURNED_STRING = "Ticket successfully returned ";

    private final static String ROUTE_NOT_FOUND_STRING =  "Route not found!";
    private final static String ROUTE_ERROR_ON_REMOVE =  "Error on remove route!";

    private final static String CLOSING_STRING =  "Have a nice day.";
    private final static String ERROR_NUM_DIGITS_STRING =  "Please input 1 digit";
    private final static String ERROR_RANGE_STRING =  "Error: Please enter from 1 to 5.";

    public final static String LOGIN_SUCCESSFUL_TEXT = "Login ok. Welcome.";
    public final static String TICKET_NOT_FOUND_STRING = "Ticket not found!";
    public final static String TICKET_REMOVE_ERROR_STRING = "Error on ticket removal!";
    public final static String TICKET_TYPE_NOT_FOUND_STRING = "Ticket type not found!";
    public final static String TICKET_FOUND_STRING = "Found ticket: ";

    private static Scanner scanner;

    public static Scanner getScanner() {
        return scanner;
    }

    public static String userInput(String message){
        System.out.println();
        System.out.print(message);
        try {
            return scanner.nextLine();
        } catch (Exception e)
        {
            return "";
        }
    }

    private static byte validateInput(String input){
        if (input.toUpperCase().equals("6")){
            System.out.println();
            System.out.println(CLOSING_STRING);
            return 6;
        }

        if (input.length() != 1) {
            System.out.println(ERROR_NUM_DIGITS_STRING);
            return 0;
        }

        if (input.charAt(0) < 49 || input.charAt(0) > 54){
            System.out.println(ERROR_RANGE_STRING);
            return 0;
        }

        return (byte)Character.getNumericValue(input.charAt(0));
    }

    public static void main(String[] args) {

        scanner = new Scanner(System.in);

        byte input;

        String login = "";
        String password = "";

        System.out.println(WELCOME_STRING);

        UserController userController = new UserController();
        ArrayList<BaseModel> userArrayList = userController.readAll();
        userController.checkSetAdminPassword(userArrayList);

        userController.doLogin(userArrayList);
        System.out.println(LOGIN_SUCCESSFUL_TEXT);

        RouteController routeController = new RouteController();

        String searchRoute = "";
        Route foundRoute = null;
        String searchDate = "";
        String searchType = "";
        TicketType foundType;

        TicketController ticketController = new TicketController();

        // Main cycle
        while (true) {
                System.out.println(WELCOME_STRING2);
                System.out.println(MENU_STRING1);
                System.out.println(MENU_STRING2);
                System.out.println(MENU_STRING3);
                System.out.println(MENU_STRING4);
                System.out.println(MENU_STRING5);
                System.out.println(MENU_STRING6);

                input = validateInput(userInput(WELCOME_STRING2));
                if ( input == 0) {
                    continue;
                } else if (input == 6) {
                    return;
                }

                switch(input) {
                    case 1: // Search ticket
                        // Input route
                        searchRoute = "";
                        foundRoute = null;
                        while (true){
                            searchRoute = userInput(INPUT_ROUTE_STRING);
                            if (searchRoute.equalsIgnoreCase("q")){
                                break;
                            }
                            if (searchRoute.length() == 0 || searchRoute == null){
                                continue;
                            }
                            foundRoute = (Route)routeController.findByName(searchRoute);
                            if (foundRoute == null){
                                System.err.println(ROUTE_NOT_FOUND_STRING);
                                continue;
                            }
                            break;
                        }
                        if (searchRoute.equalsIgnoreCase("q")){
                            break;
                        }

                        // Input date
                        searchDate = "";
                        while (true){
                            searchDate = userInput(INPUT_DATE_STRING);
                            if (searchDate.equalsIgnoreCase("q")){
                                break;
                            }
                            if (searchDate.length() != 8){
                                continue;
                            }
                            break;
                        }
                        if (searchDate.equalsIgnoreCase("q")){
                            break;
                        }

                        // Input type
                        searchType = "";
                        foundType = null;
                        while (true){
                            searchType = userInput(INPUT_TICKET_TYPE_STRING);
                            if (searchType.equalsIgnoreCase("q")){
                                break;
                            }
                            if (searchType.length() != 1){
                                continue;
                            }
                            if (!searchType.equalsIgnoreCase("0") && !searchType.equalsIgnoreCase("1")){
                                System.err.println(TICKET_TYPE_NOT_FOUND_STRING);
                                continue;
                            }
                            break;
                        }
                        if (searchType.equalsIgnoreCase("q")){
                            break;
                        }
                        foundType = TicketType.valueOf(Integer.parseInt(searchType));

                        Ticket foundTicket = (Ticket)ticketController.findByDateRouteType(searchDate, foundRoute, foundType);
                        if (foundTicket == null){
                            System.err.println(TICKET_NOT_FOUND_STRING);
                            break;
                        }

                        System.out.println(TICKET_FOUND_STRING + foundTicket);
                        break;

                    case 2: // Buy ticket
                        // Input route
                        searchRoute = "";
                        foundRoute = null;
                        while (true){
                            searchRoute = userInput(INPUT_ROUTE_STRING);
                            if (searchRoute.equalsIgnoreCase("q")){
                                break;
                            }
                            if (searchRoute.length() == 0 || searchRoute == null){
                                continue;
                            }
                            foundRoute = (Route)routeController.findByName(searchRoute);
                            if (foundRoute == null){
                                System.err.println(ROUTE_NOT_FOUND_STRING);
                                continue;
                            }
                            break;
                        }
                        if (searchRoute.equalsIgnoreCase("q")){
                            break;
                        }

                        // Input date
                        searchDate = "";
                        while (true){
                            searchDate = userInput(INPUT_DATE_STRING);
                            if (searchDate.equalsIgnoreCase("q")){
                                break;
                            }
                            if (searchDate.length() != 8){
                                continue;
                            }
                            break;
                        }
                        if (searchDate.equalsIgnoreCase("q")){
                            break;
                        }

                        // Input type
                        searchType = "";
                        foundType = null;
                        while (true){
                            searchType = userInput(INPUT_TICKET_TYPE_STRING);
                            if (searchType.equalsIgnoreCase("q")){
                                break;
                            }
                            if (searchType.length() != 1){
                                continue;
                            }
                            if (!searchType.equalsIgnoreCase("0") && !searchType.equalsIgnoreCase("1")){
                                System.err.println(TICKET_TYPE_NOT_FOUND_STRING);
                                continue;
                            }
                            break;
                        }
                        if (searchType.equalsIgnoreCase("q")){
                            break;
                        }
                        foundType = TicketType.valueOf(Integer.parseInt(searchType));

                        BaseModel newTicket = new Ticket(0,"", foundType, searchDate, foundRoute);
                        if (!ticketController.addModelToFile(newTicket)){
                            System.err.println(TICKET_WRITING_ERROR_STRING);
                            break;
                        }

                        break;

                    case 3: // Return ticket
//
                        // Input route
                        searchRoute = "";
                        foundRoute = null;
                        while (true){
                            searchRoute = userInput(INPUT_ROUTE_STRING);
                            if (searchRoute.equalsIgnoreCase("q")){
                                break;
                            }
                            if (searchRoute.length() == 0 || searchRoute == null){
                                continue;
                            }
                            foundRoute = (Route)routeController.findByName(searchRoute);
                            if (foundRoute == null){
                                System.err.println(ROUTE_NOT_FOUND_STRING);
                                continue;
                            }
                            break;
                        }
                        if (searchRoute.equalsIgnoreCase("q")){
                            break;
                        }

                        // Input date
                        searchDate = "";
                        while (true){
                            searchDate = userInput(INPUT_DATE_STRING);
                            if (searchDate.equalsIgnoreCase("q")){
                                break;
                            }
                            if (searchDate.length() != 8){
                                continue;
                            }
                            break;
                        }
                        if (searchDate.equalsIgnoreCase("q")){
                            break;
                        }

                        // Input type
                        searchType = "";
                        foundType = null;
                        while (true){
                            searchType = userInput(INPUT_TICKET_TYPE_STRING);
                            if (searchType.equalsIgnoreCase("q")){
                                break;
                            }
                            if (searchType.length() != 1){
                                continue;
                            }
                            if (!searchType.equalsIgnoreCase("0") && !searchType.equalsIgnoreCase("1")){
                                System.err.println(TICKET_TYPE_NOT_FOUND_STRING);
                                continue;
                            }
                            break;
                        }
                        if (searchType.equalsIgnoreCase("q")){
                            break;
                        }
                        foundType = TicketType.valueOf(Integer.parseInt(searchType));

                        foundTicket = (Ticket)ticketController.findByDateRouteType(searchDate, foundRoute, foundType);
                        if (foundTicket == null){
                            System.err.println(TICKET_NOT_FOUND_STRING);
                            break;
                        }

                        if (!ticketController.removeModelFromFile(foundTicket)){
                            System.err.println(TICKET_REMOVE_ERROR_STRING);
                            break;
                        }

                        System.out.println(TICKET_RETURNED_STRING + foundTicket);
                        break;

                    case 4: // Add route
                        // Input route
                        searchRoute = "";
                        foundRoute = null;
                        while (true){
                            searchRoute = userInput(INPUT_ROUTE_STRING);
                            if (searchRoute.equalsIgnoreCase("q")){
                                break;
                            }
                            if (searchRoute.length() == 0 || searchRoute == null){
                                continue;
                            }
                            foundRoute = (Route)routeController.findByName(searchRoute);
                            if (foundRoute != null){
                                System.err.println(ROUTE_ALLREADY_EXISTS_STRING);
                                continue;
                            }
                            break;
                        }
                        if (searchRoute.equalsIgnoreCase("q")){
                            break;
                        }

                        if (foundRoute == null){
                            Route route = (Route)routeController.addRouteToFile(searchRoute);
//                            System.err.println(ROUTE_ALLREADY_EXISTS_STRING);
//                            continue;
                        }

                        break;

                    case 5: // Remove route
                        // Input route
                        searchRoute = "";
                        foundRoute = null;
                        while (true){
                            searchRoute = userInput(INPUT_ROUTE_STRING);
                            if (searchRoute.equalsIgnoreCase("q")){
                                break;
                            }
                            if (searchRoute.length() == 0 || searchRoute == null){
                                continue;
                            }
                            foundRoute = (Route)routeController.findByName(searchRoute);
                            if (foundRoute == null){
                                System.err.println(ROUTE_NOT_FOUND_STRING);
                                continue;
                            }
                            break;
                        }
                        if (searchRoute.equalsIgnoreCase("q")){
                            break;
                        }

                        if (foundRoute != null){
                            if (!routeController.removeRouteToFile(foundRoute)) {
                            System.err.println(ROUTE_ERROR_ON_REMOVE);
                            break;
                            }
                        }

                        break;

                    case 6: // Quit
                        return;

                    default:
                        // code block
                }

        }

    }

}
