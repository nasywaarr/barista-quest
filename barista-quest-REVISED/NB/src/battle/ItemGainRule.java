package battle;

import items.Ingredient;
import items.Ingredient.IngredientType;
import model.Barista;
import exceptions.OutOfStockException;

public class ItemGainRule implements GameRule {

    private final int healthThreshold;
    private final String itemName;
    private boolean triggered;

    public ItemGainRule(int healthThreshold, String itemName) {
        this.healthThreshold = healthThreshold;
        this.itemName = itemName;
    }

    @Override
    public boolean appliesTo(GameEvent event) {
        return !triggered && event.getPlayer().getHealth() < healthThreshold;
    }

    @Override
    public void execute(GameEvent event) {
        if (!(event.getPlayer() instanceof Barista)) return;
        Barista barista = (Barista) event.getPlayer();

        IngredientType type = parseItemName(itemName.toLowerCase().replace("\"", "").trim());
        if (type == null) {
            System.out.println("  Unknown item \"" + itemName + "\" — rule skipped.");
            triggered = true;
            return;
        }

        Ingredient gift = new Ingredient(type, 10);
        try {
            barista.getInventory().add(gift);
            System.out.println("  Rule: " + barista.getName() + " receives " + gift.getName() + "!");
            gift.brew(barista);
        } catch (OutOfStockException e) {
            System.out.println("  Inventory full — " + gift.getName() + " lost.");
        }
        triggered = true;
    }

    private IngredientType parseItemName(String name) {
        switch (name) {
            case "espressoshot":
            case "espresso_shot":
            case "espresso shot": return IngredientType.ESPRESSO_SHOT;
            case "oatmilk":
            case "oat_milk":
            case "oat milk":      return IngredientType.OAT_MILK;
            case "simplesyrup":
            case "simple_syrup":
            case "simple syrup":  return IngredientType.SIMPLE_SYRUP;
            default:              return null;
        }
    }

    @Override
    public String describe() {
        return "IF player.health < " + healthThreshold
            + " THEN player.gain(\"" + itemName + "\")";
    }
}
