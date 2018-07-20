package GUIPages;

import Controllers.GuiController;
import com.googlecode.lanterna.gui2.*;

/**
 * Created by Channing Helmling-Cornell on 7/19/2018.
 */
public class ShoppingPage implements iPage
{
    Integer itemcount = 0;
    Float cost = 0.0f;
    private Panel root;

    ShoppingPage(GuiController guiController, int StoreID)
    {

        root = new Panel(new BorderLayout());
        Panel itemsInStorePanel = new Panel(new LinearLayout(Direction.VERTICAL));

        itemsInStorePanel.addComponent(new Label("Available Items"));

        root.addComponent(itemsInStorePanel, BorderLayout.Location.LEFT);
        root.addComponent(new Separator(Direction.VERTICAL), BorderLayout.Location.CENTER);

        Panel RightPanel = new Panel(new BorderLayout());
        Panel itemsInCartPanel = new Panel(new LinearLayout(Direction.VERTICAL));
        itemsInCartPanel.addComponent(new Label("Your Cart"));
        RightPanel.addComponent(itemsInCartPanel, BorderLayout.Location.CENTER);
        Panel ActionTotal = new Panel(new BorderLayout());
        Panel totals = new Panel(new LinearLayout(Direction.VERTICAL));
        totals.addComponent(new Separator(Direction.HORIZONTAL));
        Panel totalText = new Panel(new LinearLayout(Direction.HORIZONTAL));
        totalText.addComponent(new Label("Items in Cart: " + itemcount.toString()));
        totalText.addComponent(new Separator(Direction.VERTICAL));
        totalText.addComponent(new Label("Total: " + cost.toString()));
        totals.addComponent(totalText);
        ActionTotal.addComponent(totals, BorderLayout.Location.TOP);
        ActionTotal.addComponent(new EmptySpace(), BorderLayout.Location.CENTER);
        Panel Actions = new Panel(new LinearLayout(Direction.VERTICAL));
        Actions.addComponent(new Button("Check Out"));
        Actions.addComponent(new Button("Empty Cart"));
        Actions.addComponent(new Button("Leave Store"));
        Actions.addComponent(new Button("Sign in as Frequent Shopper"));
        ActionTotal.addComponent(Actions, BorderLayout.Location.BOTTOM);
        RightPanel.addComponent(ActionTotal, BorderLayout.Location.BOTTOM);
        root.addComponent(RightPanel, BorderLayout.Location.RIGHT);

    }

    public void Redraw()
    {

    }

    @Override
    public Panel getPanel()
    {

        return root;
    }
}
