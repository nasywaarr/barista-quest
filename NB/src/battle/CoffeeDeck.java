package battle;

import exceptions.EmptyDeckException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CoffeeDeck<T extends CoffeeCard> {
    private final List<T> cards;
    private final List<T> hand;
    private final List<T> discard;
    
    public CoffeeDeck() {
        this.cards = new ArrayList<>();
        this.hand = new ArrayList<>();
        this.discard = new ArrayList<>();
    }
    
    public void addCard(T card) { 
        cards.add(card); 
    }
    
    public void shuffle() { 
        Collections.shuffle(cards); 
    }
    
    public T draw() throws EmptyDeckException {
        if (cards.isEmpty()) {
            throw new EmptyDeckException("Deck is empty! Recycling discard pile...");
        }
        T drawn = cards.remove(0);
        hand.add(drawn);
        return drawn;
    }
    
    public List<T> draw(int n) throws EmptyDeckException {
        List<T> drawn = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            drawn.add(draw());
        }
        return drawn;
    }
    
    public List<T> draw(CardType filter) throws EmptyDeckException {
        List<T> result = new ArrayList<>();
        while (!cards.isEmpty()) {
            T card = cards.remove(0);
            hand.add(card);
            result.add(card);
            if (card.getType() == filter) break;
        }
        if (result.isEmpty()) {
            throw new EmptyDeckException("No cards of type " + filter + " found!");
        }
        return result;
    }
    
    public void discard(T card) {
        hand.remove(card);
        discard.add(card);
    }
    
    public void recycleDiscard() {
        if (discard.isEmpty()) return;
        cards.addAll(discard);
        discard.clear();
        Collections.shuffle(cards);
        System.out.println("(Deck reshuffled — cards cycling again)");
    }
    
    public List<T> getHand() { 
         return Collections.unmodifiableList(hand); 
}
    
    public boolean isEmpty() { 
        return cards.isEmpty(); 
    }
    
    public int size() { 
        return cards.size(); 
    }
}
