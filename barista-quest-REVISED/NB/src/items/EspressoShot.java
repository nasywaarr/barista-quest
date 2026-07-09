package items;

/**
 * EspressoShot — a named Ingredient subtype that restores stamina.
 * Matches the proposal's item hierarchy: Ingredient -> (OatMilk, EspressoShot).
 */
public class EspressoShot extends Ingredient {
    public EspressoShot(int staminaAmount) {
        super(IngredientType.ESPRESSO_SHOT, staminaAmount);
    }
}
