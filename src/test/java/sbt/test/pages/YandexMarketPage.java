package sbt.test.pages;

import ru.sbtqa.tag.pagefactory.Page;
import ru.sbtqa.tag.pagefactory.PageFactory;
import ru.sbtqa.tag.pagefactory.annotations.ElementTitle;
import ru.sbtqa.tag.pagefactory.annotations.PageEntry;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;
import sbt.test.blocks.SearchLine;

/**
 * Created by Home on 08.11.2017.
 */
@PageEntry(title = "Яндекс Маркет")
public class YandexMarketPage extends Page {

    @ElementTitle("Блок поиска")
    private SearchLine searchLine;

    public YandexMarketPage(){
        PageFactory.initElements(new HtmlElementDecorator(
                new HtmlElementLocatorFactory(PageFactory.getDriver())), this);
    }

}
