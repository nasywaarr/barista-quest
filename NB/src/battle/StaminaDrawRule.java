package battle;

import model.Barista;
import exceptions.EmptyDeckException;

public class StaminaDrawRule implements GameRule {

    private final int staminaThreshold;
    private final int cardsToDraw;
    private boolean triggered;

    public StaminaDrawRule(int staminaThreshold, int cardsToDraw) {
        this.staminaThreshold = staminaThreshold;
        this.cardsToDraw = cardsToDraw;
        this.triggered = false;
    }

    @Override
    public boolean appliesTo(GameEvent event) {
        return !triggered && event.getPlayer().getStamina() < staminaThreshold;
    }

    @Override
    public void execute(GameEvent event) {
        if (!(event.getPlayer() instanceof Barista)) return;
        Barista barista = (Barista) event.getPlayer();
        try {
            barista.getDeck().draw(cardsToDraw);
            System.out.println("  [DRAW] RULE TRIGGERED: Drew " + cardsToDraw
                + " bonus card(s) (low stamina)!");
        } catch (EmptyDeckException e) {
            System.out.println("  (Rule draw fizzled — deck empty)");
        }
        triggered = true;
    }

    @Override
    public String describe() {
        return "IF player.stamina < " + staminaThreshold
            + " THEN draw(" + cardsToDraw + ")";
    }
}
