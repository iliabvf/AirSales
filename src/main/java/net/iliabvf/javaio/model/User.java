package net.iliabvf.javaio.model;

public class User extends BaseModel {
    private String password;
    private String name;

    public User(Integer id, String name, String password) {
        super(id);
        this.name = name;
        this.password = password;
    }

    @Override
    public String toString() {
        return id.toString() + "," + name + "," + password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
