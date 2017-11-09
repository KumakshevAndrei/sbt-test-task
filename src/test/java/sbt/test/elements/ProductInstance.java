package sbt.test.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sbtqa.tag.pagefactory.PageFactory;
import ru.yandex.qatools.htmlelements.element.TypifiedElement;

/**
 * Created by Home on 09.11.2017.
 */
public class ProductInstance extends TypifiedElement {
    private static final Logger log = LoggerFactory.getLogger(ProductInstance.class);

    WebElement headerLinkElement;
    private static final String HEADER_LINK_ELEMENT_XPATH = ".//div[contains(@class, 'n-snippet-card2__title')]/a[contains(@class, 'link')]";

    /**
     * Specifies wrapped {@link WebElement}.
     *
     * @param wrappedElement {@code WebElement} to wrap.
     */
    public ProductInstance(WebElement wrappedElement) {
        super(wrappedElement);

    }

    public WebElement getHeaderLinkElement() {
        if (headerLinkElement == null) {
            headerLinkElement = getWrappedElement().findElement(By.xpath(HEADER_LINK_ELEMENT_XPATH));
        }
        return headerLinkElement;
    }

    public void openProductInfo() {
        getHeaderLinkElement().click();
    }

    public String getProductName() {
        String productName = getHeaderLinkElement().getText();
        openProductInfo();
        WebDriver webDriver = PageFactory.getDriver();
        try {
            WebElement titleElement = webDriver.findElement(By.xpath(".//div[contains(@class, 'n-product-summary__headline')]//div[contains(@class, 'n-title__text')]//h1[contains(@class, 'title ')]"));
            productName = titleElement.getText();
        } catch (NoSuchElementException e) {
            log.warn("The product's title has not been found on the product's information page so the product's name displayed in the products' list will be returned");
        }
        webDriver.navigate().back();
        return productName;
    }

}
