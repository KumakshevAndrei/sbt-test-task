package sbt.test.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.element.TypifiedElement;
import sbt.test.utils.PageUtils;

/**
 * Created by Home on 09.11.2017.
 */
public class ProductInstance extends TypifiedElement {

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

    public void openProductInfo(){
        getHeaderLinkElement().click();
    }

    public String getProductName() {
        return getHeaderLinkElement().getText();
    }

}
