package com.qa.ExtentReportListener;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporterNG implements IReporter {

    private ExtentReports extent;

    @Override
    public void generateReport(
            List<XmlSuite> xmlSuites,
            List<ISuite> suites,
            String outputDirectory) {

        ExtentSparkReporter spark =
                new ExtentSparkReporter(outputDirectory + "/ExtentReport.html");

        extent = new ExtentReports();
        extent.attachReporter(spark);

        for (ISuite suite : suites) {
            Map<String, ISuiteResult> results = suite.getResults();

            for (ISuiteResult result : results.values()) {
                ITestContext context = result.getTestContext();

                buildTestNodes(context.getPassedTests(), Status.PASS);
                buildTestNodes(context.getFailedTests(), Status.FAIL);
                buildTestNodes(context.getSkippedTests(), Status.SKIP);
            }
        }
        extent.flush();
    }

    private void buildTestNodes(
            org.testng.IResultMap tests, Status status) {

        for (ITestResult result : tests.getAllResults()) {
            ExtentTest test = extent.createTest(result.getName());
            test.assignCategory(result.getMethod().getGroups());
            if (result.getThrowable() != null) {
                test.log(status, result.getThrowable().getMessage());
            } else {
                test.log(status, "Test " + status.toString().toLowerCase());
            }
        }
    }
}
