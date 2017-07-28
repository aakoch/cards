package com.adamkoch.cards;

/**
 * <p>Created by aakoch on 2017-07-27.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class ShoveItPlayer extends Player {
    private final int betterThanRank;
    private int numberOfTimesGotWorseCard = 0;
    private int coins;

    public ShoveItPlayer(String name) {
        this(name, 8);
    }

    public ShoveItPlayer(String name, int betterThanRank) {
        super(name);
        this.betterThanRank = betterThanRank;
        coins = 3;
    }

    @Override
    public void setCard(Card card) {
        hand.clear();
        hand.add(card);
    }

    public void swapCard(Card card) {
        previousCard = getCard();
        if (card.getRank() < previousCard.getRank()) {
            numberOfTimesGotWorseCard++;
        }
        setCard(card);
        swapped = true;
    }

    public ShoveIt.Decision stayOrSwitch(int numberOfPreviousStays) {
        final Card card = getCard();
        if (wasSwapped() && previousCard().getRank() < card.getRank()) {
            return ShoveIt.Decision.STAY.withReason(" because swapped card was lower");
        }
        else if (card.getRank() == 13) {
            return ShoveIt.Decision.STAY.withReason("");
        }
        else {
            final int betterThanScore = betterThanRank + numberOfPreviousStays - numberOfTimesGotWorseCard;
            if (card.getRank() > betterThanScore) {
                String reason = null;
                return ShoveIt.Decision.STAY.withReason(reason);
            }
            else {
                if (card.getRank() > betterThanRank) {
                    System.out.println("numberOfPreviousStays = " + numberOfPreviousStays);
                    System.out.println("numberOfTimesGotWorseCard = " + numberOfTimesGotWorseCard);
                    System.out.println("betterThanScore = " + betterThanScore);
                }
                return ShoveIt.Decision.SWITCH;
            }
        }
    }

    @Override
    public String toString() {
        return "ShoveItPlayer{" +
                super.toString() +
                ", numberOfTimesGotWorseCard=" + numberOfTimesGotWorseCard +
                ", coins=" + coins +
                '}';
    }

    public void pay() {
        coins--;
        if (coins == 0) {
            System.out.println(getName() + " is on their honor");
        }
    }

    public int coins() {
        return coins;
    }
}
