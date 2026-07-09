package world;

import battle.BattleEngine;
import battle.RuleSet;
import exceptions.*;
import model.Barista;
import java.util.ArrayList;
import java.util.List;

public class WorldManager {
    private final List<Region> regions;
    private final BattleEngine engine;

    public WorldManager(RuleSet ruleSet, BattleEngine engine) {
        this.engine = engine;
        this.regions = new ArrayList<>();
    }

    public void buildWorld(battle.RuleSet ruleSet) {
        Region r1 = new WarungJogja();
        Region r2 = new SoloHeritage();
        Region r3 = new DiengHighlands();
        r1.buildEncounterPool(ruleSet);
        r2.buildEncounterPool(ruleSet);
        r3.buildEncounterPool(ruleSet);
        regions.add(r1);
        regions.add(r2);
        regions.add(r3);
    }

    public void run(Barista barista)
        throws EmptyDeckException, InvalidBrewException, BattleOverException,
               OutOfStockException, InvalidRuleException {
        int region3BattleNum = 0;
        int region3TotalBattles = 3;
        for (int i = 0; i < regions.size(); i++) {
            Region region = regions.get(i);
            System.out.println("\n\n----------------------------------------");
            System.out.println("  Region " + (i + 1) + " of " + regions.size() + ": " + region.getName());
            System.out.println("----------------------------------------");
            boolean isRegion3 = (i == 2);
            while (region.hasEncounters()) {
                Encounter enc = region.nextEncounter();
                if (isRegion3 && enc instanceof BattleEncounter) {
                    region3BattleNum++;
                    System.out.println("\n[ Battle " + region3BattleNum + " of " + region3TotalBattles
                        + ": " + enc.getLabel().replace("Battle: ", "") + " ]");
                } else {
                    System.out.println("\n[ " + enc.getLabel() + " ]");
                }
                enc.resolve(barista, engine);
                if (!barista.isAlive()) return;
            }
        }
    }
}
