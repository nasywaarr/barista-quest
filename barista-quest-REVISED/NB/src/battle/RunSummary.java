package battle;

import java.util.ArrayList;
import java.util.List;

public class RunSummary {
    private final List<String> log;
    private int totalDamageDealt;
    private int totalHealingDone;
    private int cardsPlayed;
    private int enemiesDefeated;
    private int turnsPlayed;
    private int score;
    
    public RunSummary() {
        this.log = new ArrayList<>();
    }
    
    public void log(String entry) {
        log.add("[Turn " + turnsPlayed + "] " + entry);
    }
    
    public void recordTurn() { turnsPlayed++; }
    
    public void recordCardPlay() { cardsPlayed++; }
    
    public void addDamage(int dmg) { totalDamageDealt += dmg; }
    
    public void addHeal(int heal) { totalHealingDone += heal; }
    
    public void recordKill() { enemiesDefeated++; }
    
    public void addScore(int points) { score += points; }
    
    private int totalBeans;
    
    public void setBeans(int beans) { this.totalBeans = beans; }

    public void printReport() {
        System.out.println("\n----------------------------------------");
        System.out.println("              RUN SUMMARY");
        System.out.println("----------------------------------------");
        System.out.printf("  %-20s %d%n", "Score:",            score);
        System.out.printf("  %-20s %d%n", "Turns played:",     turnsPlayed);
        System.out.printf("  %-20s %d%n", "Cards played:",     cardsPlayed);
        System.out.printf("  %-20s %d%n", "Enemies defeated:", enemiesDefeated);
        System.out.printf("  %-20s %d%n", "Damage dealt:",     totalDamageDealt);
        System.out.printf("  %-20s %d%n", "Healing done:",     totalHealingDone);
        System.out.printf("  %-20s %d%n", "Beans collected:",  totalBeans);
        System.out.println("----------------------------------------");
        System.out.println("              BATTLE LOG");
        System.out.println("----------------------------------------");
        for (String entry : log) {
            System.out.println("  " + entry);
        }
        if (log.isEmpty()) {
            System.out.println("  (no battles recorded)");
        }
        System.out.println("----------------------------------------");
    }
}
