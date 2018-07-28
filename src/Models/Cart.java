package Models;

import Controllers.DatabaseController;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by Michael on 7/6/2018.
 */
public class Cart
{
    private Integer CustomerId;
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
                  + String.valueOf(StoreId))));

    }

    public void SignOut()
    {

        CustomerId = null;
    }

    public int getCustomerId()
    {

        if (CustomerId == null)
        {
            return -1;
        }
        else
        {
            return CustomerId;
        }
    }

    public void setCustomerId(Integer id)
    {

        if (id != null && Customer.existsCustomer(id))
        {
            CustomerId = id;
        }
        else
        {
            CustomerId = null;
        }
    }

    public ArrayList<CartEntry> getStoreContents()
    {

        ArrayList<CartEntry> conts = new ArrayList<>();
        conts.addAll(StoreContents.values());
        return conts;
    }

    public boolean isSignedIn()
    {

        return CustomerId != null;
    }

    public boolean addItem(String UPC, Integer quantity)
    {

        if (quantity == null)
        {
            return false;
        }
        if (quantity == 0)
        {
            return false;
        }
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

    public boolean removeItem(String UPC, Integer quantity)
    {

        if (quantity == null)
        {
            return false;
        }
        if (quantity == 0)
        {
            return false;
        }

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

        return DatabaseController.createOrder(StoreId, CustomerId, getCartItemDetails());
    }

    public ArrayList<CartEntry> getCartItemDetails()
    {

        ArrayList<CartEntry> conts = new ArrayList<>();
        conts.addAll(Contents.values());
        return conts;
    }

    public float getTotalCost()
    {

        return totalCost;
    }

    public int getItemcount()
    {

        return itemcount;
    }

    public void emptyCart()
    {

        Contents = new LinkedHashMap<>();
        itemcount = 0;
        totalCost = 0;
    }
}
