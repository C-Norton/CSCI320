package Models;

import Controllers.DatabaseController;
import Utilities.RSParser;
import Utilities.StatementTemplate;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Michael on 7/6/2018.
 */
public class Cart {
    private static Cart currentCart;

    //stores the customer id, if existent
    public static boolean setCustomerId(int customerId){
        //find shopper
        if (!Customer.existsCustomer(customerId)){
            return false;
        }
        currentCart.customerId = customerId;
        return true;
    }

    //retrieves the customer id associated with this cart
    public static int getCustomerId(){
        return currentCart.customerId;
    }

    //updates the current cart
    public static boolean newCart(int storeId){
        //find store
        if (!Store.existsStore(storeId)){
            return false;
        }
        currentCart = new Cart(storeId);
        return true;
    }

    //adds a product to the cart by an amount
    public static boolean addItem(String upc, int count){
        int newQuant = count;
        ProductQuantity maybe = currentCart.hasItem(upc);
        if (maybe != null){
            newQuant += maybe.getQuantity();
            if (newQuant > Inventory.productStock(currentCart.getStoreId(), upc)){
                return false;
            }
            maybe.setQuantity(newQuant);
            return true;
        }
        if (newQuant > Inventory.productStock(currentCart.getStoreId(), upc)){
            return false;
        }
        currentCart.getCartContents().add(new ProductQuantity(upc, count));
        return true;
    }

    //total number of items in the cart
    public static int numberOfItems(){
        return currentCart.getCartContents().size();
    }

    //list of number of units for every item
    public static int[] itemsQuantities(){
        int[] prodQuant = new int[numberOfItems()];
        for (int val = 0; val < prodQuant.length; val ++){
            prodQuant[val] = currentCart.getCartContents().get(val).getQuantity();
        }
        return prodQuant;
    }

    //list of result set for every item
    public static ArrayList<ResultSet> itemsInfo(){
        return currentCart.itemsInfo;
    }

    public static float total(){
        if (numberOfItems() == 0){
            return 0;
        }
        float total = 0;
        for (int i = 0; i < numberOfItems(); i++){
            Float cost = Float.valueOf(RSParser.rsToStringHeaders(itemsInfo().get(i)).get(1)[3]);
            total += cost * itemsQuantities()[i];
        }
        return total;
    }

    public static boolean checkOut(){
        return Order.makeOrder(currentCart.items, currentCart.customerId, currentCart.getStoreId());
    }

    //instance properties
    private ArrayList<ProductQuantity> items;
    private ArrayList<ResultSet> itemsInfo;
    private int storeId;
    private int customerId;

    private Cart(int storeId){
        this.items = new ArrayList<>();
        this.itemsInfo = new ArrayList<>();
        this.storeId = storeId;
        this.customerId = -1;
    }

    //--Getters and Setters--//

    public void addItem(ProductQuantity product){
        items.add(product);
        itemsInfo.add(Products.getDetailsOfProduct(product.getProductUPC()));
    }
    
    public boolean removeItem(String upc){
        int index = itemIndex(upc);
        if (index == -1){
            return false;
        }
        items.remove(index);
        return true;
    }

    private ArrayList<ProductQuantity>getCartContents(){
        return items;
    }
    
    public void setStoreId(int storeId){
        this.storeId = storeId;
    }

    private int getStoreId() {
        return storeId;
    }

    private ProductQuantity hasItem(String upc){
        for (ProductQuantity quant:items){
            if (quant.getProductUPC().equals(upc)){
                return quant;
            }
        }
        return null;
    }

    private int itemIndex(String upc){
        int val = 0;
        for (ProductQuantity quant:items){
            if (quant.getProductUPC().equals(upc)){
                return val;
            }
            val ++;
        }
        return -1;
    }
}
