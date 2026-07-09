package items;

import model.Character;

public class Collectible extends Item {
    private final int scoreValue;

    public Collectible(String name, int scoreValue) {
        super(name, "Worth " + scoreValue + " score points.");
        this.scoreValue = scoreValue;
    }

    @Override
    public void brew(Character target) {
        System.out.println("  " + getName() + " is a trophy — nothing happens.");
    }

    public int getScoreValue() { return scoreValue; }
}
