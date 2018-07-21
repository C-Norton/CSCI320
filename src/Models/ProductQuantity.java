package Models;

/**
 * Created by Michael on 7/6/2018.
 */
public class ProductQuantity {

    //private Product product;
    private int quantity;
    private String productUPC;

    //constructor
    public ProductQuantity(String productUPC, int quantity){
        this.productUPC = productUPC;
        this.quantity = quantity;
    }


    public int getQuantity(){
        return this.quantity;
    }
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public String getProductUPC(){
        return this.productUPC;
    }
    //public Product getProduct(){
    //return this.product;
    //}
}
