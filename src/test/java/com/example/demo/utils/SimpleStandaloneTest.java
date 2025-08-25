package com.example.demo.utils;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * A simplified standalone test class for GitHub Actions.
 * This class doesn't rely on any Spring Boot context or other dependencies.
 */
public class SimpleStandaloneTest {
    
    @Test
    public void testBasicAddition() {
        int result = 2 + 2;
        System.out.println("Running basic addition test: 2 + 2 = " + result);
        Assert.assertEquals(result, 4, "Basic addition test");
    }
    
    @Test
    public void testBasicString() {
        String test = "TestNG is working";
        System.out.println("Running basic string test with: " + test);
        Assert.assertTrue(test.contains("TestNG"), "String should contain TestNG");
    }
}
