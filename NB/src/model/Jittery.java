package model;

import java.util.Random;

public class Jittery extends StatusEffect {
    private boolean fumbled;

    public Jittery(int turns) { super("Jittery", turns); }

    @Override
    public void tick(Character target) {
        fumbled = new Random().nextDouble() < 0.50;
    }

    public boolean didFumble()     { return fumbled; }
    public boolean isJustApplied() { return super.isJustApplied(); }
}
