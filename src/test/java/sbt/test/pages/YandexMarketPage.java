package sbt.test.pages;

import com.google.common.base.Strings;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sbtqa.tag.pagefactory.Page;
import ru.sbtqa.tag.pagefactory.PageFactory;
import ru.sbtqa.tag.pagefactory.annotations.ActionTitle;
import ru.sbtqa.tag.pagefactory.annotations.ElementTitle;
import ru.sbtqa.tag.pagefactory.annotations.PageEntry;
import ru.sbtqa.tag.pagefactory.annotations.RedirectsTo;
import ru.sbtqa.tag.pagefactory.exceptions.PageException;
import ru.yandex.qatools.htmlelements.element.Link;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;
import sbt.test.blocks.SearchLine;
import sbt.test.elements.ProductInstance;
import sbt.test.utils.PageUtils;

import java.util.List;
import java.util.Optional;

/**
 * Created by Home on 08.11.2017.
 */
@PageEntry(title = YandexMarketPage.YANDEX_MARKET_PAGE_TITLE)
public class YandexMarketPage extends Page {
    private final static Logger log = LoggerFactory.getLogger(YandexMarketPage.class);

    static final String YANDEX_MARKET_PAGE_TITLE = "Яндекс Маркет";

    @ElementTitle("Блок поиска")
    private SearchLine searchLine;

    @ElementTitle("Расширенный поиск")
    @FindBy(xpath = ".//a[contains(@class, 'link') and text() = 'Перейти ко всем фильтрам']")
    @RedirectsTo(page = YandexMarketExtendedSearchPage.class)
    private Link extendedSearchLink;

    @FindBy(linkText = " все результаты поиска")
    private Link allResultsLink;

    @ElementTitle("Список товаров")
    @FindBy(xpath = ".//div[contains(@class, 'n-snippet-card2 ') or contains(@class, 'n-product-title ')]")
    private List<ProductInstance> productInstances;

    private static String currentProductInstanceName;

    public YandexMarketPage() {
        PageFactory.initElements(new HtmlElementDecorator(
                new HtmlElementLocatorFactory(PageFactory.getDriver())), this);
    }

    public static String getCurrentProductInstanceName() {
        return currentProductInstanceName;
    }

    @ActionTitle("выбирает раздел")
    public void chooseSection(String sectionName) throws PageException {
        WebElement sectionLink = PageUtils.getWebElement(
                By.xpath(".//a[contains(@class, 'topmenu__link') and text() = '" + sectionName + "' or contains(@class, 'catalog-menu__list-item') and text() = '" + sectionName + "']")
        );
        if (!sectionLink.isDisplayed()) {
            throw new PageException("The section with the name \"" + sectionName + "\" does not exist on the \"" + YANDEX_MARKET_PAGE_TITLE + "\" page");
        }
        sectionLink.click();
    }

    @ActionTitle("открывает расширенный поиск")
    public void openExtendedSearch() {
        extendedSearchLink.click();
    }

    @ActionTitle("проверяет, что количество найденных товаров равно")
    public void verifyFoundProductsAmount(String expectedAmountString) throws PageException {
        int expectedAmount;
        try {
            expectedAmount = Integer.parseInt(expectedAmountString);
        } catch (NumberFormatException e) {
            throw new PageException("The expected found products' amount's value should be a integer number, but \"" + expectedAmountString + "\" has been specified", e);
        }
        Assert.assertEquals(expectedAmount, productInstances.size());
    }

    @ActionTitle("запоминает продукт")
    public void storeFoundProduct(String numberOrName) throws PageException {
        currentProductInstanceName = findAmongFoundProducts(numberOrName).getProductName();
    }

    @ActionTitle("ищет запомненный продукт")
    public void searchStoredProduct() throws PageException {
        if (currentProductInstanceName == null) {
            throw new PageException("There is no any product stored, please store any product firstly");
        }
        searchLine.startSearch(currentProductInstanceName);

    }

    @ActionTitle("открывает продукт")
    public void openFoundProduct(String nameOrNumber) throws PageException {
        try {
            findAmongFoundProducts(nameOrNumber).openProductInfo();
        } catch (NoSuchElementException e) {
            log.warn("There is nothing horrible because the searching can either open the found product's info or display the list of the matching products");
        }
    }

    @ActionTitle("открывает все результаты поиска")
    public void openAllSearchResults() {
        try {
            if (allResultsLink.isDisplayed()) {
                allResultsLink.click();
            }
        } catch (NoSuchElementException e) {
            log.warn("There is nothing horrible because the all search results link may not be presented on the search results page");
        }
    }

    private ProductInstance findAmongFoundProducts(String numberOrName) throws PageException {
        if (productInstances.isEmpty()) {
            throw new PageException("The found product instances list is empty, please try to perform the searching firstly");
        }
        try {
            int storingProductNumber = Strings.isNullOrEmpty(numberOrName) ? 0 : Integer.parseInt(numberOrName);
            try {
                return productInstances.get(storingProductNumber);
            } catch (IndexOutOfBoundsException e) {
                throw new PageException("There is only " + productInstances.size() + " products found so please specify " +
                        (productInstances.size() > 1
                                ? "the number between 0 and " + productInstances.size()
                                : "the 0 number or invoke this action without any parameter"));
            }
        } catch (NumberFormatException e) {
            log.warn("The parameter of product instance to store is not a number so it will be used as a product's name");

            Optional<ProductInstance> productToStore = productInstances.stream()
                    .filter(productInstance -> numberOrName.equalsIgnoreCase(productInstance.getProductName()))
                    .findFirst();

            if (!productToStore.isPresent()) {
                throw new PageException("A product with the \"" + numberOrName + "\" name has not been found in the list of the found products");
            }

            return productToStore.get();
        }
    }
}
