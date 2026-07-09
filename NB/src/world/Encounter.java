package world;

import battle.BattleEngine;
import exceptions.*;
import model.Barista;

public abstract class Encounter {
    public abstract void resolve(Barista barista, BattleEngine engine)
        throws EmptyDeckException, InvalidBrewException, BattleOverException,
               OutOfStockException, InvalidRuleException;
    public abstract String getLabel();
}
