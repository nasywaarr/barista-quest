package world;

import battle.RuleSet;
import items.Ingredient;
import items.OatMilk;
import java.util.Arrays;
import model.AngryCustomer;

public class WarungJogja extends Region {
    public WarungJogja() { super("Warung Jogja"); }

    @Override
    public void buildEncounterPool(RuleSet ruleSet) {
        int d = ruleSet.getEnemyScalingFactor();
        addEncounter(new BattleEncounter(new AngryCustomer(d)));
        addEncounter(new RandomEvent("A friendly local shares their coffee with you!", 10));
        shuffle();
        addEncounter(new ShopEncounter(
            Arrays.asList(
                new OatMilk(10),
                new Ingredient(Ingredient.IngredientType.SIMPLE_SYRUP, 15)),
            new int[]{10, 15}));
    }
}
