package com.huang.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class ReactAppTest {

    private WebDriver driver;

    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver",
                "/Users/tony/Documents/qac-cv-management-system/AutomatedTestReact/chromedriver");
        driver = new ChromeDriver();
    }

    @After
    public void tearDown() throws Exception {
        Thread.sleep(3000);
        driver.quit();
    }

    @Test
    public void testCreateAccount() {

        String username = "randomUser";
        String pwd = "password";

        driver.manage().window().maximize();
        driver.get("localhost:3000/create-account");
        driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/form/input[1]"))
                .sendKeys(username);
        driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys(pwd);
        driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/form/input[3]")).sendKeys(pwd);
        driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/form/input[4]")).submit();
        driver.findElement(By.xpath("//*[@id=\"root\"]/div/nav/ul/li[1]/a")).click();
        driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/form/input[1]")).sendKeys(username);
        driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/form/input[2]")).sendKeys(pwd);
        driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/form")).submit();
    }

    @Test
    public void testLogin() {
        String username = "username";
        String pwd = "password";
        driver.manage().window().maximize();
        driver.get("localhost:3000");
        driver.findElement(By.xpath("//*[@id=\"root\"]/div/nav/ul/li[1]/a")).click();
        driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/form/input[1]")).sendKeys(username);
        driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/form/input[2]")).sendKeys(pwd);
        driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/form")).submit();
    }

}