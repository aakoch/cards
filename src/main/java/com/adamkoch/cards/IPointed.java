package com.adamkoch.cards;

/**
 * Some games have an absolute rank, such as 31, where each card has a point value. Other games are relational such as
 * Euchre where, based on trump, they cards have a different value.
 *
 * @author Adam A. Koch (aakoch)
 * @since 1.0.0
 */
public interface IPointed
{
    int getValue();
}
