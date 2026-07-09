package world;

import battle.BattleEngine;
import exceptions.*;
import items.RareBean;
import model.Barista;

public class RandomEvent extends Encounter {
    private final String description;
    private final int beanReward;
    private final int healHP;
    private final int healStamina;

    public RandomEvent(String description, int beanReward) {
        this(description, beanReward, 0, 0);
    }

    public RandomEvent(String description, int beanReward, int healHP, int healStamina) {
        this.description = description;
        this.beanReward = beanReward;
        this.healHP = healHP;
        this.healStamina = healStamina;
    }

    @Override
    public void resolve(Barista barista, BattleEngine engine)
        throws EmptyDeckException, InvalidBrewException, BattleOverException,
               OutOfStockException, InvalidRuleException {
        System.out.println("\n" + description);
        if (beanReward > 0) {
            barista.earnBeans(beanReward);
            barista.getInventory().add(new RareBean(beanReward));
        }
        if (healHP > 0) {
            barista.heal(healHP);
            System.out.println("  " + barista.getName() + " restores " + healHP + " HP!");
        }
        if (healStamina > 0) {
            barista.restoreStamina(healStamina);
            System.out.println("  " + barista.getName() + " restores " + healStamina + " stamina!");
        }
    }

    @Override
    public String getLabel() { return "Event"; }
}
