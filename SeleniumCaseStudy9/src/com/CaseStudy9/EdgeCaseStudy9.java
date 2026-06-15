package com.CaseStudy9;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class EdgeCaseStudy9 {

    WebDriver driver;

    String URL = "https://auth.hollandandbarrett.com/u/login?";

    @Test(dataProvider = "HollandAndBarrett")
    public void TestBrowser(String username, String password) throws Exception {

        driver = new EdgeDriver();

        driver.manage().window().maximize();

        driver.get(URL);

        driver.findElement(By.id("username")).sendKeys(username);

        driver.findElement(By.id("password")).sendKeys(password);

        driver.findElement(By.name("action")).click();

        Thread.sleep(3000);

        // Intentional validation
        String currentUrl = driver.getCurrentUrl();

        Assert.assertFalse(currentUrl.contains("login"),
                "Login Failed");
    }

    @AfterMethod
    public void EndBrowser(ITestResult result) throws Exception {

        if (result.getStatus() == ITestResult.FAILURE) {

            TakesScreenshot ts = (TakesScreenshot) driver;

            File src = ts.getScreenshotAs(OutputType.FILE);

            File des = new File(
                    "./FailureScreenshots/Chrome_"
                            + System.currentTimeMillis()
                            + ".png");

            FileUtils.copyFile(src, des);

            System.out.println(
                    "Failure Screenshot Captured Successfully");
        }

        if (driver != null) {
            driver.quit();
        }
    }

    @DataProvider(name = "HollandAndBarrett")
    public Object[][] passData() {

        Object[][] data = new Object[3][2];

        data[0][0] = "nehabharti@gmail.com";
        data[0][1] = "neh@";

        data[1][0] = "bhartijha@gmail.com";
        data[1][1] = "bh@rti";

        data[2][0] = "bishakhabharti@gmail.com";
        data[2][1] = "bish@kh@";

        return data;
    }
}
