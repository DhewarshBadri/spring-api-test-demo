package com.example.demo.utils;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * This class contains very basic TestNG tests that should always run
 * regardless of the Spring context or other configuration issues.
 */
public class StandaloneTest {
    
    @BeforeClass
    public void setup() {
        Reporter.log("StandaloneTest setup executed", true);
        System.out.println("StandaloneTest setup completed");
    }
    
    @Test
    public void testAddition() {
        int result = 2 + 2;
        Reporter.log("Running addition test: 2 + 2 = " + result, true);
        Assert.assertEquals(result, 4, "Addition test");
    }
    
    @Test
    public void testSubtraction() {
        int result = 5 - 3;
        Reporter.log("Running subtraction test: 5 - 3 = " + result, true);
        Assert.assertEquals(result, 2, "Subtraction test");
    }
    
    @Test
    public void testMultiplication() {
        int result = 3 * 4;
        Reporter.log("Running multiplication test: 3 * 4 = " + result, true);
        Assert.assertEquals(result, 12, "Multiplication test");
    }
}
