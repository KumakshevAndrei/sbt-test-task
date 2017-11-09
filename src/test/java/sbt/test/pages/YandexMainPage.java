package sbt.test.pages;

import org.openqa.selenium.support.FindBy;
import ru.sbtqa.tag.pagefactory.Page;
import ru.sbtqa.tag.pagefactory.PageFactory;
import ru.sbtqa.tag.pagefactory.annotations.ActionTitle;
import ru.sbtqa.tag.pagefactory.annotations.ElementTitle;
import ru.sbtqa.tag.pagefactory.annotations.PageEntry;
import ru.yandex.qatools.htmlelements.annotations.Name;
import ru.yandex.qatools.htmlelements.element.Link;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;
import sbt.test.blocks.SearchLine;

/**
 * Created by Home on 08.11.2017.
 */
@PageEntry(title = "Яндекс")
public class YandexMainPage extends Page {

    @ElementTitle("Блок поиска")
    private SearchLine searchLine;

    @Name("Маркет")
    @FindBy(xpath = ".//a[contains(@class, 'home-link') and text() = 'Маркет']")
    public Link marketLink;

    public YandexMainPage() {
        PageFactory.initElements(new HtmlElementDecorator(
                new HtmlElementLocatorFactory(PageFactory.getDriver())), this);
    }

    @ActionTitle("переходит на Маркет")
    public void goToMarket() {
        marketLink.click();
    }

}
