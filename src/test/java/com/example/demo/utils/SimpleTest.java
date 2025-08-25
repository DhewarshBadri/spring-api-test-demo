package com.example.demo.utils;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * A very simple test class that doesn't depend on Spring Boot context.
 * This helps identify if the issue is with TestNG setup or with Spring Boot.
 */
public class SimpleTest {
    
    @Test
    public void testSimpleAddition() {
        int result = 2 + 2;
        System.out.println("Running simple test: 2 + 2 = " + result);
        Assert.assertEquals(result, 4, "Simple addition test");
    }
    
    @Test
    public void testSimpleString() {
        String test = "Hello TestNG";
        System.out.println("Running simple string test with: " + test);
        Assert.assertTrue(test.contains("TestNG"), "String should contain TestNG");
    }
}
