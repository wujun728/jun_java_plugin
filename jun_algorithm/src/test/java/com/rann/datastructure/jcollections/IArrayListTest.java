package com.rann.datastructure.jcollections;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.*;

/**
 * Created by hztaoran on 2016/7/18.
 */
public class IArrayListTest {

    IArrayList<Integer> ilist = null;

    @Before
    public void setUp() throws Exception {
        ilist = new IArrayList<>();
    }

    @Test
    public void logic() {
        ilist.add(1);
        assertThat(ilist.size(), is(1));
        ilist.add(2);
        ilist.add(3);
    }

    @After
    public void tearDown() throws Exception {
        ilist.clear();
        ilist = null;
    }

}