package com.example.demo.utils;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * An extremely minimal test class with no dependencies whatsoever.
 * This class should run in any environment regardless of configuration.
 */
public class MinimalTest {
    
    @Test
    public void testTrue() {
        System.out.println("Running minimal test");
        Assert.assertTrue(true);
    }
}
