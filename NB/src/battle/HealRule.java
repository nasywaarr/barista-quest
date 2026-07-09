package battle;

public class HealRule implements GameRule {

    private final int healthThreshold;
    private final int healAmount;
    private boolean triggered;

    public HealRule(int healthThreshold, int healAmount) {
        this.healthThreshold = healthThreshold;
        this.healAmount = healAmount;
        this.triggered = false;
    }

    @Override
    public boolean appliesTo(GameEvent event) {
        return !triggered && event.getPlayer().getHealth() < healthThreshold;
    }

    @Override
    public void execute(GameEvent event) {
        event.getPlayer().heal(healAmount);
        triggered = true;
        System.out.println("  Rule triggered: " + event.getPlayer().getName()
            + " heals " + healAmount + " HP!");
    }

    @Override
    public String describe() {
        return "IF player.health < " + healthThreshold
            + " THEN player.heal(" + healAmount + ")";
    }
}
