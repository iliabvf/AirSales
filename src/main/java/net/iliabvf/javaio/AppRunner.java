package net.iliabvf.javaio;

import net.iliabvf.javaio.console.RouteConsole;
import net.iliabvf.javaio.console.TicketConsole;
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

    private final static String CLOSING_STRING =  "Have a nice day.";
    private final static String ERROR_NUM_DIGITS_STRING =  "Please input 1 digit";
    private final static String ERROR_RANGE_STRING =  "Error: Please enter from 1 to 5.";

    public final static String LOGIN_SUCCESSFUL_TEXT = "Login ok. Welcome.";

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

        if (input.length() != 1){
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

        System.out.println(WELCOME_STRING);

        UserController userController = new UserController();
        ArrayList<User> userArrayList = userController.readAll();
        userController.checkSetAdminPassword(userArrayList);

        userController.doLogin(userArrayList);
        System.out.println(LOGIN_SUCCESSFUL_TEXT);

        RouteConsole routeConsole = new RouteConsole();
        TicketConsole ticketConsole = new TicketConsole();

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
            if (input == 0) {
                continue;
            } else if (input == 6) {
                return;
            }

            switch (input) {
                case 1: // Search ticket
                    ticketConsole.searchTicket();
                    break;

                case 2: // Buy ticket
                    ticketConsole.buyTicket();
                    break;

                case 3: // Return ticket
                    ticketConsole.returnTicket();
                    break;

                case 4: // Add route
                    routeConsole.addRoute();
                    break;

                case 5: // Remove route
                    routeConsole.removeRoute();
                    break;

                case 6: // Quit
                    return;

                default:
                    // code block
            }

        }

    }

}
