package com.adamkoch.utils;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * <p>Created by aakoch on 2017-08-06.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class ListUtilsTest {
    @Test
    public void testGetNext() throws Exception {
        List<String> list = Arrays.asList("a", "b", "c");
        assertEquals("b", ListUtils.getNext(list,"a"));
        assertEquals("c", ListUtils.getNext(list,"b"));
        assertEquals("a", ListUtils.getNext(list,"c"));
    }

}