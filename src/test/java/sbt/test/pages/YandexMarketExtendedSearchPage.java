package sbt.test.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sbtqa.tag.pagefactory.Page;
import ru.sbtqa.tag.pagefactory.PageFactory;
import ru.sbtqa.tag.pagefactory.annotations.ActionTitle;
import ru.sbtqa.tag.pagefactory.annotations.PageEntry;
import ru.sbtqa.tag.pagefactory.annotations.RedirectsTo;
import ru.sbtqa.tag.pagefactory.exceptions.PageException;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;
import sbt.test.utils.enums.LimitType;
import sbt.test.utils.PageUtils;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * Created by Home on 09.11.2017.
 */
@PageEntry(title = YandexMarketExtendedSearchPage.YANDEX_MARKET_EXTENDED_SEARCH_PAGE_TITLE)
public class YandexMarketExtendedSearchPage extends Page {
    private final static Logger log = LoggerFactory.getLogger(YandexMarketExtendedSearchPage.class);

    public static final String YANDEX_MARKET_EXTENDED_SEARCH_PAGE_TITLE = "Расширенный поиск на Яндекс Маркете";

    @FindBy(xpath = ".//div[contains(@class, 'n-filter-block_type_normal')]")
    private List<WebElement> filtersBlocks;

    private WebElement currentFiltersBlock;

    private HashMap<String, WebElement> filtersBlocksCache = new HashMap<>();

    @FindBy(xpath = ".//a[contains(@class, 'button') and @type = 'button' and .//span[contains(@class, 'button__text') and text() = 'Показать подходящие']]")
    @RedirectsTo(page = YandexMarketPage.class)
    private WebElement submitButton;

    public YandexMarketExtendedSearchPage() {
        PageFactory.initElements(new HtmlElementDecorator(
                new HtmlElementLocatorFactory(PageFactory.getDriver())), this);
    }

    @ActionTitle("указывает предел цены")
    public void selectPricingLimit(String price) throws PageException {
        selectPricingLimit(LimitType.TO, price);
    }

    @ActionTitle("указывает предел цены")
    public void selectPricingLimit(String limitName, String price) throws PageException {
        LimitType limitType = LimitType.getByDisplayingName(limitName);
        if (limitType == null) {
            log.warn("Unknown pricing limit type specified: {}. Specified pricing limit's value will be used as a highest limit.");
            limitType = LimitType.TO;
        }
        selectPricingLimit(limitType, price);
    }

    private void selectPricingLimit(LimitType limitType, String price) throws PageException {
        /*try {
            Integer.parseInt(price);
        } catch (NumberFormatException e) {
            throw new PageException("The price should be a valid integer number", e);
        }*/
        //commented because maybe it will be needed to test a negative cases when the specified price is not a number
        WebElement priceInput = PageUtils.getWebElement(By.xpath(limitType.getXpath()));
        priceInput.sendKeys(price);
    }

    @ActionTitle("переключается на блок фильтров")
    public void selectFiltersBlock(final String blockName) throws PageException {
        if (!filtersBlocksCache.containsKey(blockName)) {
            Optional<WebElement> targetFiltersBlockOptional = filtersBlocks.stream()
                    .filter(filtersBlock -> {
                        WebElement blockHeader = getFiltersBlockHeader(filtersBlock, blockName);
                        if (blockHeader != null && blockHeader.isDisplayed()) {
                            if (isFiltersBlockClosed(filtersBlock)) {
                                blockHeader.click();
                            }
                            return true;
                        }
                        return false;
                    })
                    .findFirst();

            if (!targetFiltersBlockOptional.isPresent()) {
                throw new PageException("The filters block with the \"" + blockName + "\" has not been found");
            }

            WebElement targetFiltersBlock = targetFiltersBlockOptional.get();
            filtersBlocksCache.put(blockName, targetFiltersBlock);
            currentFiltersBlock = targetFiltersBlock;
        } else {
            currentFiltersBlock = filtersBlocksCache.get(blockName);
            WebElement targetFiltersBlockHeader = getFiltersBlockHeader(currentFiltersBlock, blockName);
            if (targetFiltersBlockHeader != null && targetFiltersBlockHeader.isDisplayed()) {
                if (isFiltersBlockClosed(currentFiltersBlock)) {
                    targetFiltersBlockHeader.click();
                }
            } else {
                throw new PageException("The target filters block has been found but it can not be opened because its header is not displayed");
            }
        }

    }

    private boolean isFiltersBlockClosed(WebElement filtersBlock) {
        return filtersBlock.getAttribute("class").contains("n-filter-block_closed_yes");
    }

    @Nullable
    private WebElement getFiltersBlockHeader(WebElement filtersBlock, String blockName) {
        try {
            return PageUtils.getWebElement(filtersBlock, By.xpath(".//div[contains(@class, 'n-filter-block__header') and .//span[contains(@class, 'title__content') and text() = '" + blockName + "']]"));
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    @ActionTitle("выбирает в текущем блоке фильтров чекбоксы")
    public void selectCheckboxInCurrentFiltersBlock(List<String> checkboxesNames) throws PageException {
        if (currentFiltersBlock == null) {
            throw new PageException("Current filters block is not defined. You should select the current filters block before choosing the checkboxes into it");
        }
        for (String checkBoxName : checkboxesNames) {
            try {
                WebElement targetCheckBox = PageUtils.getWebElement(currentFiltersBlock,
                        By.xpath("//a[contains(@class, 'n-filter-block__item-link') and .//label[contains(@class, 'checkbox__label') and text() = '" + checkBoxName + "']]"));
                targetCheckBox.click();
            } catch (NoSuchElementException e) {
                throw new PageException("Current filters block does not contains the checkbox with the name \"" + checkBoxName + "\"", e);
            }
        }
    }

    @ActionTitle("применяет параметры поиска")
    public void submitSearchingParameters() {
        submitButton.click();
    }

}
