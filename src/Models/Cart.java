package Models;

import Controllers.DatabaseController;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by Michael on 7/6/2018.
 */
public class Cart
{
    public Integer CustomerId;
    private int StoreId;
    private LinkedHashMap<String, CartEntry> Contents;
    private LinkedHashMap<String, CartEntry> StoreContents;
    private int itemcount;
    private float totalCost;

    public Cart(int StoreId)
    {

        this.StoreId = StoreId;
        CustomerId = null;
        itemcount = 0;
        totalCost = 0.0f;
        Contents = new LinkedHashMap<>();
        StoreContents = CartEntry.RStoContents(DatabaseController.SelectQuery
                (("Select Product.UPC, Product.Name, Product.Brand, Product.Price, Inventory.Quantity from product "
                  + "join inventory on Product.UPC = inventory.productUPC where inventory.storeId="
                  + String.valueOf(StoreId)), false));

    }

    public ArrayList<CartEntry> getCartItemDetails()
    {

        ArrayList<CartEntry> conts = new ArrayList<>();
        conts.addAll(Contents.values());
        return conts;
    }

    public boolean addItem(String UPC, int quantity)
    {

        if (StoreContents.containsKey(UPC))
        {
            CartEntry item = StoreContents.get(UPC);
            int desiredQuantity = quantity + (Contents.containsKey(UPC) ? Contents.get(UPC).Quantity : 0);
            if (item.Quantity >= desiredQuantity)
            {
                CartEntry entry = new CartEntry();
                entry.UPC = UPC;
                entry.Name = item.Name;
                entry.Brand = item.Brand;
                entry.Price = item.Price;
                entry.Quantity = desiredQuantity;
                Contents.put(UPC, entry);
                itemcount += quantity;
                totalCost += quantity * entry.Price;
                return true;
            }
        }
        return false;
    }

    public boolean removeItem(String UPC, int quantity)
    {

        if (Contents.containsKey(UPC))
        {
            CartEntry item = Contents.get(UPC);
            int desiredQuantity = item.Quantity - quantity;
            if (desiredQuantity > 0)
            {
                Contents.get(UPC).Quantity = desiredQuantity;
                totalCost -= item.Price * quantity;
                itemcount -= quantity;
                return true;
            }
            else if (desiredQuantity == 0)
            {
                totalCost -= item.Price * quantity;
                itemcount -= quantity;
                Contents.remove(UPC);
                return true;
            }
        }
        return false;
    }

    public boolean CheckOut()
    {
        /*
        ResultSet storeInventory = DatabaseController.SelectQuery("Select * from Inventory where storeId = "
                                                                  + String.valueOf(StoreId), true);
        boolean succesful = true;

        while (storeInventory.next())
        {
            String storeUPC = storeInventory.getString(2);
            if (Contents.containsKey(storeUPC))
            {
                CartEntry entry = Contents.get(storeUPC);
                int storecount = storeInventory.getInt(3);
                if (storecount >= entry.Quantity)
                {
                    storeInventory.updateInt(3, storecount - entry.Quantity);
                }
                else
                {
                    succesful = false;
                }
            }
        }
        storeInventory.beforeFirst();
        while (storeInventory.next())
        {
            if (succesful)
            {
                storeInventory.updateRow();
            }
            else
            {
                storeInventory.cancelRowUpdates();
            }
        }
        if (!succesful)
        {
            return false;
        }
        int updated = (CustomerId == null) ? DatabaseController.UpdateQuery("Insert into Orders (storeId) VALUES("
                + String.valueOf(StoreId) + ")")
                            : DatabaseController.UpdateQuery("Insert into Orders (userId, storeId) VALUES("
                              + CustomerId.toString()+ "," + String.valueOf(StoreId)+ ")");
        if (updated == -1){
            return false;
        }
        for (CartEntry me:Contents.values())
        {
            DatabaseController.UpdateQuery("Insert into prodQuantities ");
        }
        return succesful;
        */
        return false;
    }

    public float getTotalCost()
    {

        return totalCost;
    }

    public int getItemcount()
    {

        return itemcount;
    }
}
