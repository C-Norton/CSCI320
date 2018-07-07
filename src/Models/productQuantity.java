package Models;

/**
 * Created by Michael on 7/6/2018.
 */
public class productQuantity {

    private Product product;
    private int quantity;

    public productQuantity(Product product, int quantity){
        this.product = product;
        this.quantity = quantity;
    }

    public int getQuantity(){
        return this.quantity;
    }

    public Product getProduct(){
        return this.product;
    }
}
