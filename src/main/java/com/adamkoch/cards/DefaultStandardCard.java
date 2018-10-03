package com.adamkoch.cards;

/**
 * Since some games don't assign value until trump is determined, I'm not sure if this will work yet. We might need
 * to deal out cards that don't yet have a rank but then add a rank later.
 * 
 * @author Adam A. Koch (aakoch)
 * @since 1.0.0
 */
public class DefaultStandardCard implements IStandardCard
{

    private final Suit suit;
    private Integer value;

    public DefaultStandardCard(Suit suit, int value)
    {
        this.suit = suit;
        this.value = Integer.valueOf(value);
    }

    @Override
    public int getValue()
    {
        if (value == null) {
            throw new ValueNotSetException();
        }
        return value;
    }

    @Override
    public ISuit getSuit()
    {
        return suit;
    }

}