package model;

import exceptions.BattleOverException;

public class Burned extends StatusEffect {
    private final int damagePerTurn;

    public Burned(int damagePerTurn, int turns) {
        super("Burned", turns);
        this.damagePerTurn = damagePerTurn;
    }

    @Override
    public void tick(Character target) {
        System.out.println("  " + target.getName() + " is Burned — takes " + damagePerTurn + " damage!");
        try { target.takeDamage(damagePerTurn); }
        catch (BattleOverException e) { System.out.println("  " + e.getMessage()); }
    }
}
