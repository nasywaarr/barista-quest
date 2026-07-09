package battle;

import model.Barista;
import exceptions.EmptyDeckException;

public class PeriodicDrawRule implements GameRule {
    private final int interval;
    private final int cardsToGain;
    
    public PeriodicDrawRule(int interval, int cardsToGain) {
        this.interval = interval;
        this.cardsToGain = cardsToGain;
    }
    
    @Override
    public boolean appliesTo(GameEvent event) {
        BattleContext context = (BattleContext) event;
        return context.getCurrentTurn() > 0 && context.getCurrentTurn() % interval == 0;
    }
    
    @Override
    public void execute(GameEvent event) {
        BattleContext context = (BattleContext) event;
        if (context.getPlayer() instanceof Barista) {
            Barista barista = (Barista) context.getPlayer();
            try {
                if (barista.getDeck().isEmpty()) {
                    barista.getDeck().recycleDiscard();
                }
                for (int i = 0; i < cardsToGain; i++) {
                    barista.getDeck().draw();
                }
                System.out.println("  [DRAW] RULE BONUS: " + cardsToGain + " extra card(s) drawn!");
            } catch (EmptyDeckException e) {
                System.out.println("  (Rule draw skipped — no cards available)");
            }
        }
    }
    
    @Override
    public String describe() {
        return "Periodic Draw: Every " + interval + " turns, gain " + cardsToGain + " card(s)";
    }
}
