package com.adamkoch.cards;

/**
 * A ranked card is a card that has a relative value such as a card in Euchre which depends on what is selected as
 * trump. Therefore, I'm thinking it has to be compared to another card.
 *
 * I'm not sure how yet to use this as we can't set a rank on a card until after the game has started.
 *
 * @see IPointed
 * @author Adam A. Koch (aakoch)
 * @since 1.0.0
 */
public interface IRanked<T extends ICard> extends Comparable<T>
{

}
