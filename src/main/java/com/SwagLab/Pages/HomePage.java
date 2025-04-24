package com.SwagLab.Pages;

import com.SwagLab.utils.BrowserActions;
import com.SwagLab.utils.ElementActions;
import com.SwagLab.utils.LogsUtil;
import com.SwagLab.utils.Validations;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.locators.RelativeLocator;

import java.util.List;

public class HomePage {
    private List<WebElement> allCartBtns;

    private WebDriver driver;

    private final By menuBtn = By.cssSelector("button#react-burger-menu-btn");
    private final By cartBtn = By.cssSelector("a[class*='shopping_cart_link']");
    private final By logoutLink = By.cssSelector("a#logout_sidebar_link");


    //Instructor
    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    //Navigation
    @Step("navigate To Home page Url {Url} ")
    public HomePage navigateToHomePageUrl(String Url) {
        BrowserActions.navigateToUrl(driver, Url);
        return this;
    }

    //actions
    @Step("add {itemName} To Cart ")
    public HomePage addSpecificItemToCart(String itemName) {
        LogsUtil.info("adding " + itemName + " to cart");
        By addCartButton = RelativeLocator.with(By.xpath("//button[.='Add to cart']"))
                .below(By.xpath("//div[.='" + itemName + "']"));
        ElementActions.clickOnElement(driver, addCartButton);
        return this;
    }

    @Step("Open the cart ")
    public CartPage clickOnCartIcon() {
        ElementActions.clickOnElement(driver, this.cartBtn);
        return new CartPage(driver);
    }

    @Step("Logout from Website")
    public SignInPage logout() throws InterruptedException {
        ElementActions.clickOnElement(driver, this.menuBtn);
        ElementActions.clickOnElementByJavaScrip(driver, this.logoutLink);
        return new SignInPage(driver);
    }

    //Validation
    @Step("Validate that {itemName} is added to Cart ")
    public HomePage assertThatProductAddedToCart(String itemName) {
        LogsUtil.info("Asserting if " + itemName + "is added to cart");
        By removeCartButton = RelativeLocator.with(By.xpath("//button[.='Remove']"))
                .below(By.xpath("//div[.='" + itemName + "']"));
        String buttonName = ElementActions.GetTextFromWebElement(driver, removeCartButton);
        Validations.validateEquals(buttonName.toLowerCase(), "remove", "Product not added ");
        LogsUtil.info("product " + itemName + " added to cart successfully");
        return this;
    }

}



