package world;

import battle.RuleSet;
import items.Ingredient;
import items.MokaPot;
import java.util.Arrays;
import model.RivalBarista;

public class SoloHeritage extends Region {
    public SoloHeritage() { super("Solo Heritage"); }

    @Override
    public void buildEncounterPool(RuleSet ruleSet) {
        int d = ruleSet.getEnemyScalingFactor();
        addEncounter(new BattleEncounter(new RivalBarista("Solo Rival", d)));
        addEncounter(new RandomEvent("A gamelan musician plays you an energizing song!", 10));
        shuffle();
        addEncounter(new ShopEncounter(
            Arrays.asList(
                new MokaPot(5),
                new Ingredient(Ingredient.IngredientType.SIMPLE_SYRUP, 15)),
            new int[]{20, 15}));
    }
}
