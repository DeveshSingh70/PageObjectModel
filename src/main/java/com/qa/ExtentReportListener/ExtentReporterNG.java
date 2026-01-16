package com.qa.ExtentReportListener;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

/**
 * ExtentReporterNG
 *
 * This class integrates Extent Reports with TestNG using ITestListener.
 * It provides:
 *  - Real-time test logging
 *  - PASS / FAIL / SKIP status
 *  - Thread-safe execution (parallel support)
 *
 * This replaces old IReporter-based reporting (post execution only).
 */
public class ExtentReporterNG implements ITestListener {

    // ExtentReports instance (one per execution)
    private static ExtentReports extent;

    /*
     * ThreadLocal ensures:
     * Each test gets its own ExtentTest instance
     * Safe for parallel execution
     */
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    /*
     * Static block executes once when class loads
     * Used to configure Extent Report
     */
    static {
        // Location where Extent report will be generated
        ExtentSparkReporter spark =
                new ExtentSparkReporter("test-output/ExtentReport.html");

        // Report configuration
        spark.config().setReportName("Automation Test Report");
        spark.config().setDocumentTitle("Selenium Test Results");

        // Attach reporter to ExtentReports
        extent = new ExtentReports();
        extent.attachReporter(spark);

        // System information visible in report
        extent.setSystemInfo("Tester", "Dev");
        extent.setSystemInfo("Framework", "Selenium + TestNG");
        extent.setSystemInfo("Browser", "Chrome");
    }

    /**
     * Triggered when a test method starts
     * Creates a new test entry in Extent report
     */
    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest =
                extent.createTest(result.getMethod().getMethodName());

        // Store test instance in ThreadLocal
        test.set(extentTest);
    }

    /**
     * Triggered when a test passes
     */
    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().log(Status.PASS, "Test passed successfully");
    }

    /**
     * Triggered when a test fails
     * Logs failure reason (exception)
     */
    @Override
    public void onTestFailure(ITestResult result) {
        test.get().log(Status.FAIL, "Test failed");
        test.get().log(Status.FAIL, result.getThrowable());
    }

    /**
     * Triggered when a test is skipped
     */
    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().log(Status.SKIP, "Test skipped");
    }

    /**
     * Triggered after all tests finish execution
     * Flushes the Extent report to disk
     */
    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }

    /**
     * Utility method
     * Can be used inside test classes to log steps:
     *
     * ExtentReporterNG.getTest().info("Step description");
     */
    public static ExtentTest getTest() {
        return test.get();
    }
}
