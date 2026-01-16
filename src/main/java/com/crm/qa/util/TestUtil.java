package com.crm.qa.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.*;

import com.crm.qa.base.TestBase;

public class TestUtil extends TestBase {

    // ================= Timeouts =================
    public static final long PAGE_LOAD_TIMEOUT = 60;

    // ================= Test Data =================
    // Interview-safe: dynamic path
    public static final String TESTDATA_SHEET_PATH =
            System.getProperty("user.dir")
                    + "/src/main/java/com/crm/qa/testdata/FreeCrmTestData.xlsx";

    static Workbook book;
    static Sheet sheet;

    // ================= Frame Handling =================
    public void switchToFrame() {
        driver.switchTo().frame("mainpanel");
    }

    // ================= Data Provider Utility =================
    public static Object[][] getTestData(String sheetName) {

        try (FileInputStream file = new FileInputStream(TESTDATA_SHEET_PATH)) {

            book = WorkbookFactory.create(file);
            sheet = book.getSheet(sheetName);

            Object[][] data = new Object[sheet.getLastRowNum()]
                    [sheet.getRow(0).getLastCellNum()];

            for (int i = 0; i < sheet.getLastRowNum(); i++) {
                for (int k = 0; k < sheet.getRow(0).getLastCellNum(); k++) {
                    data[i][k] = sheet.getRow(i + 1).getCell(k).toString();
                }
            }
            return data;

        } catch (Exception e) {
            throw new RuntimeException("Failed to read test data from Excel", e);
        }
    }

    // ================= Screenshot Utility =================
    // Called ONLY from TestNG listener
    public static void takeScreenshotAtEndOfTest(String testName) {

        try {
            File srcFile = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.FILE);

            String path = System.getProperty("user.dir")
                    + "/screenshots/" + testName + "_" + System.currentTimeMillis() + ".png";

            FileUtils.copyFile(srcFile, new File(path));

            System.out.println("Screenshot captured: " + path);

        } catch (Exception e) {
            System.out.println("Screenshot capture failed");
        }
    }
}
