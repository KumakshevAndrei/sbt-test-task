package sbt.test.blocks;

import org.openqa.selenium.Keys;
import org.openqa.selenium.support.FindBy;
import ru.sbtqa.tag.pagefactory.annotations.ActionTitle;
import ru.yandex.qatools.htmlelements.annotations.Name;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.element.TextInput;

/**
 * Created by Home on 08.11.2017.
 */
@Name("Блок поиска")
@FindBy(xpath = ".//form[contains(@class, 'search2')]")
public class SearchLine extends HtmlElement {

    @Name("Строка поиска")
    @FindBy(xpath = "//input[@id = 'text' and contains(@class, 'input__control') or @name = 'text' and contains(@class, 'input__control')]")
    public TextInput searchString;

    @ActionTitle("ищет")
    public void startSearch(String text) {
        searchString.sendKeys(text);
        searchString.sendKeys(Keys.ENTER);
    }

}
