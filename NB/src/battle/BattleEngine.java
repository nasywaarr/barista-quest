package battle;

import model.Character;
import model.Barista;
import model.Enemy;
import exceptions.*;
import java.util.List;
import java.util.Scanner;

public class BattleEngine {
    private final RuleSet ruleSet;
    private final RunSummary summary;
    private int comboCounter;

    private static final Scanner SC = new Scanner(System.in);

    public BattleEngine(RuleSet ruleSet, RunSummary summary) {
        this.ruleSet = ruleSet;
        this.summary = summary;
        this.comboCounter = 0;
    }

    public void runBattle(Barista barista, Enemy enemy)
            throws EmptyDeckException, InvalidBrewException,
                   OutOfStockException, BattleOverException, InvalidRuleException {

        System.out.println("\n----------------------------------------");
        System.out.println("  BATTLE START: " + barista.getName() + " vs " + enemy.getName());
        System.out.println("----------------------------------------\n");

        comboCounter = 0;

        java.util.List<CoffeeCard> leftover = new java.util.ArrayList<>(barista.getDeck().getHand());
        for (CoffeeCard c : leftover) {
            barista.getDeck().discard(c);
        }
        barista.getDeck().recycleDiscard();

        barista.restoreStamina(barista.getMaxStamina());

        barista.getDeck().shuffle();
        try {
            barista.getDeck().draw(ruleSet.getHandSize());
        } catch (EmptyDeckException e) {
            System.out.println("Warning: Not enough cards in deck!");
        }

        int turnCount = 0;

        while (barista.isAlive() && enemy.isAlive()) {
            turnCount++;
            summary.recordTurn();

            System.out.println("\n--- TURN " + turnCount + " ---");
            System.out.println(barista + " | " + enemy);

            boolean playerFumbled = false;
            for (model.StatusEffect se : barista.getStatusEffects()) {
                if (se instanceof model.Jittery && !se.isJustApplied()
                        && ((model.Jittery) se).didFumble()) {
                    playerFumbled = true;
                    break;
                }
            }

            BattleContext context = new BattleContext(barista, enemy, turnCount, comboCounter);
            applyCustomRules(context);

            if (playerFumbled) {
                System.out.println("  " + barista.getName() + " is Jittery — fumbles and loses their turn!");
                comboCounter = 0;
            } else {
                try {
                    playerTurn(barista, enemy);
                } catch (BattleOverException e) {
                    System.out.println("  " + e.getMessage());
                    break;
                }
            }

            if (!enemy.isAlive()) break;

            System.out.println("\n" + enemy.getName() + "'s turn:");

            boolean enemyFumbled = false;
            for (model.StatusEffect se : enemy.getStatusEffects()) {
                if (se instanceof model.Jittery && !se.isJustApplied()
                        && ((model.Jittery) se).didFumble()) {
                    enemyFumbled = true;
                    break;
                }
            }

            if (!enemyFumbled) {
                try {
                    enemy.act(barista);
                } catch (BattleOverException e) {
                    System.out.println("  " + e.getMessage());
                    break;
                }
            } else {
                System.out.println("  " + enemy.getName() + " is too jittery to act!");
            }

            if (!barista.isAlive()) break;

            barista.tickStatusEffects();
            enemy.tickStatusEffects();

            ruleSet.tick();

            barista.restoreStamina(1);
            enemy.restoreStamina(1);

            System.out.print("\nPress Enter to continue...");
            SC.nextLine();
        }

        resolveBattleEnd(barista, enemy);
    }

    private void applyCustomRules(BattleContext context) {
        List<GameRule> rules = ruleSet.getRules();
        for (GameRule rule : rules) {
            if (rule.appliesTo(context)) {
                rule.execute(context);
            }
        }
    }

