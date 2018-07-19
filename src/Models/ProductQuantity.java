package Models;

/**
 * Created by Michael on 7/6/2018.
 */
public class ProductQuantity {

    private Product product;
    private int quantity;

    //constructor
    public ProductQuantity(String productUPC, int quantity){

        this.quantity = quantity;
    }


    public int getQuantity(){
        return this.quantity;
    }

    public Product getProduct(){
        return this.product;
    }
}
