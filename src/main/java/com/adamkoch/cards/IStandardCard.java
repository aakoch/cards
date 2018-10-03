package com.adamkoch.cards;

/**
 * A StandardCard is a playing card of the type played in poker. It has a Suit and a Rank, although the Rank is not
 * the same for every card game.
 *
 * The rank depends on the game so I'm not sure we can have IStandardCard extend IPointed.
 *
 * @author Adam A. Koch (aakoch)
 * @since 1.0.0
 */
public interface IStandardCard extends ICard, ISuited, IPointed
{
}
