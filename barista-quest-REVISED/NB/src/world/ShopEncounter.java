package world;

import battle.BattleEngine;
import exceptions.*;
import items.Item;
import model.Barista;
import java.util.List;
import java.util.Scanner;

public class ShopEncounter extends Encounter {
    private final List<Item> stock;
    private final int[] prices;
    private static final Scanner SC = new Scanner(System.in);

    public ShopEncounter(List<Item> stock, int[] prices) {
        this.stock = stock;
        this.prices = prices;
    }

    @Override
    public void resolve(Barista barista, BattleEngine engine)
        throws EmptyDeckException, InvalidBrewException, BattleOverException,
               OutOfStockException, InvalidRuleException {
        System.out.println("\nSHOP (Beans: " + barista.getBeans() + ")");
        System.out.println("  HP: " + barista.getHealth() + "/" + barista.getMaxHealth()
            + "  |  ST: " + barista.getStamina() + "/" + barista.getMaxStamina());
        System.out.println();
        for (int i = 0; i < stock.size(); i++)
            System.out.println("  [" + (i+1) + "] " + stock.get(i).getName()
                + " - " + prices[i] + " beans  (" + stock.get(i).getDescription() + ")");
        System.out.println("  [0] Leave");

        while (true) {
            System.out.print("Buy: ");
            int c;
            try { c = Integer.parseInt(SC.nextLine().trim()); }
            catch (NumberFormatException e) { continue; }
            if (c == 0) break;
            if (c >= 1 && c <= stock.size()) {
                if (barista.spendBeans(prices[c-1])) {
                    Item bought = stock.get(c-1);
                    barista.getInventory().add(bought);
                    bought.brew(barista);
                    System.out.println("  Bought and used " + bought.getName() + "!");
                    System.out.println("  +-- Status ------------------------------------");
                    System.out.println("  | Beans: " + barista.getBeans());
                    System.out.println("  | HP:    " + barista.getHealth() + "/" + barista.getMaxHealth());
                    System.out.println("  | ST:    " + barista.getStamina() + "/" + barista.getMaxStamina());
                    System.out.println("  +----------------------------------------------");
                } else {
                    System.out.println("  Not enough beans! (Have: " + barista.getBeans() + ")");
                }
            }
        }
    }

    @Override
    public String getLabel() { return "Shop"; }
}
