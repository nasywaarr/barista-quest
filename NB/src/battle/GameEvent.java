package battle;

import model.Character;

public interface GameEvent {
    Character getPlayer();
    Character getEnemy();
    int getCurrentTurn();
    int getCardsPlayedThisTurn();
}
