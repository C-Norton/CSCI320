package GUIPages;

import Controllers.GuiController;
import Models.Cart;
import Models.Store;
import com.googlecode.lanterna.gui2.*;

/**
 * Created by Channing Helmling-Cornell on 7/19/2018.
 */
public class ShoppingPage implements iPage
{

    private Panel root;
    private GuiController guiController;
    private Panel StoreItems;
    private Cart cart;

    ShoppingPage(GuiController guiController, int StoreID)
    {

        cart = new Cart(StoreID);
        this.guiController = guiController;
        StoreItems = (new DataTablePage(guiController, Store.getInventoryMetadata(StoreID), "CartPanel"))
                .getPanel();
        redraw();

    }

    public void redraw()
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

        totals.addComponent(new Separator(Direction.HORIZONTAL));
        //TODO:totalText.addComponent(new Label("Items in Cart: " + Cart.numberOfItems()));
        totalText.addComponent(new Separator(Direction.VERTICAL));
        //TODO:totalText.addComponent(new Label("Total: " + Cart.total()));


        ActionTotal.addComponent(new EmptySpace(), BorderLayout.Location.CENTER);
        Actions.addComponent(new Button("Check Out"));
        Actions.addComponent(new Button("Empty Cart", new Runnable()
        {
            @Override
            public void run()
            {


            }
        }));
        Actions.addComponent(new Button("Sign in as Frequent Shopper"));
        Actions.addComponent(new Button("Leave Store", guiController::closePage));

        itemsInStorePanel.addComponent(StoreItems);
        totals.addComponent(totalText);
        ActionTotal.addComponent(totals, BorderLayout.Location.TOP);
        ActionTotal.addComponent(Actions, BorderLayout.Location.BOTTOM);
        RightPanel.addComponent(itemsInCartPanel, BorderLayout.Location.CENTER);
        RightPanel.addComponent(ActionTotal, BorderLayout.Location.BOTTOM);

        root.addComponent(itemsInStorePanel, BorderLayout.Location.LEFT);
        root.addComponent(new Separator(Direction.VERTICAL), BorderLayout.Location.CENTER);
        root.addComponent(RightPanel, BorderLayout.Location.RIGHT);
    }

    @Override
    public Panel getPanel()
    {

        return root;
    }
}
