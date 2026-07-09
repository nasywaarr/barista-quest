package items;

import model.Character;

public class Equipment extends Item {
    private final int powerBonus;
    private boolean equipped;

    public Equipment(String name, int powerBonus) {
        super(name, "Gives +" + powerBonus + " power while equipped.");
        this.powerBonus = powerBonus;
    }

    @Override
    public void brew(Character target) {
        if (!equipped) { equipped = true; System.out.println("  " + getName() + " equipped!"); }
        else System.out.println("  " + getName() + " is already equipped.");
    }

    public int getPowerBonus()  { return powerBonus; }
    public boolean isEquipped() { return equipped; }
}
