package Models;

/**
 * Created by Michael on 7/6/2018.
 */
public class Product {

    private String name;
    private String brand;
    private float price;
    private String upc;
    private Vendor vendor;

    //constructor
    public Product(String upc, String name, String brand, float price,int vendorID){
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.upc = upc;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public float getPrice() {
        return price;
    }

    public String getUpc() {
        return upc;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }
}
