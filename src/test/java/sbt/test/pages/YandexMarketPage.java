package sbt.test.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sbtqa.tag.pagefactory.Page;
import ru.sbtqa.tag.pagefactory.PageFactory;
import ru.sbtqa.tag.pagefactory.annotations.ActionTitle;
import ru.sbtqa.tag.pagefactory.annotations.ElementTitle;
import ru.sbtqa.tag.pagefactory.annotations.PageEntry;
import ru.sbtqa.tag.pagefactory.exceptions.PageException;
import ru.yandex.qatools.htmlelements.element.Link;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;
import sbt.test.blocks.SearchLine;
import sbt.test.utils.PageUtils;

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
    private Link extendedSearchLink;

    public YandexMarketPage() {
        PageFactory.initElements(new HtmlElementDecorator(
                new HtmlElementLocatorFactory(PageFactory.getDriver())), this);
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

}
