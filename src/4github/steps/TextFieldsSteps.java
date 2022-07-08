package ru.alfabank.steps;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import cucumber.api.java.ru.И;
import cucumber.api.java.ru.Когда;
import lombok.experimental.Delegate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.Keys;
import ru.alfabank.alfatest.cucumber.api.AkitaScenario;
import java.text.DecimalFormat;
import java.util.Random;

import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Selenide.sleep;
import static ru.alfabank.tests.core.helpers.PropertyLoader.getPropertyOrValue;

@Slf4j
public class TextFieldsSteps {

    @Delegate
    AkitaScenario alfaScenario = AkitaScenario.getInstance();

    BaseMethods baseMethods = new BaseMethods();

    @И("^в поле \"([^\"]*)\" дописывается значение \"([^\"]*)\"$")
    public void addValue(String fieldName, String value) {
        SelenideElement field = alfaScenario.getCurrentPage().getElement(fieldName);
        String oldValue = field.getValue();
        field.sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
        field.setValue("");
        field.setValue(oldValue + value);
    }

    /**
     * Устанавливается значение (в приоритете: из property, из переменной сценария, значение аргумента) в заданное поле.
     * Перед использованием поле нужно очистить
     */
    @Когда("^в поле телефон введено значение \"(.*)\"$")
    public void setFieldValuePhone(String value) {
        value = baseMethods.getPropertyOrStringVariableOrValue(value);
        SelenideElement valueInput = alfaScenario.getCurrentPage().getElement("Телефон");
        valueInput.clear();
        valueInput.setValue(value);
    }

    /**
     * В поле телефон устанавливается случайное значение
     */
    @Когда("^в поле телефон введено случайное значение и сохранено в переменную \"(.*)\"$$")
    public void setRandomValuePhone(String phoneVariable) {
        //Генерируем номер телефона
        Random rand = new Random();
        int num1 = rand.nextInt(99);
        int num2 = rand.nextInt(999);
        int num3 = rand.nextInt(9999);
        DecimalFormat df3 = new DecimalFormat("000"); // 3 zeros
        DecimalFormat df4 = new DecimalFormat("0000"); // 4 zeros
        String phoneNumber = df3.format(num1+900) + df3.format(num2) + df4.format(num3);
        System.out.println(phoneNumber);

        SelenideElement valueInput = alfaScenario.getCurrentPage().getElement("Телефон");
        valueInput.clear();
        valueInput.setValue(phoneNumber);

        alfaScenario.setVar(phoneVariable, phoneNumber);
    }

    @Когда("^поместили значение \"([^\"]*)\" в поле \"([^\"]*)\"$")
    public void setValToField(String value, String nameOfField) {
        SelenideElement valueInput = alfaScenario.getCurrentPage().getElement(nameOfField);
        valueInput.sendKeys(Keys.chord(Keys.CONTROL, value));
        valueInput.should(not(Condition.empty));
    }

    @Когда("^установлено значение \"([^\"]*)\" в поле \"([^\"]*)\" без проверки заполнения$")
    public void setValueToField(String amount, String nameOfField) {
        SelenideElement valueInput = alfaScenario.getCurrentPage().getElement(nameOfField);
        valueInput.clear();
        valueInput.setValue(String.valueOf(amount));
    }
    @Когда("^правильно очищено поле \"([^\"]*)\"$")
    public void cleanField(String nameOfField) {
        SelenideElement valueInput = alfaScenario.getCurrentPage().getElement(nameOfField);
        valueInput.click();
        valueInput.clear();
        valueInput.setValue("");
    }

    @Когда("^очищено поле \"([^\"]*)\" и введено значение \"(.*)\"$")
    public void setFieldValue(String elementName, String value) {
        value = baseMethods.getPropertyOrStringVariableOrValue(value);
        sleep(500);
        SelenideElement valueInput = alfaScenario.getCurrentPage().getElement(elementName);
        clear(valueInput);
        valueInput.setValue(String.valueOf(value));
    }

    @Когда("^очищено поле \"([^\"]*)\" и введено случайное число из (\\d+) (?:цифр|цифры)$")
    public void setFieldValue(String elementName, int seqLength) {
        sleep(500);
        SelenideElement valueInput = alfaScenario.getCurrentPage().getElement(elementName);
        valueInput.scrollTo();
        valueInput.click();
        valueInput.clear();
        String numSeq = RandomStringUtils.randomNumeric(seqLength);
        valueInput.setValue(String.valueOf(numSeq));
    }

    @Когда("^выполен переход в поле \"([^\"]*)\"$")
    public void clickField(String elementName) {
        sleep(500);
        SelenideElement valueInput = alfaScenario.getCurrentPage().getElement(elementName);
        valueInput.scrollTo();
        valueInput.click();
    }

    @Когда("^поле \"([^\"]*)\" заполняется уникальным \"([^\"]*)\"-значным словом$")
    public void stepCreateUniqueWordValue(String var, Integer length) {
        String uniqueWord = "Б" + RandomStringUtils.random(length, "йцукенгшщзхэждлорпавыфячсмитбю");
        SelenideElement valueInput = alfaScenario.getCurrentPage().getElement(var);
        valueInput.setValue(uniqueWord);
    }

    @Когда("^поле \"([^\"]*)\" заполняется уникальным \"([^\"]*)\"-значным цифро-буквенным словом$")
    public void stepCreateUniqueWordNValue(String var, Integer length) {
        String uniqueWord = RandomStringUtils.random(length - 2, "1323у4к5е6н7г8ш9щ0з1х2ф3ы4в5а6п7р8о9л041ж2э3я4ч5с6м7и8т9ь0б1ю");
        SelenideElement valueInput = alfaScenario.getCurrentPage().getElement(var);
        valueInput.setValue(uniqueWord);
    }

    @Когда("^в поле \"([^\"]*)\" введено случайное число длиной от (\\d+) до (\\d+) (?:цифр|цифры)$")
    public void inputRandomNumInRange(String elementName, int minSeqLength, int maxSeqLength) {
        SelenideElement valueInput = alfaScenario.getCurrentPage().getElement(elementName);
        cleanField(elementName);
        String numSeq = RandomStringUtils.randomNumeric(minSeqLength, maxSeqLength);
        valueInput.setValue(numSeq);
        alfaScenario.write(String.format("В поле [%s] введено значение [%s]", elementName, numSeq));
    }


    @Когда("^поле \"([^\"]*)\" заполнено значением \"(.*)\"$")
    public void setValueIntoField(String elementName, String value) {
        value = getPropertyOrValue(value);
        SelenideElement valueInput = alfaScenario.getCurrentPage().getElement(elementName);
        valueInput.sendKeys(value);
    }


    private void clear(SelenideElement selenideElement) {
        //в одних случаях работает в clear и не работает sendKeys, в других наоборот
        selenideElement.scrollTo();
        selenideElement.doubleClick();
        selenideElement.clear();
        do {
            selenideElement.doubleClick().sendKeys(Keys.DELETE);
        } while (selenideElement.getValue().length() != 0);
    }
}


