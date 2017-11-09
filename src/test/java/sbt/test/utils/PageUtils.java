package sbt.test.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.sbtqa.tag.pagefactory.PageFactory;

/**
 * Created by Home on 09.11.2017.
 */
public class PageUtils {

    public static WebElement getWebElement(By by) {
        return PageFactory.getDriver()
                .findElement(by);
    }

    public static WebElement getWebElement(WebElement parent, By by) {
        return parent.findElement(by);
    }
}
