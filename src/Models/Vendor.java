package Models;

/**
 * Created by Michael on 7/6/2018.
 */
public class Vendor {
    private int id;
    private String name;
    private String location;
    private String rep;
    private String phone;

    public Vendor(int id, String name, String location, String rep, String phone) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.rep = rep;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRep() {
        return rep;
    }

    public void setRep(String rep) {
        this.rep = rep;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
