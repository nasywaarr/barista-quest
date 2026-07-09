package items;

import exceptions.OutOfStockException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Inventory<T extends Item> {
    private final List<T> items;
    private final int maxCapacity;

    public Inventory()                  { this(20); }
    public Inventory(int maxCapacity)   { this.items = new ArrayList<>(); this.maxCapacity = maxCapacity; }

    public void add(T item) throws OutOfStockException {
        if (items.size() >= maxCapacity)
            throw new OutOfStockException("Inventory full — cannot add " + item.getName());
        items.add(item);
    }

    public T get(String name) throws OutOfStockException {
        for (T item : items)
            if (item.getName().equalsIgnoreCase(name)) return item;
        throw new OutOfStockException("No item named \"" + name + "\" in inventory.");
    }

    public void remove(T item) throws OutOfStockException {
        if (!items.remove(item))
            throw new OutOfStockException(item.getName() + " not in inventory.");
    }

    public List<T> getAll() { return Collections.unmodifiableList(items); }
    public boolean isEmpty() { return items.isEmpty(); }
    public int size()        { return items.size(); }
}
