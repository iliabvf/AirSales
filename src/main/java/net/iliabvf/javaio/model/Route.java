package net.iliabvf.javaio.model;

public class Route extends BaseModel {

    public Route(Integer id, String name) {
        super(id, name);
    }

    @Override
    public String toString() {
        return id.toString() + "," + name;
    }


}
