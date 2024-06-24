package net.botwithus;

import net.botwithus.rs3.game.hud.interfaces.Component;
import net.botwithus.rs3.game.queries.builders.components.ComponentQuery;

import java.util.Arrays;

/**
 * Equipment is a class to interact with the equipment interface
 * @see InventoryContainer
 * @see ComponentQuery
 * @see Component
 */

public final class Equipment {

    private Equipment() {
    }

    private static final InventoryContainer INSTANCE = InventoryContainer.of(94);

    private static final int INTERFACE = 1464;
    private static final int COMPONENT = 15;

    private static final ComponentQuery query = ComponentQuery.newQuery(INTERFACE).componentIndex(COMPONENT).mark();

    /**
     * Interact with an item in the equipment
     * @param slot the slot of the items to interact with
     * @param index the option index to interact with
     * @return true if the interaction was successful
     */
    public static boolean interact(Slot slot, int index) {
        Component comp = query.reset().subComponentIndex(slot.getIndex()).subComponentIndex(index).results().first();
        if(comp != null) {
            return comp.interact(index);
        }
        return false;
    }

    /**
     * Interact with an item in the equipment
     * @param slot the slot of the items to interact with
     * @param option the option to interact with
     * @return true if the interaction was successful
     */

    public static boolean interact(Slot slot, String option) {
        Component comp = query.reset().subComponentIndex(slot.getIndex()).results().first();
        if(comp != null) {
            return comp.interact(option);
        }
        return false;
    }

    /**
     * Interact with an item in the equipment
     * @param name the name of the items to interact with
     * @param option the option to interact with
     * @return true if the interaction was successful
     */
    public static boolean interact(String name, String option) {
        Component comp = query.reset().itemName(name).results().first();
        if(comp != null) {
            return comp.interact(option);
        }
        return false;
    }

    /**
     * Interact with an item in the equipment
     * @param id the id of the item to interact with
     * @param option the option to interact with
     * @return true if the interaction was successful
     */
    public static boolean interact(int id, String option) {
        Component comp = query.reset().item(id).results().first();
        if(comp != null) {
            return comp.interact(option);
        }
        return false;
    }

    /**
     * Interact with an item in the backpack
     * @param category the category of the items to interact with
     * @param option the option to interact with
     * @return true if the interaction was successful
     */

    public static boolean interactByCategory(int category, String option) {
        Component comp = query.reset().itemCategory(category).results().first();
        if(comp != null) {
            return comp.interact(option);
        }
        return false;
    }

    /**
     * Check if the backpack contains a specific item
     * @param name the name of the items to check for
     * @return true if the backpack contains the item
     */

    public static boolean contains(String name) {
        return INSTANCE.contains(name);
    }

    /**
     * Check if the backpack contains a specific item
     * @param id the id of the item to check for
     * @return true if the backpack contains the item
     */

    public static boolean contains(int id) {
        return INSTANCE.contains(id);
    }

    /**
     * Get the InventoryContainer for Equipment
     * @return the InventoryContainer for the players Equipment
     */
    public static InventoryContainer container() {
        return INSTANCE;
    }

    public enum Slot {
        HEAD(0),
        CAPE(1),
        NECK(2),
        WEAPON(3),
        BODY(4),
        SHIELD(5),
        LEGS(7),
        HANDS(9),
        FEET(10),
        RING(12),
        AMMUNITION(13),
        AURA(14),
        POCKET(17);

        private final int index;

        Slot(int index) {
            this.index = index;
        }

        private static final Slot[] SLOTS = values();

        public static Slot resolve(int index) {
            return Arrays.stream(SLOTS).filter(slot -> slot.index == index).findFirst().orElse(null);
        }

        public final int getIndex() {
            return this.index;
        }
    }

}
