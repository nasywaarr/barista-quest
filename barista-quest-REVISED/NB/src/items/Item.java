package items;

import model.Brewable;
import model.Character;

public abstract class Item implements Brewable {
    private final String name;
    private final String description;

    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName()        { return name; }
    public String getDescription() { return description; }

    @Override
    public String toString() { return name + ": " + description; }
}
