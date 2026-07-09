package battle;

import model.Character;
import model.Barista;
import exceptions.InvalidBrewException;
import exceptions.BattleOverException;
import exceptions.EmptyDeckException;

public class AttackCard extends CoffeeCard {
    // 3-step information hiding: (1) private access, (2) instance member, (3) constant
    private final int damage;
    private final boolean drawOnPlay;

    public AttackCard(String name, int staminaCost, int damage) {
        this(name, staminaCost, damage, false);
    }

    public AttackCard(String name, int staminaCost, int damage, boolean drawOnPlay) {
        super(name, staminaCost, CardType.ATTACK);
        this.damage = damage;
        this.drawOnPlay = drawOnPlay;
    }

    /** Read-only access — external code can query damage but never modify it. */
    public int getDamage() { return damage; }

    @Override
    public String describe() {
        String extra = drawOnPlay ? ", draw 1" : "";
        return "[" + getType() + "] " + getName()
            + " (cost: " + getStaminaCost() + " stamina, " + damage + " dmg" + extra + ")";
    }

    @Override
    public void use(Character user, Character target)
            throws InvalidBrewException, BattleOverException {
        user.useStamina(this.getStaminaCost());
        int totalDamage = this.damage;
        int equipmentBonus = 0;
        int comboBonus = 0;
        if (user instanceof Barista) {
            equipmentBonus = ((Barista) user).getEquipmentBonus();
            if (equipmentBonus > 0) totalDamage += equipmentBonus;
            comboBonus = ((Barista) user).consumeComboBonus();
            if (comboBonus > 0) totalDamage += comboBonus;
        }
        target.takeDamage(totalDamage);
        String extraNote = "";
        if (equipmentBonus > 0) extraNote += " (+" + equipmentBonus + " equipment)";
        if (comboBonus > 0)     extraNote += " (+" + comboBonus + " combo)";
        System.out.println("  " + user.getName() + " plays " + this.getName()
            + " -> " + target.getName() + " takes " + totalDamage + " damage!" + extraNote);

        if (drawOnPlay && user instanceof Barista) {
            try {
                ((Barista) user).getDeck().draw();
                System.out.println("  (Drew 1 card from Cafe Creme bonus!)");
            } catch (EmptyDeckException e) {
            }
        }
    }
}
