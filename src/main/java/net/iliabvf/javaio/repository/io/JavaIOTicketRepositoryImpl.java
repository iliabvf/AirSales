package net.iliabvf.javaio.repository.io;

import net.iliabvf.javaio.controller.RouteController;
import net.iliabvf.javaio.exceptions.CreationException;
import net.iliabvf.javaio.exceptions.DeleteException;
import net.iliabvf.javaio.exceptions.ReadingException;
import net.iliabvf.javaio.model.Route;
import net.iliabvf.javaio.model.Ticket;
import net.iliabvf.javaio.model.TicketType;
import net.iliabvf.javaio.repository.TicketRepository;

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

public class JavaIOTicketRepositoryImpl extends TicketRepository {
    static String STRING_FILE_NAME = "tickets.csv";

    @Override
    public Ticket findByName(String searchRoute) throws ReadingException {
        return null;
    }

    @Override
    public Boolean removeModelFromFile(Ticket baseModel) throws DeleteException {
        ArrayList<Ticket> modelsList = readAll();
        for (Ticket model : modelsList){
            if (model.equals(baseModel)){
                modelsList.remove(model);
                break;
            }
        }

        writeModelListToFile(modelsList);

        return true;
    }

    @Override
    public Ticket findById(Integer id) throws ReadingException {
        return null;
    }

    @Override
    public Ticket findByDateRouteType(String date, Route route, TicketType type) throws ReadingException {
        ArrayList<Ticket> modelsList = readAll();
        Ticket foundModel = modelsList.stream()
                .filter(model -> {
                    Boolean isEqual = (
                    (date.equals((model).getDate())
                        && route.equals(model.getRoute())
                        && type.equals((model).getTicketType())
                    ));
                    return isEqual;
                })
                .findAny()
                .orElse(null);

        return foundModel;
    }

    @Override
    public Ticket addModelToFile(Ticket newModel) throws CreationException, ReadingException {
        ArrayList<Ticket> modelsArrayList = readAll();

        Integer maxId = 0;
        Ticket maxIdModel = modelsArrayList.stream()
                .max(Comparator.comparing(Ticket::getId))
                .orElse(null);

        if (maxIdModel == null){
            maxId = 0;
        } else {
            maxId = maxIdModel.getId();
        }

        maxId++;
        newModel.setId(maxId);

        modelsArrayList.add(newModel);

        writeModelListToFile(modelsArrayList);

        return newModel;
    }

    boolean writeModelListToFile(ArrayList<Ticket> modelsList){
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

    public void writeArrayListToFile(ArrayList<String> dataLines) throws IOException {
        File csvOutputFile = new File(STRING_FILE_NAME);
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            dataLines.stream()
                    .forEach(pw::println);
        }
    }

    public ArrayList<Ticket> readAll() {
        ArrayList<Ticket> list = (ArrayList)readFile(STRING_FILE_NAME);

        return list;
    }

    List<Ticket> readFile(String fileName) {
        String SEPARATOR = ",";
        ArrayList<Ticket> list = new ArrayList<>();

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
                TicketType ticketType = TicketType.valueOf(Integer.parseInt(n[1]));

                RouteController routeController = new RouteController();
                Route route = null;
                try {
                    route = routeController.findById(Integer.parseInt(n[3]));
                } catch (ReadingException e){
//                    System.err.println();
                }

                list.add(new Ticket(Integer.parseInt(n[0]), ticketType, n[2], route));
            });

            return list;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }


}
