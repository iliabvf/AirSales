package net.iliabvf.javaio.model;

public class User extends BaseModel {
    String password;

    public User(Integer id, String name, String password) {
        super(id, name);
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
}
