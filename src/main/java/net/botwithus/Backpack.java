package net.botwithus;

import net.botwithus.rs3.game.Item;
import net.botwithus.rs3.game.hud.interfaces.Component;
import net.botwithus.rs3.game.minimenu.MiniMenu;
import net.botwithus.rs3.game.minimenu.actions.ComponentAction;
import net.botwithus.rs3.game.queries.builders.components.ComponentQuery;
import net.botwithus.rs3.game.queries.builders.items.InventoryItemQuery;
import net.botwithus.rs3.script.Script;

/**
 * Backpack is a class to interact with the backpack interface
 *
 * @see InventoryContainer
 * @see ComponentQuery
 * @see Component
 * @see InventoryItemQuery
 */

public final class Backpack {

    private static final InventoryContainer INSTANCE = InventoryContainer.of(93);

    private static final int INTERFACE = 1473;
    private static final int COMPONENT = 5;

    private static final ComponentQuery query = ComponentQuery.newQuery(INTERFACE).componentIndex(COMPONENT).mark();

    private Backpack() {
    }

    public static boolean isEmpty() {
        return INSTANCE.isEmpty();
    }

    public static boolean isFull() {
        return INSTANCE.isFull();
    }

    /**
     * Interact with an item in the backpack
     *
     * @param name   the name of the items to interact with
     * @param option the option to interact with
     * @return true if the interaction was successful
     */

    public static boolean interact(String name, String option) {
        Component comp = query.reset().itemName(name).results().first();
        if (comp != null) {
            return comp.interact(option);
        }
        return false;
    }

    /**
     * Interact with an item in the backpack
     *
     * @param id     the id of the item to interact with
     * @param option the option to interact with
     * @return true if the interaction was successful
     */

    public static boolean interact(int id, String option) {
        Component comp = query.reset().item(id).results().first();
        if (comp != null) {
            return comp.interact(option);
        }
        return false;
    }

    public static boolean use(int id) {
        Component comp = query.reset().item(id).results().first();
        if (comp != null) {
            return MiniMenu.interact(ComponentAction.COMPONENT_ITEM.getType(), 0, comp.getSubComponentIndex(), comp.getInterfaceIndex() << 16 | comp.getComponentIndex());
        }
        return false;
    }

    public static boolean use(String name) {
        Component comp = query.reset().itemName(name).results().first();
        if (comp != null) {
            return MiniMenu.interact(ComponentAction.COMPONENT_ITEM.getType(), 0, comp.getSubComponentIndex(), comp.getInterfaceIndex() << 16 | comp.getComponentIndex());
        }
        return false;
    }

    public static Item getSlot(int slot) {
        return INSTANCE.slot(slot);
    }

    /**
     * Interact with an item in the backpack
     *
     * @param category the category of the items to interact with
     * @param option   the option to interact with
     * @return true if the interaction was successful
     */

    public static boolean interactByCategory(int category, String option) {
        Component comp = query.reset().itemCategory(category).results().first();
        if (comp != null) {
            return comp.interact(option);
        }
        return false;
    }

    /**
     * Check if the backpack contains a specific item
     *
     * @param name the name of the items to check for
     * @return true if the backpack contains the item
     */

    public static boolean contains(String name) {
        return INSTANCE.contains(name);
    }

    /**
     * Check if the backpack contains a specific item
     *
     * @param id the id of the item to check for
     * @return true if the backpack contains the item
     */

    public static boolean contains(int id) {
        return INSTANCE.contains(id);
    }

    public static boolean contains(String name, long amount) {
        return INSTANCE.contains(name, amount);
    }

    public static boolean contains(int id, long amount) {
        return INSTANCE.contains(id, amount);
    }

    public static boolean containsByCategory(int category) {
        return INSTANCE.containsCategory(category);
    }

    public static void print(Script script) {
        for (Item item : INSTANCE.getItems()) {
            script.println(item);
        }
    }

    /**
     * Get the InventoryContainer for the backpack
     *
     * @return the InventoryContainer for the backpack
     */
    public static InventoryContainer container() {
        return INSTANCE;
    }

}
