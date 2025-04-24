package com.SwagLab.Listners;

import com.SwagLab.utils.LogsUtil;
import com.SwagLab.utils.Waits;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.WebDriverListener;

public class WebManagerListener implements WebDriverListener {
    @Override
    public void beforeFindElement(WebDriver driver, By locator) {
        Waits.waitForElementToBeVisible(driver, locator);
        LogsUtil.info("Finding element : ", locator.toString());
    }

    @Override
    public void beforeFindElements(WebDriver driver, By locator) {
        Waits.waitForListOfElementsToBeVisible(driver, locator);
        LogsUtil.info("Finding group of element linked by locator : ", locator.toString());
    }


}