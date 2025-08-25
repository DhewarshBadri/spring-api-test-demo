package com.example.demo.utils;

import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TestReportListener implements IReporter {

    public TestReportListener() {
        // Default constructor
    }

    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        // Create a timestamp for the report
        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        
        // Ensure we have the target/surefire-reports directory
        File surefireDir = new File("target/surefire-reports");
        if (!surefireDir.exists()) {
            surefireDir.mkdirs();
        }
        
        // Create reports in both the output directory and surefire directory
        String reportFileName = "test-report-" + timestamp + ".html";
        String reportFilePath = outputDirectory + File.separator + reportFileName;
        String surefireReportPath = "target/surefire-reports" + File.separator + "test-report.html";

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
            
            // Also write the same content to the surefire directory for GitHub Actions
            try (BufferedWriter surefireWriter = new BufferedWriter(new FileWriter(surefireReportPath))) {
                // Copy the same HTML content
                surefireWriter.write("<html><head>");
                surefireWriter.write("<style>");
                surefireWriter.write("body { font-family: Arial, sans-serif; margin: 20px; }");
                surefireWriter.write("h1, h2 { color: #333; }");
                surefireWriter.write("table { border-collapse: collapse; width: 100%; margin-bottom: 20px; }");
                surefireWriter.write("th, td { padding: 8px; text-align: left; border: 1px solid #ddd; }");
                surefireWriter.write("th { background-color: #f2f2f2; }");
                surefireWriter.write(".passed { color: green; }");
                surefireWriter.write(".failed { color: red; }");
                surefireWriter.write(".skipped { color: orange; }");
                surefireWriter.write("</style>");
                surefireWriter.write("<title>API Test Report</title>");
                surefireWriter.write("</head><body>");
                surefireWriter.write("<h1>API Test Report</h1>");
                surefireWriter.write("<p>Generated on: " + new Date() + "</p>");
                
                // Write test summary
                int totalTests = 0;
                int passedTests = 0;
                int failedTests = 0;
                int skippedTests = 0;
                
                for (ISuite suite : suites) {
                    for (ISuiteResult suiteResult : suite.getResults().values()) {
                        ITestContext testContext = suiteResult.getTestContext();
                        passedTests += testContext.getPassedTests().size();
                        failedTests += testContext.getFailedTests().size();
                        skippedTests += testContext.getSkippedTests().size();
                    }
                }
                
                totalTests = passedTests + failedTests + skippedTests;
                
                surefireWriter.write("<h2>Test Summary</h2>");
                surefireWriter.write("<table>");
                surefireWriter.write("<tr><th>Total Tests</th><th>Passed</th><th>Failed</th><th>Skipped</th></tr>");
                surefireWriter.write("<tr>");
                surefireWriter.write("<td>" + totalTests + "</td>");
                surefireWriter.write("<td class='passed'>" + passedTests + "</td>");
                surefireWriter.write("<td class='failed'>" + failedTests + "</td>");
                surefireWriter.write("<td class='skipped'>" + skippedTests + "</td>");
                surefireWriter.write("</tr>");
                surefireWriter.write("</table>");
                
                // Write details for each suite
                for (ISuite suite : suites) {
                    surefireWriter.write("<h2>Suite: " + suite.getName() + "</h2>");
                    
                    for (ISuiteResult suiteResult : suite.getResults().values()) {
                        ITestContext testContext = suiteResult.getTestContext();
                        
                        surefireWriter.write("<h3>Test: " + testContext.getName() + "</h3>");
                        
                        // Add test details similar to the original report
                        // (You can expand this section to include passed/failed/skipped tests as needed)
                    }
                }
                
                surefireWriter.write("</body></html>");
                System.out.println("Surefire test report generated: " + surefireReportPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            // Also create a JUnit/Surefire compatible XML report
            try {
                File xmlReportDir = new File("target/surefire-reports");
                if (!xmlReportDir.exists()) {
                    xmlReportDir.mkdirs();
                }
                
                for (ISuite suite : suites) {
                    for (ISuiteResult suiteResult : suite.getResults().values()) {
                        ITestContext testContext = suiteResult.getTestContext();
                        String className = testContext.getName().replace(" ", "_");
                        
                        // Create a surefire-compatible XML report
                        String xmlFileName = "TEST-" + className + ".xml";
                        String xmlFilePath = "target/surefire-reports/" + xmlFileName;
                        
                        try (BufferedWriter xmlWriter = new BufferedWriter(new FileWriter(xmlFilePath))) {
                            xmlWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
                            xmlWriter.write("<testsuite name=\"" + className + "\" time=\"" + 
                                           ((testContext.getEndDate().getTime() - testContext.getStartDate().getTime()) / 1000.0) + 
                                           "\" tests=\"" + (testContext.getPassedTests().size() + 
                                                         testContext.getFailedTests().size() + 
                                                         testContext.getSkippedTests().size()) + 
                                           "\" errors=\"0\" failures=\"" + testContext.getFailedTests().size() + 
                                           "\" skipped=\"" + testContext.getSkippedTests().size() + "\">\n");
                            
                            // Write system properties
                            xmlWriter.write("  <properties>\n");
                            xmlWriter.write("    <property name=\"java.runtime.name\" value=\"" + System.getProperty("java.runtime.name", "Unknown") + "\"/>\n");
                            xmlWriter.write("    <property name=\"sun.boot.library.path\" value=\"" + System.getProperty("sun.boot.library.path", "Unknown") + "\"/>\n");
                            xmlWriter.write("    <property name=\"java.vm.version\" value=\"" + System.getProperty("java.vm.version", "Unknown") + "\"/>\n");
                            xmlWriter.write("    <property name=\"java.vm.vendor\" value=\"" + System.getProperty("java.vm.vendor", "Unknown") + "\"/>\n");
                            xmlWriter.write("    <property name=\"java.vendor.url\" value=\"" + System.getProperty("java.vendor.url", "Unknown") + "\"/>\n");
                            xmlWriter.write("    <property name=\"user.dir\" value=\"" + System.getProperty("user.dir", "Unknown") + "\"/>\n");
                            xmlWriter.write("    <property name=\"java.vm.name\" value=\"" + System.getProperty("java.vm.name", "Unknown") + "\"/>\n");
                            xmlWriter.write("    <property name=\"java.version\" value=\"" + System.getProperty("java.version", "Unknown") + "\"/>\n");
                            xmlWriter.write("    <property name=\"os.name\" value=\"" + System.getProperty("os.name", "Unknown") + "\"/>\n");
                            xmlWriter.write("  </properties>\n");
                            
                            // Write test cases
                            testContext.getPassedTests().getAllResults().forEach(result -> {
                                try {
                                    xmlWriter.write("  <testcase name=\"" + result.getName() + "\" classname=\"" + 
                                                  result.getTestClass().getName() + "\" time=\"" + 
                                                  ((result.getEndMillis() - result.getStartMillis()) / 1000.0) + "\"/>\n");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });
                            
                            testContext.getFailedTests().getAllResults().forEach(result -> {
                                try {
                                    xmlWriter.write("  <testcase name=\"" + result.getName() + "\" classname=\"" + 
                                                  result.getTestClass().getName() + "\" time=\"" + 
                                                  ((result.getEndMillis() - result.getStartMillis()) / 1000.0) + "\">\n");
                                    xmlWriter.write("    <failure message=\"" + 
                                                  (result.getThrowable() != null ? escapeXml(result.getThrowable().getMessage()) : "Test failed") + 
                                                  "\" type=\"" + 
                                                  (result.getThrowable() != null ? result.getThrowable().getClass().getName() : "Unknown") + 
                                                  "\">\n");
                                    if (result.getThrowable() != null) {
                                        xmlWriter.write(escapeXml(getStackTraceAsString(result.getThrowable())));
                                    }
                                    xmlWriter.write("    </failure>\n");
                                    xmlWriter.write("  </testcase>\n");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });
                            
                            testContext.getSkippedTests().getAllResults().forEach(result -> {
                                try {
                                    xmlWriter.write("  <testcase name=\"" + result.getName() + "\" classname=\"" + 
                                                  result.getTestClass().getName() + "\" time=\"0\">\n");
                                    xmlWriter.write("    <skipped/>\n");
                                    xmlWriter.write("  </testcase>\n");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });
                            
                            xmlWriter.write("</testsuite>\n");
                            System.out.println("XML report generated: " + xmlFilePath);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Helper method to escape XML special characters
    private String escapeXml(String input) {
        if (input == null) return "";
        return input.replace("&", "&amp;")
                   .replace("<", "&lt;")
                   .replace(">", "&gt;")
                   .replace("\"", "&quot;")
                   .replace("'", "&apos;");
    }
    
    // Helper method to get stack trace as string
    private String getStackTraceAsString(Throwable throwable) {
        if (throwable == null) return "";
        StringBuilder sb = new StringBuilder();
        sb.append(throwable.toString()).append("\n");
        for (StackTraceElement element : throwable.getStackTrace()) {
            sb.append("\tat ").append(element.toString()).append("\n");
        }
        return sb.toString();
    }
    
    // Helper method to create Maven compatible XML reports
    private void createMavenXmlReports(List<ISuite> suites) {
        try {
            // Create surefire directory if it doesn't exist
            File xmlReportDir = new File("target/surefire-reports");
            if (!xmlReportDir.exists()) {
                xmlReportDir.mkdirs();
            }
            
            // For each test suite, create a separate XML file
            for (ISuite suite : suites) {
                for (ISuiteResult suiteResult : suite.getResults().values()) {
                    ITestContext testContext = suiteResult.getTestContext();
                    
                    // Sanitize the test name for use as a file name
                    String className = testContext.getName().replace(" ", "_");
                    
                    // Create XML report file - using Maven Surefire standard naming format
                    String xmlFileName = "TEST-" + className + ".xml";
                    String xmlFilePath = "target/surefire-reports/" + xmlFileName;
                    
                    try (BufferedWriter xmlWriter = new BufferedWriter(new FileWriter(xmlFilePath))) {
                        // Start XML report
                        xmlWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
                        
                        // Total time in seconds (end time - start time)
                        double totalTime = (testContext.getEndDate().getTime() - testContext.getStartDate().getTime()) / 1000.0;
                        
                        // Total tests count
                        int totalTests = testContext.getPassedTests().size() + testContext.getFailedTests().size() + testContext.getSkippedTests().size();
                        
                        // Test suite element
                        xmlWriter.write("<testsuite name=\"" + className + "\" time=\"" + totalTime + 
                                       "\" tests=\"" + totalTests + 
                                       "\" errors=\"0\" failures=\"" + testContext.getFailedTests().size() + 
                                       "\" skipped=\"" + testContext.getSkippedTests().size() + "\">\n");
                        
                        // Write test cases
                        writeTestCasesToXml(xmlWriter, testContext);
                        
                        // Close test suite
                        xmlWriter.write("</testsuite>\n");
                        
                        System.out.println("Maven XML report generated: " + xmlFilePath);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Helper method to write test cases to XML
    private void writeTestCasesToXml(BufferedWriter xmlWriter, ITestContext testContext) throws IOException {
        // Write passed tests
        for (ITestResult result : testContext.getPassedTests().getAllResults()) {
            writeTestCaseToXml(xmlWriter, result, "passed");
        }
        
        // Write failed tests
        for (ITestResult result : testContext.getFailedTests().getAllResults()) {
            writeTestCaseToXml(xmlWriter, result, "failed");
        }
        
        // Write skipped tests
        for (ITestResult result : testContext.getSkippedTests().getAllResults()) {
            writeTestCaseToXml(xmlWriter, result, "skipped");
        }
    }
    
    // Helper method to write a single test case to XML
    private void writeTestCaseToXml(BufferedWriter xmlWriter, ITestResult result, String status) throws IOException {
        // Calculate test duration in seconds
        double duration = (result.getEndMillis() - result.getStartMillis()) / 1000.0;
        
        // Start test case element
        xmlWriter.write("  <testcase name=\"" + result.getName() + "\" classname=\"" + 
                       result.getTestClass().getName() + "\" time=\"" + duration + "\"");
        
        if ("passed".equals(status)) {
            // Just close the element for passed tests
            xmlWriter.write("/>\n");
        } else {
            // For failed or skipped tests, add more details
            xmlWriter.write(">\n");
            
            if ("failed".equals(status)) {
                // Add failure information
                Throwable throwable = result.getThrowable();
                String message = (throwable != null) ? escapeXml(throwable.getMessage()) : "Test failed without exception";
                String type = (throwable != null) ? throwable.getClass().getName() : "unknown";
                
                xmlWriter.write("    <failure message=\"" + message + "\" type=\"" + type + "\">\n");
                if (throwable != null) {
                    xmlWriter.write(escapeXml(getStackTraceAsString(throwable)));
                }
                xmlWriter.write("    </failure>\n");
            } else if ("skipped".equals(status)) {
                // Add skipped information
                xmlWriter.write("    <skipped/>\n");
            }
            
            xmlWriter.write("  </testcase>\n");
        }
    }
}
