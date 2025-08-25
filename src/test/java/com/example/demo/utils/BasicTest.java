package com.example.demo.utils;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * This class ensures we have at least one basic test that will run
 * and be included in the test report.
 */
public class BasicTest {
    
    @Test
    public void testBasic() {
        // A simple test that will always pass
        Assert.assertTrue(true, "This test should always pass");
        System.out.println("BasicTest executed successfully");
    }
}
