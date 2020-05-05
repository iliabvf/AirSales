package net.iliabvf.javaio.controller;

import net.iliabvf.javaio.model.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TicketController extends BaseController  {
    static String STRING_FILE_NAME = "tickets.csv";

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
                TicketType ticketType = TicketType.valueOf(Integer.parseInt(n[2]));

                RouteController routeController = new RouteController();
                Route route = (Route)routeController.findById(Integer.parseInt(n[4]));

                list.add(new Ticket(Integer.parseInt(n[0]), n[1], ticketType, n[3], route));
            });

            return list;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public ArrayList<BaseModel> readAll() {
        ArrayList<BaseModel> list = (ArrayList)readFile(STRING_FILE_NAME);

        return list;
    }

    public BaseModel findByDate(String date){
        ArrayList<BaseModel> modelsList = readAll();

        BaseModel foundModel = modelsList.stream()
                .filter(model -> date.equals(((Ticket)model).getDate()))
                .findAny()
                .orElse(null);

        return foundModel;
    }

    public BaseModel findByDateRouteType(String date, Route route, TicketType type){
        ArrayList<BaseModel> modelsList = readAll();
        BaseModel foundModel = modelsList.stream()
                .filter(model -> {

                    return (date.equals(((Ticket)model).getDate())
                        && route.equals(((Ticket)model).getRoute())
                        && type.equals(((Ticket)model).getTicketType())
                    );
                })
                .findAny()
                .orElse(null);

        return foundModel;
    }

    public void writeArrayListToFile(ArrayList<String> dataLines) throws IOException {
        File csvOutputFile = new File(STRING_FILE_NAME);
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            dataLines.stream()
                    .forEach(pw::println);
        }
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

    public boolean addModelToFile(BaseModel newModel){
        ArrayList<BaseModel> modelsArrayList = readAll();

        Integer maxId = 0;
        BaseModel maxIdModel = modelsArrayList.stream()
                .max(Comparator.comparing(BaseModel::getId))
                .orElse(null);

        if (maxIdModel == null){
            maxId = 0;
        }

        maxId++;

        modelsArrayList.add(newModel);

        writeModelListToFile(modelsArrayList);

        return true;
    }

    public boolean removeModelFromFile(BaseModel baseModel){
        ArrayList<BaseModel> modelsList = readAll();
        for (BaseModel model : modelsList){
            if (model.equals(baseModel)){
                modelsList.remove(model);
                break;
            }
        }
        return true;
    }

}
