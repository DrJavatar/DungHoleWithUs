package net.botwithus;

import com.google.common.flogger.FluentLogger;
import net.botwithus.rs3.game.Item;
import net.botwithus.rs3.game.queries.builders.items.InventoryItemQuery;
import net.botwithus.rs3.game.queries.results.ResultSet;

import java.util.List;

public class InventoryContainer {
    private static final FluentLogger log = FluentLogger.forEnclosingClass();

    private final int id;

    private final InventoryItemQuery query;

    InventoryContainer(int id) {
        this.id = id;
        this.query = InventoryItemQuery.newQuery(this.id).mark();
    }

    InventoryContainer(int id, InventoryItemQuery additionalQuery) {
        this.id = id;
        this.query = InventoryItemQuery.newQuery(this.id).and(additionalQuery).mark();
    }

    /**
     * Get the id of the InventoryContainer
     * @return the id of the InventoryContainer
     */

    public int getId() {
        return id;
    }


    public boolean isEmpty() {
        ResultSet<Item> results = query.reset().results();
        if (results.isEmpty()) {
            return true;
        }
        return results.stream().allMatch(i -> i.getId() == -1);
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }

    /**
     * Check if the InventoryContainer contains a specific item
     * @param id the ids of the items to check for
     * @return true if the InventoryContainer contains the item
     */

    public boolean contains(int id) {
        return !this.query.reset().ids(id).results().isEmpty();
    }

    public boolean contains(String name, long amount) {
        return this.query.reset().name(name).results().stream().mapToInt(Item::getStackSize).sum() >= amount;
    }

    public boolean contains(int id, long amount) {
        return this.query.reset().ids(id).results().stream().mapToInt(Item::getStackSize).sum() >= amount;
    }

    public Item slot(int slot) {
        return this.query.reset().slots(slot).results().stream().findFirst().orElse(null);
    }

    /**
     * Check if the InventoryContainer contains a specific item
     * @param names the names of the items to check for
     * @return true if the InventoryContainer contains the item
     */

    public boolean contains(String... names) {
        return !this.query.reset().name(names).results().isEmpty();
    }

    public boolean isFull() {
        if(query.reset().results().isEmpty()) {
            return false;
        }
        return query.reset().ids(-1).results().isEmpty();
    }

    /**
     * Check if the InventoryContainer contains a specific category
     * @param category the category to check for
     * @return true if the InventoryContainer contains the category
     */

    public boolean containsCategory(int category) {
        return !this.query.reset().category(category).results().isEmpty();
    }

    public List<Item> getItems() {
        return query.reset().results().stream().toList();
    }

    /**
     * Create a new InventoryContainer
     * @param id the id of the container
     * @return a new InventoryContainer
     */

    public static InventoryContainer of(int id) {
        return new InventoryContainer(id);
    }

    /**
     * Create a new InventoryContainer with an additional query
     * @param id the id of the container
     * @param query the additional query
     * @return a new InventoryContainer with the additional query
     */

    public static InventoryContainer of(int id, InventoryItemQuery query) {
        return new InventoryContainer(id, query);
    }

}
