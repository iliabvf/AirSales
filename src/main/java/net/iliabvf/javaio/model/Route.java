package net.iliabvf.javaio.model;

public class Route extends BaseModel {
    private String name;

    public Route(Integer id, String name) {
        super(id);
        this.name = name;
    }

    @Override
    public String toString() {
        return id.toString() + "," + name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Route route = (Route)o;
        return id == route.getId() &&
                name.equals(route.name);
    }
}
