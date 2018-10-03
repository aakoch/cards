package com.adamkoch.cards;

/**
 * A listener for a card which value changes based on the selected trump.
 *
 * @author Adam A. Koch (aakoch)
 * @since 1.0.0
 */
public interface ITrumpListener<T extends ISuit>
{
    void onTrumpSelected(T trump);
}
