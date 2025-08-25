package com.example.demo.utils;

import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.xml.XmlSuite;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TestReportListener implements IReporter {

    public TestReportListener() {
        // Default constructor
    }

    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        // Create a timestamp for the report
        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String reportFileName = "test-report-" + timestamp + ".html";
        String reportFilePath = outputDirectory + File.separator + reportFileName;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(reportFilePath))) {
            writer.write("<html><head>");
            writer.write("<style>");
            writer.write("body { font-family: Arial, sans-serif; margin: 20px; }");
            writer.write("h1, h2 { color: #333; }");
            writer.write("table { border-collapse: collapse; width: 100%; margin-bottom: 20px; }");
            writer.write("th, td { padding: 8px; text-align: left; border: 1px solid #ddd; }");
            writer.write("th { background-color: #f2f2f2; }");
            writer.write(".passed { color: green; }");
            writer.write(".failed { color: red; }");
            writer.write(".skipped { color: orange; }");
            writer.write("</style>");
            writer.write("<title>API Test Report</title>");
            writer.write("</head><body>");
            writer.write("<h1>API Test Report</h1>");
            writer.write("<p>Generated on: " + new Date() + "</p>");

            // Iterate through the suites
            for (ISuite suite : suites) {
                writer.write("<h2>Suite: " + suite.getName() + "</h2>");
                
                Map<String, ISuiteResult> suiteResults = suite.getResults();
                
                for (ISuiteResult suiteResult : suiteResults.values()) {
                    ITestContext testContext = suiteResult.getTestContext();
                    
                    writer.write("<h3>Test: " + testContext.getName() + "</h3>");
                    
                    // Summary table
                    writer.write("<table>");
                    writer.write("<tr><th>Total Tests</th><th>Passed</th><th>Failed</th><th>Skipped</th><th>Start Time</th><th>End Time</th></tr>");
                    writer.write("<tr>");
                    writer.write("<td>" + (testContext.getPassedTests().size() + testContext.getFailedTests().size() + testContext.getSkippedTests().size()) + "</td>");
                    writer.write("<td class='passed'>" + testContext.getPassedTests().size() + "</td>");
                    writer.write("<td class='failed'>" + testContext.getFailedTests().size() + "</td>");
                    writer.write("<td class='skipped'>" + testContext.getSkippedTests().size() + "</td>");
                    writer.write("<td>" + new Date(testContext.getStartDate().getTime()) + "</td>");
                    writer.write("<td>" + new Date(testContext.getEndDate().getTime()) + "</td>");
                    writer.write("</tr>");
                    writer.write("</table>");
                    
                    // Passed tests
                    if (!testContext.getPassedTests().getAllResults().isEmpty()) {
                        writer.write("<h4>Passed Tests</h4>");
                        writer.write("<table>");
                        writer.write("<tr><th>Test Name</th><th>Method</th><th>Duration (ms)</th></tr>");
                        
                        testContext.getPassedTests().getAllResults().forEach(result -> {
                            try {
                                writer.write("<tr>");
                                writer.write("<td>" + result.getTestClass().getName() + "</td>");
                                writer.write("<td>" + result.getName() + "</td>");
                                writer.write("<td>" + (result.getEndMillis() - result.getStartMillis()) + "</td>");
                                writer.write("</tr>");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                        
                        writer.write("</table>");
                    }
                    
                    // Failed tests
                    if (!testContext.getFailedTests().getAllResults().isEmpty()) {
                        writer.write("<h4>Failed Tests</h4>");
                        writer.write("<table>");
                        writer.write("<tr><th>Test Name</th><th>Method</th><th>Duration (ms)</th><th>Exception</th></tr>");
                        
                        testContext.getFailedTests().getAllResults().forEach(result -> {
                            try {
                                writer.write("<tr>");
                                writer.write("<td>" + result.getTestClass().getName() + "</td>");
                                writer.write("<td>" + result.getName() + "</td>");
                                writer.write("<td>" + (result.getEndMillis() - result.getStartMillis()) + "</td>");
                                writer.write("<td>" + result.getThrowable().getMessage() + "</td>");
                                writer.write("</tr>");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                        
                        writer.write("</table>");
                    }
                    
                    // Skipped tests
                    if (!testContext.getSkippedTests().getAllResults().isEmpty()) {
                        writer.write("<h4>Skipped Tests</h4>");
                        writer.write("<table>");
                        writer.write("<tr><th>Test Name</th><th>Method</th><th>Exception</th></tr>");
                        
                        testContext.getSkippedTests().getAllResults().forEach(result -> {
                            try {
                                writer.write("<tr>");
                                writer.write("<td>" + result.getTestClass().getName() + "</td>");
                                writer.write("<td>" + result.getName() + "</td>");
                                writer.write("<td>" + (result.getThrowable() != null ? result.getThrowable().getMessage() : "N/A") + "</td>");
                                writer.write("</tr>");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                        
                        writer.write("</table>");
                    }
                }
            }
            
            writer.write("</body></html>");
            
            System.out.println("Test report generated: " + reportFilePath);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