    private void playerTurn(Barista barista, Enemy enemy)
            throws EmptyDeckException, InvalidBrewException, BattleOverException, InvalidRuleException {

        CoffeeDeck<CoffeeCard> deck = barista.getDeck();
        List<CoffeeCard> hand = deck.getHand();

        if (hand.isEmpty()) {
            deck.recycleDiscard();
            try {
                deck.draw(ruleSet.getHandSize());
            } catch (EmptyDeckException e) {
                System.out.println("No cards available — skipping turn.");
                return;
            }
            hand = deck.getHand();
        }

        if (hand.size() < ruleSet.getHandSize()) {
            try {
                deck.draw();
                hand = deck.getHand();
            } catch (EmptyDeckException e) {
            }
        }

        System.out.println("\n" + barista.getName() + "'s turn (Stamina: "
            + barista.getStamina() + "/" + barista.getMaxStamina() + "):");
        System.out.println("Hand (" + hand.size() + " cards):");
        boolean anyPlayable = false;
        for (int i = 0; i < hand.size(); i++) {
            CoffeeCard card = hand.get(i);
            boolean banned    = !ruleSet.isCardAllowed(card.getType());
            boolean tooExpensive = card.getStaminaCost() > barista.getStamina();
            String status;
            if (banned)            status = " [BANNED]";
            else if (tooExpensive) status = " [need " + card.getStaminaCost() + " ST]";
            else                   { status = ""; anyPlayable = true; }
            System.out.println("  [" + (i + 1) + "] " + card.describe() + "  " + status);
        }

        if (!anyPlayable) {
            System.out.println("  No playable cards (not enough stamina) — skipping turn.");
            comboCounter = 0;
            return;
        }

        System.out.println("  [0] Pass turn (skip)");
        System.out.println("Choose card (1-" + hand.size() + ") or 0 to pass: ");

        CoffeeCard chosen = null;
        while (chosen == null) {
            System.out.print("> ");
            String line = SC.nextLine().trim();
            int choice;
            try {
                choice = Integer.parseInt(line) - 1;
            } catch (NumberFormatException e) {
                System.out.println("  Please enter a number.");
                continue;
            }

            if (choice == -1) {
                System.out.println("  Nasywa passes this turn.");
                comboCounter = 0;
                return;
            }

            if (choice < 0 || choice >= hand.size()) {
                System.out.println("  Invalid choice — pick 1 to " + hand.size() + ".");
                continue;
            }

            CoffeeCard candidate = hand.get(choice);
            if (!ruleSet.isCardAllowed(candidate.getType())) {
                System.out.println("  X " + candidate.getType() + " cards are banned this turn!");
                continue;
            }
            if (candidate.getStaminaCost() > barista.getStamina()) {
                System.out.println("  X Not enough stamina for that card ("
                    + candidate.getStaminaCost() + " needed, "
                    + barista.getStamina() + " available).");
                continue;
            }

            chosen = candidate;
        }

        comboCounter++;
        if (comboCounter >= 2) {
            System.out.println("  COMBO x" + comboCounter + "!");
        }

        try {
            chosen.use(barista, enemy);
            deck.discard(chosen);
            summary.addDamage(chosen instanceof AttackCard ?
                              ((AttackCard) chosen).getDamage() : 0);
            summary.addHeal(chosen instanceof HealCard ?
                            ((HealCard) chosen).getHealAmount() : 0);
            summary.recordCardPlay();
        } catch (InvalidBrewException | OutOfStockException | InvalidRuleException e) {
            System.out.println("  X " + e.getMessage() + " Choose a different card.");
            comboCounter--;
            playerTurn(barista, enemy);
        }
    }

    private void resolveBattleEnd(Barista barista, Enemy enemy) {
        if (barista.isAlive()) {
            summary.recordKill();
            summary.addScore(enemy.getReward());
            summary.log(barista.getName() + " defeated " + enemy.getName() + ".");
            barista.earnBeans(enemy.getReward());
        } else {
            summary.log(barista.getName() + " was defeated by " + enemy.getName() + ".");
        }
    }
}
