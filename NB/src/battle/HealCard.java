package battle;

import model.Character;
import exceptions.EmptyDeckException;
import exceptions.InvalidBrewException;
import exceptions.OutOfStockException;
import exceptions.BattleOverException;
import exceptions.InvalidRuleException;

public class HealCard extends CoffeeCard {
    private final int healAmount;
    private final int staminaRestore;

    public HealCard(String name, int staminaCost, int healAmount) {
        this(name, staminaCost, healAmount, 0);
    }

    public HealCard(String name, int staminaCost, int healAmount, int staminaRestore) {
        super(name, staminaCost, CardType.HEAL);
        this.healAmount = healAmount;
        this.staminaRestore = staminaRestore;
    }

    @Override
    public void use(Character user, Character target)
        throws EmptyDeckException, InvalidBrewException,
               OutOfStockException, BattleOverException, InvalidRuleException {
        user.useStamina(this.getStaminaCost());
        if (healAmount > 0) {
            user.heal(this.healAmount);
            System.out.println("  " + user.getName() + " plays " + this.getName()
                + " -> Heals " + this.healAmount + " HP!");
        }
        if (staminaRestore > 0) {
            user.restoreStamina(this.staminaRestore);
            System.out.println("  " + user.getName() + " restores " + this.staminaRestore + " stamina!");
        }
    }

    public int getHealAmount() { return healAmount; }

    @Override
    public String describe() {
        String effect = "";
        if (healAmount > 0 && staminaRestore > 0)
            effect = "+" + healAmount + " HP, +" + staminaRestore + " ST";
        else if (healAmount > 0)
            effect = "+" + healAmount + " HP";
        else
            effect = "+" + staminaRestore + " ST";
        return "[" + getType() + "] " + getName()
            + " (cost: " + getStaminaCost() + " stamina, " + effect + ")";
    }
}
