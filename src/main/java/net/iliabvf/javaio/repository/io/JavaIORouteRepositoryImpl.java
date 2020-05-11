package net.iliabvf.javaio.repository.io;

import net.iliabvf.javaio.exceptions.CreationException;
import net.iliabvf.javaio.exceptions.DeleteException;
import net.iliabvf.javaio.exceptions.ReadingException;
import net.iliabvf.javaio.model.Route;
import net.iliabvf.javaio.repository.RouteRepository;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavaIORouteRepositoryImpl extends RouteRepository {
    RouteRepository repo;

    static String STRING_FILE_NAME = "routes.csv";

    @Override
    public Route addModelToFile(Route route) throws CreationException, ReadingException {
        Route baseModel = null;

        ArrayList<Route> routersList = readAll();

        Integer maxId = 0;
        Route maxIdModel = routersList.stream()
                .max(Comparator.comparing(Route::getId))
                .orElse(null);

        if (maxIdModel == null){
            maxId = 0;
        } else {
            maxId = maxIdModel.getId();
        }

        maxId++;

        baseModel = new Route(maxId, route.getName());
        routersList.add(baseModel);

        writeModelListToFile(routersList);

        return baseModel;
    }

    public ArrayList<Route> readAll() {
        ArrayList<Route> list = (ArrayList)readFile(STRING_FILE_NAME);

        return list;
    }

    List<Route> readFile(String fileName) {
        String SEPARATOR = ",";
        ArrayList<Route> list = new ArrayList<>();

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

        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            ArrayList<String[]> list1 = (ArrayList<String[]>)stream
                    .map(str -> str.split(",", -1))
                    .collect(Collectors.toList());

            list1.forEach(n->{
                list.add(new Route(Integer.parseInt(n[0]), n[1]));
            });

            return list;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    boolean writeModelListToFile(ArrayList<Route> modelsList){
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


    @Override
    public Route findByName(String name) throws ReadingException {
        ArrayList<Route> modelsList = readAll();

        Route foundModel = modelsList.stream()
                .filter(model -> name.equalsIgnoreCase(model.getName()))
                .findAny()
                .orElse(null);

        return foundModel;
    }

    @Override
    public Boolean removeModelFromFile(Route model) throws DeleteException {
        ArrayList<Route> routersList = readAll();

        for (Route baseModel : routersList){
            if (routersList.equals(baseModel)){
                routersList.remove(baseModel);
                break;
            }
        }

        writeModelListToFile(routersList);

        return true;
    }

    @Override
    public Route findById(Integer id) throws ReadingException {
        ArrayList<Route> modelsList = readAll();

        Route foundModel = modelsList.stream()
                .filter(model -> {
                    Boolean isEqual = (id == model.getId());
                    return isEqual;
                }

                )
                .findAny()
                .orElse(null);

        return foundModel;
    }


}
