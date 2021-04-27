package blackjackpack;

import java.util.ArrayList;
import java.util.Random;

/**
 This is the Deck class, which defines the number of cards in a standard deck
 which is 4 sets of every value (1 set for each suit), and also includes
 the method for shuffling, burning (removing) a card, 
 */
public class Deck
{
    //The instance variable is an ArrayList of multiple cards, thus the name 'multipleCards' 
    private ArrayList<Card> deckCards;
    
    //Constructor method
    public Deck() {
        this.deckCards = new ArrayList<Card>(); 
        for (Suit cardSuit : Suit.values()) {
            for (Value cardValue : Value.values()) {                    //Adds a new card
                this.deckCards.add(new Card(cardSuit, cardValue));  	//Every new card that is added contains a value and
                                                                        //a suit
            }
        }
        shuffle();
    }
    
    //Method that shuffles the deck
    private void shuffle() {
        ArrayList<Card> tempDeck = new ArrayList<Card>();               //New ArrayList of a temporary deck of cards
        Random random = new Random();                                   //New Random called 'random'
        int randomCardIndex = 0;                                        //Random index of cards to be removed from deck
        int noOfCards = this.deckCards.size();                      //Defines the number of cards in the original deck
                                                                        //(which is fifty two, 13 * 4)
        //Syntax for generating a random number: random.nextInt((max - min) +1) + min;
        for (int i = 0; i < noOfCards; i++) {                           //Generates a random index
            randomCardIndex = random.nextInt((this.deckCards.size() //Defines what the random index number is
            -1 - 0) + 1) + 0;
            tempDeck.add(this.deckCards.get(randomCardIndex));      //Adds gen'd no. of cards to the temporary deck
            this.deckCards.remove(randomCardIndex);                 //Removes gen'd no. of cards from the temporary deck 
                                                                        //back into the original deck
        }
        this.deckCards = tempDeck;
    }
    
    //Loop that returns a list of all cards in the players hand on new lines
    public String toString() {
        String cardsInPlayerDeck = "";
        for (Card aCard : this.deckCards) {
            cardsInPlayerDeck += aCard.toString() + "\n";
        }
        return cardsInPlayerDeck;
    }
    
    //Removes 1 card from the shuffled deck before dealing
    public void burnCard(int i) {
        this.deckCards.remove(i);
    }
    
    
    public Card getCard(int i) {
        return this.deckCards.get(i);
    }
    
    
    public void addCard(Card addCard) {
        this.deckCards.add(addCard);
    }
    
    //Dealer draws cards from the deck
    public void draw(Deck comingFrom) {
        this.deckCards.add(comingFrom.getCard(0));
        comingFrom.burnCard(0);
    }

    public Card draw() {
        return deckCards.remove(0);
    }
    //Returns the size of the deck
    public int size() {
        return this.deckCards.size();
    }
    
    //Returns the total value of the cards in both the players' and (eventually) dealers' hands
    public int cardValue() {
        int totalValue = 0;
        int aceCard = 0;
        
        //Defines the numerical value of each card, with conditions for the Ace, since it can be 1 or 11
        for (Card aCard : this.deckCards) {
            switch (aCard.getValue()){
                case ACE: aceCard += 1; break;
                default : totalValue += aCard.getValue().getValue();
            }
        }
        
        //Simple for loop to specify when the Ace should have a value of 1 or 11
        for (int i = 0; i < aceCard; i++) {
            if (totalValue > 10) {                   
                totalValue += 1;                                        //If the total value of the players hand is greater
                                                                        //than 10, the value of the Ace is 1
            } else {
                totalValue += 11;                                       //Otherwise, it is 11
            }
        }
        return totalValue;
    }
    
    //Moves both the players' and dealers' hands back into the deck post-round
    public void moveToDeck(Deck moveTo) {
        int thisDeckSize = this.deckCards.size();
        
        //puts cards into the 'moveTo' deck
        for (int i = 0; i < thisDeckSize; i++) {
            moveTo.addCard(this.getCard(i));
        }
        
        for (int i = 0; i < thisDeckSize; i++) {
            this.burnCard(0);
        }
    }
}
