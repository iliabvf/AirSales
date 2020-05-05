package net.iliabvf.javaio.controller;

import net.iliabvf.javaio.model.BaseModel;
import net.iliabvf.javaio.model.Route;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RouteController extends BaseController {
    static String STRING_FILE_NAME = "routes.csv";

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
                list.add(new Route(Integer.parseInt(n[0]), n[1]));
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

    public BaseModel findById(Integer id){
        ArrayList<BaseModel> modelsList = readAll();

        BaseModel foundModel = modelsList.stream()
                .filter(model -> (id.toString()).equals(model.getId()))
                .findAny()
                .orElse(null);

        return foundModel;
    }

    public BaseModel findByName(String name){
        ArrayList<BaseModel> modelsList = readAll();

        BaseModel foundModel = modelsList.stream()
                .filter(model -> name.equalsIgnoreCase(model.getName()))
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

    public BaseModel addRouteToFile(String name){
        BaseModel baseModel = null;

        ArrayList<BaseModel> routersList = readAll();

        Integer maxId = 0;
        BaseModel maxIdModel = routersList.stream()
                .max(Comparator.comparing(BaseModel::getId))
                .orElse(null);

        if (maxIdModel == null){
            maxId = 0;
        }

        maxId++;

        baseModel = new Route(maxId, name);
        routersList.add(baseModel);

        writeModelListToFile(routersList);

        return baseModel;
    }

    public boolean removeRouteToFile(BaseModel model){
        ArrayList<BaseModel> routersList = readAll();

        for (BaseModel baseModel : routersList){
            if (routersList.equals(baseModel)){
                routersList.remove(baseModel);
                break;
            }
        }

        writeModelListToFile(routersList);

        return true;
    }

}
