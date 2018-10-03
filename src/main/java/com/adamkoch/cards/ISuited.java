package com.adamkoch.cards;

/**
 * Standard playing cards have Suits. Other types of cards don't have suits but factions instead.
 *
 * Named "ISuited" until I can remove and replace "Suit"
 *
 * @author Adam A. Koch (aakoch)
 */
public interface ISuited<T extends ISuit>
{
    T getSuit();
}
