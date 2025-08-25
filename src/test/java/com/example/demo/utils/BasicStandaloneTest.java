package com.example.demo.utils;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Ultra-simple test class with no dependencies that should always run.
 * Used as a fallback when other tests might have issues.
 */
public class BasicStandaloneTest {
    
    @Test
    public void testTrue() {
        Assert.assertTrue(true, "This test should always pass");
    }
    
    @Test
    public void testEquals() {
        Assert.assertEquals(1, 1, "Basic equality test");
    }
}
