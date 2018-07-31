package GUIPages;

import Controllers.DatabaseController;
import Controllers.GuiController;
import Models.Customer;
import Models.Products;
import Models.Store;
import Models.Vendor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;

/**
 * Created by Channing Helmling-Cornell on 7/14/2018.
 */
public class MainMenu implements iPage
{
    private GuiController guiController;
    private Panel panel;

    MainMenu(GuiController guiController)
    {

        this.guiController = guiController;
        redraw();
    }

    private void redraw()
    {

        panel = new Panel(new LinearLayout(Direction.VERTICAL));
        panel.addComponent(new Label("Welcome to FastMart! Where it's fast and easy to shop!\nUse the arrow Keys "
                                     + "to highlight desired option. Press ENTER to select\n" +
                                     "Text Fields CAN be pasted into, using the hotkey Ctrl+Shift+V.\nMultiline "
                                     + "pasting not supported"));
        panel.addComponent(new Button("1. View Stores", new Runnable()
        {
            @Override
            public void run()
            {

                iPage Storelist = new DataTablePage(guiController, Store.retrieveStores(), "Stores");
                guiController.addAndDisplayPage(Storelist);

            }
        }));
        panel.addComponent(new Button("2. View Products", new Runnable()
        {
            @Override
            public void run()
            {

                iPage Productlist = new DataTablePage(guiController, Products.getProductList(), "Products");
                guiController.addAndDisplayPage(Productlist);
            }
        }));
        panel.addComponent(new Button("3. View Metrics/ Advanced Sample Queries", new Runnable()
        {
            @Override
            public void run()
            {

                iPage metricsMenu = new MetricsMenuPage(guiController);
                guiController.addAndDisplayPage(metricsMenu);
            }
        }));
        panel.addComponent(new Button("4. Execute Custom Query", new Runnable()
        {
            @Override
            public void run()
            {

                new AbitraryQueryPage(guiController);

            }
        }));
        panel.addComponent(new Button("5. Start Shopping", new Runnable()
        {
            @Override
            public void run()
            {

                guiController.addAndDisplayPage(new DataTablePage(guiController, Store.retrieveStores(), "Select "
                                                                                                         + "Store"));
            }
        }));
        panel.addComponent(new Button("6. Register as Frequent Shopper", new Runnable()
        {
            @Override
            public void run()
            {

                String name;
                String addr;
                String phone;
                String creditCard;
                name = guiController.textpopup("Name", "Please enter your name (first 32 letters)(Required).");
                addr = guiController.textpopup("Address", "Please enter your street address (first 255 letters)"
                                                          + "(Required).");
                phone = guiController.textpopup("Phone", "Please enter your Phone number (10 digits)(Required).");
                creditCard = guiController.textpopup("Credit Card", "Please enter your Credit card number (max 19 "
                                                                    + "digits) (Optional).");
                if ((name != null
                     && name.length() > 0
                     && name.length() <= 32
                     && addr != null
                     && addr.length() > 0
                     && addr.length() <= 255
                     && phone != null
                     && phone.length() == 10
                     && phone.matches("[0-9]{10}"))
                    && (
                            creditCard == null
                            || creditCard.matches("[0-9]{0,19}")
                    )
                )
                {
                    if (creditCard == null || creditCard.length() == 0)
                    {
                        DatabaseController.UpdateQuery("INSERT INTO FrequentShopper (name,address,phoneNum) VALUES "
                                                       + "('" + name + "', '" + addr + "', '" + phone + "')");
                    }
                    else
                    {
                        DatabaseController.UpdateQuery("INSERT INTO FrequentShopper (name, address, phoneNum, "
                                                       + "creditCard) VALUES ('" + name + "', '" + addr + "', '"
                                                       + phone + "', '" + creditCard + "')");
                    }
                    MessageDialog.showMessageDialog(guiController.textGUI, "Thank you for registering.",
                            "You are now a frequent shopper.", MessageDialogButton.OK);
                }
                else
                {
                    MessageDialog.showMessageDialog(guiController.textGUI, "Your entry was invalid",
                            "You have not been registered. Please try again.", MessageDialogButton.Close);
                }
            }
        }));
        panel.addComponent(new Button("7. View vendor information", new Runnable()
        {
            @Override
            public void run()
            {

                Integer vendorid = guiController.numPopup("Please enter the vendor ID");
                if (vendorid != null)
                {
                    iPage results = new DataTablePage(guiController, Vendor.getSingleVendorQuery(vendorid),
                            "Vendorinfo");
                    guiController.addAndDisplayPage(results);
                }
            }
        }));
        boolean restockenabled = DatabaseController.getRestockenabled();
        panel.addComponent(new Button("8. Toggle automatic restocking, Current status: "
                                      + String.valueOf(restockenabled),
                new Runnable()
                {
                    @Override
                    public void run()
                    {

                        if (restockenabled)
                        {
                            DatabaseController.disableRestockingTrigger();
                        }
                        else
                        {
                            DatabaseController.enableRestockingTrigger();
                        }
                        redraw();
                        guiController.refreshPage();
                    }
                }));
        panel.addComponent(new Button("View Shopping history for Customer", new Runnable()
        {
            @Override
            public void run()
            {

                Integer custid = guiController.numPopup("Enter the customer to check history for");
                if (custid != null)
                {
                    if (Customer.existsCustomer(custid))
                    {
                        iPage results = new DataTablePage(guiController, Customer.getOrdersOfCustomer(custid),
                                "Customer orders");
                        guiController.addAndDisplayPage(results);
                    }
                    else
                    {
                        MessageDialog.showMessageDialog(guiController.textGUI, "Error: Invalid customer",
                                "The requested "
                                + "customer does"
                                + " not exist");
                    }
                }

            }
        }));
        panel.addComponent(new Button("9. Back", guiController::closePage));
    }

    @Override
    public Panel getPanel()
    {

        return panel;
    }

}
