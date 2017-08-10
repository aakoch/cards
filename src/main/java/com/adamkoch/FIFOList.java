package com.adamkoch;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Created by aakoch on 2017-07-31.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class FIFOList<T> {
    private List<T> list;

    public FIFOList() {
        list = new ArrayList();
    }

    public FIFOList(List<T> newList) {
        list = new ArrayList<>(newList);
    }

    public void add(T object) {
        list.add(object);
    }

    public T pop() {
        return list.remove(list.size() - 1);
    }

    public List<T> allButLast() {
        final List<T> subList = list.subList(0, list.size() - 1);
        List<T> newList = new ArrayList<>(subList);
        subList.clear();
        return newList;
    }

    public T peek() {
        return list.get(list.size() - 1);
    }
}
