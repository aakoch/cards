package com.adamkoch.cards;

/**
 * Since some games don't assign value until trump is determined, I'm not sure if this will work yet. We might need
 * to deal out cards that don't yet have a rank but then add a rank later.
 * 
 * @author Adam A. Koch (aakoch)
 * @since 1.0.0
 */
public class TrumpDependentCard implements IStandardCard, ITrumpListener<ISuit>, IRanked<TrumpDependentCard>
{

    private final Suit suit;
    private Integer value;
    private boolean trump;

    public TrumpDependentCard(Suit suit)
    {
        this.suit = suit;
    }

    @Override
    public int getValue()
    {
        if (value == null) {
            throw new ValueNotSetException();
        }
        if (trump) {
            // ?
        }
        return value;
    }

    @Override
    public ISuit getSuit()
    {
        return suit;
    }

    @Override
    public void onTrumpSelected(ISuit trumpSuit) {
        // this is where we would set the value of the card based on what trump was selected.
        // value = ?
        if (this.suit == trumpSuit) {
            trump = true;
        }
    }

    @Override
    public int compareTo(TrumpDependentCard card) {
        if (trump && !card.isTrump()) {
            return 1;
        }
        if (!trump && card.isTrump()) {
            return -1;
        }
        return getValue() - card.getValue();
    }

    public boolean isTrump() {
        return trump;
    }

    public void setTrump(boolean trump) {
        this.trump = trump;
    }
}