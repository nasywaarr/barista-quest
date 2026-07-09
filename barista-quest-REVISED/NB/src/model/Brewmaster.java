package model;

import exceptions.BattleOverException;
import java.util.Random;

public class Brewmaster extends Enemy {
    private int phase = 1;
    private static final Random RANDOM = new Random();

    public Brewmaster() { this(1); }

    public Brewmaster(int difficulty) {
        super("Brewmaster",
            100 + (difficulty - 1) * 30,
            10,
            10  + (difficulty - 1) * 3,
            100);
    }

    private void attack(Character target, String move, int damage) throws BattleOverException {
        System.out.println("  " + getName() + " " + move);
        System.out.println("  -> " + target.getName() + " takes " + damage + " damage!");
        target.takeDamage(damage);
    }

    @Override
    protected void decideAction(Character target) throws BattleOverException {
        if (phase == 1 && getHealth() <= getMaxHealth() / 2) {
            phase = 2;
            System.out.println("\n*** " + getName() + " ENRAGES! Phase 2! ***\n");
        }
        if (phase == 1) {
            switch (RANDOM.nextInt(3)) {
                case 0: attack(target, "throws hot coffee!", getAttackPower()); break;
                case 1: attack(target, "slams the counter hard!", getAttackPower() + 3); break;
                case 2:
                    attack(target, "blasts hot steam!", getAttackPower() - 4);
                    target.addStatusEffect(new Steamed(2));
                    break;
            }
        } else {
            switch (RANDOM.nextInt(2)) {
                case 0:
                    attack(target, "explodes with burning rage!", getAttackPower() + 5);
                    target.addStatusEffect(new Burned(3, 2));
                    break;
                case 1:
                    attack(target, "releases a giant steam burst!", getAttackPower());
                    target.addStatusEffect(new Steamed(2));
                    target.addStatusEffect(new Jittery(1));
                    break;
            }
        }
    }
}
