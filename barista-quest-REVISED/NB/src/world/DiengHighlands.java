package world;

import battle.RuleSet;
import items.Ingredient;
import items.Ingredient.IngredientType;
import items.OatMilk;
import items.MokaPot;
import model.Brewmaster;
import model.CoffeeCritic;
import model.HealthInspector;
import java.util.Arrays;

public class DiengHighlands extends Region {
    public DiengHighlands() { super("Dieng Highlands"); }

    @Override
    public void buildEncounterPool(RuleSet ruleSet) {
        int d = ruleSet.getEnemyScalingFactor();
        addEncounter(new BattleEncounter(new HealthInspector(ruleSet, d)));
        addEncounter(new BattleEncounter(new CoffeeCritic(ruleSet, d)));
        addEncounter(new RandomEvent("The volcano's warmth restores your energy!", 0, 15, 5));
        addEncounter(new ShopEncounter(
            Arrays.asList(
                new OatMilk(10),
                new Ingredient(IngredientType.SIMPLE_SYRUP, 15),
                new MokaPot(5)),
            new int[]{10, 15, 20}));
        addEncounter(new BattleEncounter(new Brewmaster(d)));
    }
}
