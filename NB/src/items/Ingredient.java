package items;

import model.Character;

public class Ingredient extends Item {
    public enum IngredientType { OAT_MILK, ESPRESSO_SHOT, SIMPLE_SYRUP }

    private final IngredientType ingredientType;
    private final int effectValue;

    public Ingredient(IngredientType type, int effectValue) {
        super(displayName(type), describeEffect(type, effectValue));
        this.ingredientType = type;
        this.effectValue = effectValue;
    }

    @Override
    public void brew(Character target) {
        switch (ingredientType) {
            case OAT_MILK:      target.heal(effectValue); break;
            case ESPRESSO_SHOT: target.restoreStamina(effectValue); break;
            case SIMPLE_SYRUP:  target.heal(effectValue); target.restoreStamina(effectValue/2); break;
        }
        System.out.println("  " + target.getName() + " uses " + getName() + "!");
    }

    private static String displayName(IngredientType t) {
        switch (t) {
            case OAT_MILK:      return "OatMilk";
            case ESPRESSO_SHOT: return "EspressoShot";
            case SIMPLE_SYRUP:  return "SimpleSyrup";
            default: return t.name();
        }
    }

    private static String describeEffect(IngredientType t, int v) {
        switch (t) {
            case OAT_MILK:      return "Restores " + v + " HP.";
            case ESPRESSO_SHOT: return "Restores " + v + " stamina.";
            case SIMPLE_SYRUP:  return "Restores " + v + " HP and " + (v/2) + " stamina.";
            default: return "";
        }
    }

    public IngredientType getIngredientType() { return ingredientType; }
}
