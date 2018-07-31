package GUIPages;

import Controllers.GuiController;
import Models.Cart;
import Models.Item;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.gui2.table.Table;

/**
 * Created by Channing Helmling-Cornell on 7/19/2018.
 */
public class ShoppingPage implements iPage
{
    private Panel root;
    private GuiController guiController;
    private Cart cart;

    ShoppingPage(GuiController guiController, int StoreID)
    {

        cart = new Cart(StoreID);
        this.guiController = guiController;

        draw();
    }

    public void draw()
    {

        root = null;
        root = new Panel(new BorderLayout());

        Panel itemsInStorePanel = new Panel(new LinearLayout(Direction.VERTICAL));
        Panel RightPanel = new Panel(new BorderLayout());
        Panel itemsInCartPanel = new Panel(new LinearLayout(Direction.VERTICAL));
        Panel Actions = new Panel(new LinearLayout(Direction.VERTICAL));
        Panel ActionTotal = new Panel(new BorderLayout());
        Panel totals = new Panel(new LinearLayout(Direction.VERTICAL));
        Panel totalText = new Panel(new LinearLayout(Direction.HORIZONTAL));
        itemsInStorePanel.addComponent(new Label("Available Items"));
        itemsInCartPanel.addComponent(new Label("Your Cart"));

        Table<String> inventoryTable = new Table<>("UPC", "Item Name", "Brand", "Cost", "Quantity Available");

        for (Item item : cart.getStoreContents())
        {
            inventoryTable.getTableModel().addRow(item.UPC, item.Name, item.Brand, String.valueOf(item.Price), String
                    .valueOf
                            (item.Quantity));
        }
        inventoryTable.setVisibleRows(25);
        inventoryTable.setSelectAction(new Runnable()
        {
            @Override
            public void run()
            {
                String UPC = inventoryTable.getTableModel().getCell(0, inventoryTable.getSelectedRow());
                Integer count = guiController.numPopup("Enter required quantity");
                boolean successful = cart.addItem(UPC, count);
                if (successful)
                {
                    redraw();
                }
                else
                {
                    MessageDialog.showMessageDialog(guiController.textGUI, "Error: ", "Requested product or quantity "
                                                                                      + "invalid. Cart has not been "
                                                                                      + "modified.", MessageDialogButton
                            .Close);
                }
            }
        });
        Table<String> cartTable = new Table<>("UPC", "Item Name", "Brand", "Cost", "Quantity in Cart");
        for (Item item : cart.getCartItemDetails())
        {
            cartTable.getTableModel().addRow(item.UPC, item.Name, item.Brand, String.valueOf(item.Price), String
                    .valueOf(item.Quantity));
        }
        cartTable.setVisibleRows(25);
        cartTable.setSelectAction(
                new Runnable()
                {
                    @Override
                    public void run()
                    {

                        String UPC = cartTable.getTableModel().getCell(0, cartTable.getSelectedRow());
                        Integer count = guiController.numPopup("Enter quantity to remove");
                        boolean successful = cart.removeItem(UPC, count);
                        if (successful)
                        {
                            redraw();
                        }
                        else
                        {
                            MessageDialog.showMessageDialog(guiController.textGUI, "Error: ",
                                    "Requested product or quantity "
                                    + "invalid. Cart has not been "
                                    + "modified.", MessageDialogButton.Close);
                        }
                    }
                });
        totals.addComponent(new Separator(Direction.HORIZONTAL));
        totalText.addComponent(new Label("Items in Cart: " + String.valueOf(cart.getItemcount())));
        totalText.addComponent(new Separator(Direction.VERTICAL));
        totalText.addComponent(new Label("Total: " + String.valueOf(cart.getTotalCost())));
        ActionTotal.addComponent(new EmptySpace(), BorderLayout.Location.CENTER);
        Actions.addComponent(new Button("Check Out",
                new Runnable()
                {
                    @Override
                    public void run()
                    {
                        boolean successful = cart.CheckOut();
                        if (successful)
                        {
                            MessageDialog.showMessageDialog(guiController.textGUI, "Checked out successfully",
                                    "Your order has been placed.", MessageDialogButton.Close);
                            guiController.closePage();
                        }
                        else
                        {
                            MessageDialog.showMessageDialog(guiController.textGUI, "Something went wrong...",
                                    "Your order was invalid, and has not been placed. Please ensure your cart is "
                                    + "valid, and place the order again.", MessageDialogButton.Close);
                        }
                    }
                }));
        Actions.addComponent(new Button("Empty Cart", new Runnable()
        {
            @Override
            public void run()
            {
                cart.emptyCart();
                redraw();
            }
        }));
        Actions.addComponent((cart.isSignedIn()) ?
                             new Button("Currently signed in as user:" +
                                        String.valueOf(cart.getCustomerId()) + " Sign out", new Runnable()
                             {
                                 @Override
                                 public void run()
                                 {

                                     cart.SignOut();
                                     redraw();
                                 }
                             })
                                                 :
                             new Button("Sign in as Frequent Shopper",
                                     new Runnable()
                                     {
                                         @Override
                                         public void run()
                                         {
                                             Integer id = guiController.numPopup("Please enter your customer ID: ");
                                             cart.setCustomerId(id);
                                             redraw();
                                         }
                                     }
                             ));


        Actions.addComponent(new Button("Leave Store", guiController::closePage));

        itemsInStorePanel.addComponent(inventoryTable);
        totals.addComponent(totalText);
        ActionTotal.addComponent(totals, BorderLayout.Location.TOP);
        ActionTotal.addComponent(Actions, BorderLayout.Location.BOTTOM);
        itemsInCartPanel.addComponent(cartTable);
        RightPanel.addComponent(itemsInCartPanel, BorderLayout.Location.CENTER);
        RightPanel.addComponent(ActionTotal, BorderLayout.Location.BOTTOM);
        root.addComponent(itemsInStorePanel, BorderLayout.Location.LEFT);
        root.addComponent(new Separator(Direction.VERTICAL), BorderLayout.Location.CENTER);
        root.addComponent(RightPanel, BorderLayout.Location.RIGHT);
    }

    private void redraw()
    {
        draw();
        guiController.refreshPage();
    }

    @Override
    public Panel getPanel()
    {
        return root;
    }
}
