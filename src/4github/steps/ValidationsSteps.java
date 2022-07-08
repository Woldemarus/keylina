package ru.alfabank.steps;

import com.codeborne.selenide.SelenideElement;
import cucumber.api.java.ru.Тогда;
import lombok.experimental.Delegate;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import ru.alfabank.alfatest.cucumber.api.AkitaScenario;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@Slf4j
public class ValidationsSteps {

    @Delegate
    AkitaScenario alfaScenario = AkitaScenario.getInstance();

    @Тогда("^в поле \"([^\"]*)\" содержится ошибка бизнес валидации об обязательности поля$")
    public void fieldWithError(String fieldName) {
        SelenideElement field = alfaScenario.getCurrentPage().getElement(fieldName);
        SelenideElement error = field.find(By.xpath("./ancestor::*[contains(@class, 'radio-group_invalid') or contains(@class, 'select_invalid') or contains(@class, 'input_invalid') or contains(@class, 'control-group_invalid') or contains(@class, 'textarea_invalid')]"));
        assertTrue(String.format("Поле [%s] не содержит ошибки", fieldName), error.exists());
    }

    @Тогда("^в поле \"([^\"]*)\" содержится текст ошибки бизнес валидации \"(.*)\"$")
    public void fieldTextError(String fieldName, String validationText) {
        SelenideElement field = alfaScenario.getCurrentPage().getElement(fieldName);
        SelenideElement error = field.find(By.xpath("./ancestor::span[contains(@class, 'input__inner')]/child::span[contains(@class, 'input__sub')]/div"));
        String resultErrorText = error.getValue()!=null?error.getValue():error.getText();
        assertTrue(String.format("В поле [%s] содержится ошибка валидации: [%s]", fieldName, validationText), resultErrorText.contains(validationText));
    }

    @Тогда("^в поле textarea \"([^\"]*)\" содержится текст ошибки бизнес валидации \"(.*)\"$")
    public void fieldTextAreaError(String fieldName, String validationText) {
        SelenideElement field = alfaScenario.getCurrentPage().getElement(fieldName);
        SelenideElement error = field.find(By.xpath("./ancestor::span[contains(@class, 'textarea__inner')]/child::span[contains(@class, 'textarea__sub')]/div"));
        String resultErrorText = error.getValue()!=null?error.getValue():error.getText();
        assertTrue(String.format("В поле [%s] содержится ошибка валидации: [%s]", fieldName, validationText), resultErrorText.contains(validationText));
    }

    @Тогда("^в сдвоенном поле \"([^\"]*)\" содержится ошибка валидации$")
    public void passportTextError(String fieldName) {
        SelenideElement field = alfaScenario.getCurrentPage().getElement(fieldName);
        SelenideElement error = field.find(By.xpath("./child::div[contains(@class, 'passport__sub')]"));
        assertTrue(String.format("В поле [%s] содержится ошибка валидации:", fieldName), error.getText().length() > 0);
    }

    @Тогда("^в поле \"([^\"]*)\" отсутствует ошибка$")
    public void fieldWithoutError(String fieldName) {
        SelenideElement field = alfaScenario.getCurrentPage().getElement(fieldName);
        SelenideElement error = field.find(By.xpath("./ancestor::span[contains(@class, 'radio-group_invalid') or contains(@class, 'input_invalid')]"));
        assertFalse(String.format("Поле [%s] содержит ошибку", fieldName), error.exists());
    }
}


