package battle;

public class ConditionalBonusRule implements GameRule {
    private final int healthThreshold;
    private final int bonusStamina;
    private boolean triggered;
    
    public ConditionalBonusRule(int threshold, int bonus) {
        this.healthThreshold = threshold;
        this.bonusStamina = bonus;
        this.triggered = false;
    }
    
    @Override
    public boolean appliesTo(GameEvent event) {
        BattleContext context = (BattleContext) event;
        return context.getPlayer().getHealth() < healthThreshold && !triggered;
    }
    
    @Override
    public void execute(GameEvent event) {
        BattleContext context = (BattleContext) event;
        context.getPlayer().restoreStamina(bonusStamina);
        triggered = true;
        System.out.println("  Rule triggered: " + context.getPlayer().getName() + 
                          " gains " + bonusStamina + " emergency stamina!");
    }
    
    @Override
    public String describe() {
        return "Conditional Bonus: If HP < " + healthThreshold + ", gain " + bonusStamina + " stamina";
    }
}
