package sbt.test.pages;

import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sbtqa.tag.pagefactory.Page;
import ru.sbtqa.tag.pagefactory.PageFactory;
import ru.sbtqa.tag.pagefactory.annotations.ActionTitle;
import ru.sbtqa.tag.pagefactory.annotations.PageEntry;
import ru.sbtqa.tag.pagefactory.exceptions.PageException;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

/**
 * Created by Home on 09.11.2017.
 */
@PageEntry(title = YandexMarketFoundProductPage.YANDEX_MARKET_FOUND_PRODUCT_PAGE_TITLE)
public class YandexMarketFoundProductPage extends Page {
    private static final Logger log = LoggerFactory.getLogger(YandexMarketFoundProductPage.class);
    public static final String YANDEX_MARKET_FOUND_PRODUCT_PAGE_TITLE = "Найденный продукт";

    @FindBy(xpath = ".//div[contains(@class, 'n-product-summary__headline')]//div[contains(@class, 'n-title__text')]//h1[contains(@class, 'title ')]")
    WebElement productTitle;

    public YandexMarketFoundProductPage() {
        PageFactory.initElements(new HtmlElementDecorator(
                new HtmlElementLocatorFactory(PageFactory.getDriver())), this);
        Assert.assertTrue(PageFactory.getDriver().getTitle().toLowerCase().contains("купить по выгодной цене на яндекс.маркете"));
    }

    @ActionTitle("проверяет, что название продукта совпадает с названием сохраненного продукта")
    public void verifyThatProductNameIsEqualToTheStoredProductName() throws PageException {
        if (!productTitle.isDisplayed()) {
            throw new PageException("The product title element is not displayed");
        }
        String storedProductInstanceName = YandexMarketPage.getCurrentProductInstanceName();
        if (storedProductInstanceName == null) {
            throw new PageException("There is no any stored product instance for the current thread");
        }
        Assert.assertEquals(storedProductInstanceName, productTitle.getText());
    }
}
