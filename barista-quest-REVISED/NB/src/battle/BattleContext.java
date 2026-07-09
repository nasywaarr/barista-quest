package battle;

import model.Character;

public class BattleContext implements GameEvent {
    private final Character player;
    private final Character enemy;
    private final int currentTurn;
    private int cardsPlayedThisTurn;
    
    public BattleContext(Character player, Character enemy, int turn) {
        this(player, enemy, turn, 0);
    }

    public BattleContext(Character player, Character enemy, int turn, int comboStreak) {
        this.player = player;
        this.enemy = enemy;
        this.currentTurn = turn;
        this.cardsPlayedThisTurn = comboStreak;
    }
    
    @Override
    public Character getPlayer() { return player; }
    
    @Override
    public Character getEnemy() { return enemy; }
    
    @Override
    public int getCurrentTurn() { return currentTurn; }
    
    @Override
    public int getCardsPlayedThisTurn() { return cardsPlayedThisTurn; }
    
    public void incrementCardsPlayed() { cardsPlayedThisTurn++; }
    
    public void resetCardsPlayed() { cardsPlayedThisTurn = 0; }
}
