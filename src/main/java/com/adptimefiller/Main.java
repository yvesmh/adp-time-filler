package com.adptimefiller;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Main {

    private static final String eTimeUrl = "https://collaboration.carefusion.com/sites/Inside/HRCONNECT/time/Pages/eTimesplash.aspx";
    private static final By eTimeImageElement = By.cssSelector("img[src='/sites/Inside/HRCONNECT/time/PublishingImages/eTimeHTML.png'");
    private static final By myTimecardLinkElement = By.cssSelector("a[href='/43hb9e/applications/mss/html/portal-launch.jsp?url=-513492104&from=home'");
    private static final int[] workDayRowNumbers = {
            12,
            11,
            10,
            9,
            8,
            5,
            4,
            3,
            2,
            1,
    };

    private static final int[] morningRowNumbers = {
            0,
            2,
            4,
            6,
            8,
            12,
            14,
            16,
            18,
            20
    };

    private static final String morningTimeIn = "09:00";
    private static final String morningTimeOut = "13:00";
    private static final String afternoonTimeIn = "14:00";
    private static final String afternoonTimeOut = "18:00";

    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get(eTimeUrl);
        WebDriverWait wait = new WebDriverWait(driver, 100);
        wait.until(ExpectedConditions.elementToBeClickable(eTimeImageElement));
        driver.findElement(eTimeImageElement).click();
        wait.until(ExpectedConditions.titleIs("Enterprise eTIME®"));
        // switch to iframe, otherwise findElement wont find anything inside iframe
        driver.switchTo().frame(driver.findElement(By.id("contentPane")));
        driver.findElement(myTimecardLinkElement).click();
        addMoreRows(driver);
        fillTimeCard(driver);
    }

    private static void addMoreRows(WebDriver driver) {
        for(int workdayRowNumber: workDayRowNumbers) {
            driver.findElement(By.xpath("//*[@id=\"kronos\"]/form[1]/table[2]/tbody/tr[1]/td[1]/table/tbody/tr[" + workdayRowNumber + "]/td[1]/div/a")).click();
        }
    }

    private static void fillTimeCard(WebDriver driver) {
        for(int morningRowNumber: morningRowNumbers) {
            driver.findElement(By.name("T0R" + morningRowNumber + "C5")).sendKeys(morningTimeIn);
            driver.findElement(By.name("T0R" + morningRowNumber + "C7")).sendKeys(morningTimeOut);
            driver.findElement(By.name("T0R" + (morningRowNumber + 1) + "C5")).sendKeys(afternoonTimeIn);
            driver.findElement(By.name("T0R" + (morningRowNumber + 1) + "C7")).sendKeys(afternoonTimeOut);
        }
    }
}
