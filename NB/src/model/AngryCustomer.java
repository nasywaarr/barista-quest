package model;

import exceptions.BattleOverException;
import java.util.Random;

public class AngryCustomer extends Enemy {
    private static final Random RANDOM = new Random();

    public AngryCustomer() { this(1); }

    public AngryCustomer(int difficulty) {
        super("Angry Customer",
            50 + (difficulty - 1) * 10,
            4,
            8  + (difficulty - 1) * 2,
            10);
    }

    @Override
    protected void decideAction(Character target) throws BattleOverException {
        switch (RANDOM.nextInt(3)) {
            case 0:
                System.out.println("  Angry Customer slams the table!");
                System.out.println("  -> " + target.getName() + " takes " + getAttackPower() + " damage!");
                target.takeDamage(getAttackPower());
                target.addStatusEffect(new Jittery(2));
                break;
            case 1:
                System.out.println("  Angry Customer demands a refund!");
                System.out.println("  -> " + target.getName() + " takes " + getAttackPower() + " damage!");
                target.takeDamage(getAttackPower());
                target.addStatusEffect(new Jittery(2));
                break;
            case 2:
                System.out.println("  Angry Customer spills hot coffee at you!");
                System.out.println("  -> " + target.getName() + " takes " + getAttackPower() + " damage!");
                target.takeDamage(getAttackPower());
                target.addStatusEffect(new Jittery(2));
                break;
        }
    }
}
