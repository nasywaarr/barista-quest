package battle;

public class ComboRule implements GameRule {
    private final int comboThreshold;
    private final double damageMultiplier;
    
    public ComboRule(int threshold, double multiplier) {
        this.comboThreshold = threshold;
        this.damageMultiplier = multiplier;
    }
    
    @Override
    public boolean appliesTo(GameEvent event) {
        BattleContext context = (BattleContext) event;
        return context.getCardsPlayedThisTurn() >= comboThreshold;
    }
    
    @Override
    public void execute(GameEvent event) {
        BattleContext context = (BattleContext) event;
        int flatBonus = (int) Math.round((damageMultiplier - 1.0) * 100);
        System.out.println("  COMBO! " + (int)(damageMultiplier * 100 - 100)
            + "% damage boost active — next attack deals +" + flatBonus + " damage!");
        if (context.getPlayer() instanceof model.Barista) {
            ((model.Barista) context.getPlayer()).setComboBonus(flatBonus);
        }
    }
    
    @Override
    public String describe() {
        return "Combo Bonus: " + comboThreshold + "+ cards = " + (int)(damageMultiplier * 100) + "% boost";
    }
}
