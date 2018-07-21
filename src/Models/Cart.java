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
    public static boolean logIn(int customerId){
        ResultSet rs = null;

        //find shopper
        String query = "select * from frequentshopper where userid = " + customerId;
        rs = DatabaseController.MakeSelQuery(query);
        ArrayList<String[]> parsedRs = RSParser.rsToStringHeaders(rs);
        if (parsedRs == null || !parsedRs.get(1)[0].equals(String.valueOf(customerId))){
            return false;
        }
        Cart.currentCart.customerId = customerId;
        return true;
    }

    //retrieves the customer id associated with this cart
    public static int getCustomerId(){
        return Cart.currentCart.customerId;
    }

    //updates the current cart
    public static boolean newCart(int storeId){
        Cart.currentCart = new Cart(storeId);
        return true;
    }

    //adds a product to the cart by an amount
    public static boolean addItem(String upc, int count){
        int newQuant = count;
        ProductQuantity maybe = Cart.currentCart.hasItem(upc);
        if (maybe != null){
            newQuant += maybe.getQuantity();
            if (newQuant > Inventory.productStock(Cart.currentCart.getStoreId(), upc)){
                return false;
            }
            maybe.setQuantity(newQuant);
            return true;
        }
        if (newQuant > Inventory.productStock(currentCart.getStoreId(), upc)){
            return false;
        }
        Cart.currentCart.getCartContents().add(new ProductQuantity(upc, count));
        return true;
    }

    //total number of items in the cart
    public static int numberOfItems(){
        return Cart.currentCart.getCartContents().size();
    }

    //list of number of units for every item
    public static int[] itemsQuantities(){
        int[] prodQuant = new int[numberOfItems()];
        for (int val = 0; val < prodQuant.length; val ++){
            prodQuant[val] = Cart.currentCart.getCartContents().get(val).getQuantity();
        }
        return prodQuant;
    }

    //list of result set for every item
    public static ArrayList<ResultSet> itemsInfo(){
        return Cart.currentCart.itemsInfo;
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

    public ArrayList<ProductQuantity>getCartContents(){
        return items;
    }
    
    public void setStoreId(int storeId){
        this.storeId = storeId;
    }

    public int getStoreId() {
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
