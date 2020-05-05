package net.iliabvf.javaio.controller;

import net.iliabvf.javaio.model.BaseModel;
import net.iliabvf.javaio.model.User;

import javax.jws.soap.SOAPBinding;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserController extends BaseController {
    static String STRING_FILE_NAME = "users.csv";
    static String ADMIN_USER_NAME = "admin";
    static String SET_NEW_PASSWORD_TEXT = "Admin user has no password. Please set new password: ";
    static String LOGIN_INPUT_NAME_TEXT = "Please input login name: ";
    static String LOGIN_INPUT_PASSWORD_TEXT = "Please input login password: ";
    static String LOGIN_ERROR_TEXT = "Invalid login or password!";

    private static Scanner scanner = new Scanner(System.in);

    BaseModel findById(Integer id){
        ArrayList<BaseModel> userArrayList = readAll();

        BaseModel foundUser = userArrayList.stream()
                .filter(user -> (id.toString()).equals(user.getName()))
                .findAny()
                .orElse(null);

        return foundUser;
    }

    BaseModel findByName(String name){
        ArrayList<BaseModel> userArrayList = readAll();

        BaseModel foundUser = userArrayList.stream()
                .filter(user -> (name).equalsIgnoreCase(user.getName()))
                .findAny()
                .orElse(null);

        return foundUser;
    }

    public void writeArrayListToFile(ArrayList<String> dataLines) throws IOException {
        File csvOutputFile = new File(STRING_FILE_NAME);
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            dataLines.stream()
                    .forEach(pw::println);
        }
    }

    boolean writeModelToFile(BaseModel model, Boolean emptyFile){
        if (model.getId() == 0){
            Integer maxId;

            // Write new model
            if (emptyFile){
                model.setId(1);

                ArrayList<String> dataLines = new ArrayList<>();
                dataLines.add(model.toString());

                try {
                    writeArrayListToFile(dataLines);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                maxId = 0;

                // Read all records

            }

            return true;
        }

        // Updating by id

        return true;
    }

    boolean writeModelListToFile(ArrayList<BaseModel> modelsList){

        ArrayList<String> dataLines = new ArrayList<>();

        modelsList.forEach(n->{
            dataLines.add(n.toString());
        });

        try {
            writeArrayListToFile(dataLines);

        } catch (IOException e) {
             e.printStackTrace();
        }

        return true;
    }

    List<BaseModel> readFile(String fileName) {
        String SEPARATOR = ",";
        ArrayList<BaseModel> list = new ArrayList<>();

        File myObj = new File(fileName);
        if (!myObj.exists()){
            try {
                if (myObj.createNewFile()) {

                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

//        List<String> list = new ArrayList<>();

        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            ArrayList<String[]> list1 = (ArrayList<String[]>)stream
                    .map(str -> str.split(",", -1))
                    .collect(Collectors.toList());

            list1.forEach(n->{
                list.add(new User(Integer.parseInt(n[0]), n[1], n[2]));
            });

            return list;

        } catch (IOException e) {
            e.printStackTrace();
        }

//        try {
//            Path path = Paths.get(fileName);
//            Reader reader = Files.newBufferedReader(path, Charset.forName("UTF-8"));
//            BufferedReader bufferedReader = new BufferedReader(reader);
//            if (bufferedReader.lines().count() == 0){
//
//                // Creating admin user
//                User user = new User(ADMIN_USER_NAME, 0);
//                if (!writeData(user, true)){
//                    return list;
//                }
//
//            }
//                list = bufferedReader.lines().collect(Collectors.toList());
//                return list;
//
//        } catch (NoSuchFileException e) {
//            return list;
//            //e.printStackTrace();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            return list;
//
//        }

        return list;
    }

    @Override
    public ArrayList<BaseModel> readAll() {
        ArrayList<BaseModel> list = (ArrayList)readFile(STRING_FILE_NAME);

        return list;
    }

    public void createAdminUser(ArrayList<BaseModel> userArrayList){
        Integer maxId = 0;
        BaseModel maxIdModel = userArrayList.stream()
                    .max(Comparator.comparing(BaseModel::getId))
                    .orElse(null);

        if (maxIdModel == null){
            maxId = 0;
        }

        maxId++;

        userArrayList.add(new User(maxId, ADMIN_USER_NAME, ""));

        writeModelListToFile(userArrayList);

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

    public void setUserPassword(User user){
        String newPassword;
        while (true){
            newPassword = userInput(SET_NEW_PASSWORD_TEXT);
            if (!newPassword.equals("")){
                break;
            }
        }

        user.setPassword(newPassword);

    }

    public void checkSetAdminPassword(ArrayList<BaseModel> userArrayList){
        BaseModel adminUser = null;

        while (adminUser == null){
            adminUser = userArrayList.stream()
                    .filter(user -> ADMIN_USER_NAME.equalsIgnoreCase(user.getName()))
                    .findAny()
                    .orElse(null);

            if (adminUser == null){
                createAdminUser(userArrayList);
            } else {
                break;
            }
        }

        adminUser = userArrayList.stream()
                .filter(user -> ADMIN_USER_NAME.equalsIgnoreCase(user.getName()))
                .findAny()
                .orElse(null);

        if (((User)adminUser).getPassword().equals("")){
            setUserPassword((User)adminUser);
            writeModelListToFile(userArrayList);

        }

    }

    public void doLogin(ArrayList<BaseModel> userArrayList){
        while (true){
            String login = "";
            String password = "";
            while (true){
                login = userInput(LOGIN_INPUT_NAME_TEXT);
                if (!login.equals("")){
                    break;
                }
            }
            while (true){
                password = userInput(LOGIN_INPUT_PASSWORD_TEXT);
                if (!password.equals("")){
                    break;
                }
            }
            final String login1 = login;
            final String password1 = password;
            BaseModel foundUser = userArrayList.stream()
                    .filter(user -> (login1.equalsIgnoreCase(user.getName())))
                    .findAny()
                    .orElse(null);

            if (foundUser != null && ((User)foundUser).getPassword().equals(password1)){
                return;
            } else {
                System.err.println(LOGIN_ERROR_TEXT);
            }
        }

    }
}
