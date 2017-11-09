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
import ru.sbtqa.tag.pagefactory.exceptions.PageException;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;
import sbt.test.utils.LimitType;
import sbt.test.utils.PageUtils;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * Created by Home on 09.11.2017.
 */
@PageEntry(title = "Расширенный поиск на Яндекс Маркете")
public class YandexMarketExtendedSearchPage extends Page {
    private final static Logger log = LoggerFactory.getLogger(YandexMarketExtendedSearchPage.class);

    @FindBy(xpath = ".//div[contains(@class, 'n-filter-block_type_normal')]")
    private List<WebElement> searchingBlocks;

    private WebElement currentSearchingBlock;

    private HashMap<String, WebElement> searchingBlocksCache = new HashMap<>();

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
    public void openFiltersBlock(final String blockName) throws PageException {
        if (!searchingBlocksCache.containsKey(blockName)) {
            Optional<WebElement> targetSearchingBlockOptional = searchingBlocks.stream()
                    .filter(searchingBlock -> {
                        WebElement blockHeader = getSearchingBlockHeader(searchingBlock, blockName);
                        if (blockHeader != null && blockHeader.isDisplayed()) {
                            if (isSearchingBlockClosed(searchingBlock)) {
                                blockHeader.click();
                            }
                            return true;
                        }
                        return false;
                    })
                    .findFirst();

            if (!targetSearchingBlockOptional.isPresent()) {
                throw new PageException("The searching block with the \"" + blockName + "\" has not been found");
            }

            WebElement targetSearchingBlock = targetSearchingBlockOptional.get();
            searchingBlocksCache.put(blockName, targetSearchingBlock);
            currentSearchingBlock = targetSearchingBlock;
        } else {
            currentSearchingBlock = searchingBlocksCache.get(blockName);
            WebElement targetSearchingBlockHeader = getSearchingBlockHeader(currentSearchingBlock, blockName);
            if (targetSearchingBlockHeader != null && targetSearchingBlockHeader.isDisplayed()) {
                if (isSearchingBlockClosed(currentSearchingBlock)) {
                    targetSearchingBlockHeader.click();
                }
            } else {
                throw new PageException("The target searching block has been found but it can not be opened because its header is not displayed");
            }
        }

    }

    private boolean isSearchingBlockClosed(WebElement searchingBlock) {
        return searchingBlock.getAttribute("class").contains("n-filter-block_closed_yes");
    }

    @Nullable
    private WebElement getSearchingBlockHeader(WebElement searchingBlock, String blockName) {
        try {
            return searchingBlock.findElement(By.xpath(".//div[contains(@class, 'n-filter-block__header') and .//span[contains(@class, 'title__content') and text() = '" + blockName + "']]"));
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    @ActionTitle("выбирает в текущем блоке фильтров чекбоксы")
    public void selectCheckboxInCurrentSearchingFilter(List<String> checkboxesNames) throws PageException {
        if (currentSearchingBlock == null) {
            throw new PageException("Current searching block is not defined. You should select the current searching block before choosing the checkboxes into it");
        }
        for (String checkBoxName : checkboxesNames) {

        }
    }

}
