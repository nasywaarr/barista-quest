package model;

import exceptions.BattleOverException;

public class Steamed extends StatusEffect {
    public Steamed(int turns) { super("Steamed", turns); }

    @Override
    public void tick(Character target) {
        System.out.println("  " + target.getName() + " is Steamed — takes 3 damage and loses 1 stamina!");
        try { target.takeDamage(3); }
        catch (BattleOverException e) { System.out.println("  " + e.getMessage()); }
        try { target.useStamina(1); }
        catch (exceptions.InvalidBrewException e) {  }
    }
}
