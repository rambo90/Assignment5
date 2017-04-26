package myprojects.automation.assignment5;


import myprojects.automation.assignment5.model.BuyerData;
import myprojects.automation.assignment5.utils.Properties;
import myprojects.automation.assignment5.utils.logging.CustomReporter;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Random;

import static myprojects.automation.assignment5.utils.DataConverter.parsePriceValue;

public class GeneralActions {
    private WebDriver driver;
    private WebDriverWait wait;
    private String name;
    private float price;
    private String quantities;
    private String productURL;

    public GeneralActions(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 30);
    }

    public void openRandomProduct() {
        driver.navigate().to(Properties.getBaseUrl());
        waitForContentLoad(By.cssSelector("#wrapper"));
        driver.findElement(By.cssSelector("#content > section > a")).click();
        List<WebElement> elements = driver.findElements(By.cssSelector(".h3.product-title"));
        elements.get(new Random().nextInt(elements.size())).click();
    }

    public void getOpenedProductInfo() {
        CustomReporter.logAction("Get information about currently opened product");
        name = driver.findElement(By.cssSelector(".h1")).getText();
        price = parsePriceValue(driver.findElement(By.cssSelector(".current-price")).getText());
        productURL = driver.findElement(By.cssSelector("head > link:nth-child(3)")).getAttribute("href");
        driver.findElement(By.xpath("//a[text()='Подробнее о товаре']")).click();
        waitForContentLoad(By.cssSelector("#product-details > div.product-quantities > span"));
        quantities = driver.findElement(By.cssSelector("#product-details > div.product-quantities > span")).getText();
    }

    public void addToCart() {
        driver.findElement(By.cssSelector(".btn.btn-primary.add-to-cart")).click();
        waitForContentLoad(By.cssSelector("#myModalLabel"));
        driver.findElement(By.cssSelector("a.btn.btn-primary")).click();
        Assert.assertTrue(driver.findElement(By.cssSelector(".label.js-subtotal")).getText().contains("1"));
        Assert.assertEquals(driver
                .findElement(By.cssSelector(".product-line-info")).getText().toUpperCase(), name);
        Assert.assertTrue(parsePriceValue(driver.findElement(By.cssSelector(".product-price")).getText())== price);
    }

    public void procced(BuyerData newBuyer) {
        driver.findElement(By.xpath("//*[@id='main']/div/div[2]/div/div[2]/div")).click();
        driver.findElement(By.name("firstname")).sendKeys(newBuyer.getName());
        driver.findElement(By.name("lastname")).sendKeys(newBuyer.getSurname());
        driver.findElement(By.name("email")).sendKeys(newBuyer.getEmail());
        driver.findElement(By.name("continue")).click();
        driver.findElement(By.name("address1")).sendKeys("Peremohy Avenue");
        driver.findElement(By.name("postcode")).sendKeys("01111");
        driver.findElement(By.name("city")).sendKeys("Kyiv");
        driver.findElement(By.name("confirm-addresses")).click();
        driver.findElement(By.name("confirmDeliveryOption")).click();
        List<WebElement> elements = driver.findElement(By.cssSelector(".payment-options"))
                .findElements(By.cssSelector(".custom-radio.pull-xs-left"));
        elements.get(new Random().nextInt(elements.size())).click();
        driver.findElement(By.cssSelector(".js-terms")).click();
        driver.findElement(By.cssSelector(".btn.btn-primary.center-block")).click();
        Assert.assertTrue(driver
                .findElement(By.cssSelector(".h1.card-title")).getText().contains("ВАШ ЗАКАЗ ПОДТВЕРЖДЁН"));
        Assert.assertTrue(driver.findElement(By.cssSelector(".col-xs-2")).getText().contains("1"));
        Assert.assertTrue(driver
                .findElement(By.cssSelector(".col-sm-4.col-xs-9")).getText().toUpperCase().contains( name));
        Assert.assertTrue(parsePriceValue(driver.findElement(By.cssSelector(".col-xs-5.text-xs-right")).getText()) == price);
    }

    public void checkQuantities() {
        driver.navigate().to(productURL);
        driver.findElement(By.xpath("//a[text()='Подробнее о товаре']")).click();
        waitForContentLoad(By.cssSelector("#product-details > div.product-quantities > span"));
        String quantitiesNew = driver.findElement(By.cssSelector("#product-details > div.product-quantities > span")).getText();
        CustomReporter.logAction("There were " + quantities);
        CustomReporter.logAction("Now there are " + quantitiesNew);
        Assert.assertFalse(quantities.equals(quantitiesNew));
    }

    private void waitForContentLoad(By by) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

}
