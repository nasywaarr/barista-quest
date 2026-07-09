package battle;

public interface GameRule {
    boolean appliesTo(GameEvent event);
    
    void execute(GameEvent event);
    
    String describe();
}
