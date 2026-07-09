package items;

/**
 * OatMilk — a named Ingredient subtype that restores HP.
 * Matches the proposal's item hierarchy: Ingredient -> (OatMilk, EspressoShot).
 */
public class OatMilk extends Ingredient {
    public OatMilk(int healAmount) {
        super(IngredientType.OAT_MILK, healAmount);
    }
}
