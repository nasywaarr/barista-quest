package world;

import battle.BattleEngine;
import exceptions.*;
import model.Barista;
import model.Enemy;

public class BattleEncounter extends Encounter {
    private final Enemy enemy;

    public BattleEncounter(Enemy enemy) { this.enemy = enemy; }

    @Override
    public void resolve(Barista barista, BattleEngine engine)
        throws EmptyDeckException, InvalidBrewException, BattleOverException,
               OutOfStockException, InvalidRuleException {
        System.out.println("\nA wild " + enemy.getName() + " appears!");
        engine.runBattle(barista, enemy);
    }

    @Override
    public String getLabel() { return "Battle: " + enemy.getName(); }
}
