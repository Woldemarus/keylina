package ru.bank.steps;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import cucumber.api.java.ru.И;
import cucumber.api.java.ru.Когда;
import cucumber.api.java.ru.Тогда;
import lombok.experimental.Delegate;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import ru.bank.alfatest.cucumber.api.AkitaScenario;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Selenide.sleep;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.junit.Assert.*;
import static ru.bank.tests.core.helpers.PropertyLoader.getPropertyOrValue;

@Slf4j
public class AssertsSteps {

    @Delegate
    AkitaScenario alfaScenario = AkitaScenario.getInstance();

    InputInteractionSteps inputInteractionSteps = new InputInteractionSteps();
    ElementsInteractionSteps elementsInteractionSteps = new ElementsInteractionSteps();
    ListInteractionSteps listInteractionSteps = new ListInteractionSteps();
    ElementsVerificationSteps elementsVerificationSteps = new ElementsVerificationSteps();

    private TextFieldsSteps textFieldsSteps = new TextFieldsSteps();
    private ComboboxsSteps comboboxsSteps = new ComboboxsSteps();

    @Тогда("^в поле \"([^\"]*)\" содержится подсказка с текстом \"([^\"]*)\"$")
    public void fieldWithHintFillingText(String fieldName, String hintText) {
        SelenideElement field = alfaScenario.getCurrentPage().getElement(fieldName);
        SelenideElement hint = field.find(By.xpath(".//following::*[contains(text(), (hintText))]"));
        assertTrue(String.format("Поле [%s] не содержит подсказку с заданным текстом", fieldName), hint.exists());
    }

    @Тогда("^(?:поле|элемент) \"([^\"]*)\" (?:нередактируемо|нередактируем)$")
    public void ElemIsDisabled(String elemName) {
        SelenideElement element = alfaScenario.getCurrentPage().getElement(elemName);
        SelenideElement disabled = element.find(By.xpath(".//ancestor::*[contains(@class, 'disabled')]"));
        assertTrue(String.format("Поле [%s] можно заполнить", elemName), disabled.exists());
    }

    @Тогда("^элемент \"([^\"]*)\" доступен для заполнения$")
    public void ElemIsEnabled(String elemName) {
        SelenideElement element = alfaScenario.getCurrentPage().getElement(elemName);
        SelenideElement disabled = element.find(By.xpath(".//ancestor::*[contains(@class, 'disabled')]"));
        assertFalse(String.format("Поле [%s] нельзя заполнить", elemName), disabled.exists());
    }

    @Тогда("^элемент \"([^\"]*)\" не видим на странице$")
    public void ElemIsNotVisible(String elemName) {
        SelenideElement element = alfaScenario.getCurrentPage().getElement(elemName);
        element.shouldNotBe(Condition.visible);
    }

    @И("^элемента \"([^\"]*)\" нет на странице$")
    public void elemIsNotPresentedOnPage(String elemName) {
        assertTrue(alfaScenario.getCurrentPage().getElement(elemName).is(Condition.disappears));
    }
    
    @И("^элемент \"([^\"]*)\" не содержит CSS элемента \"([^\"]*)\"$")
    public void elemCssIsNotPresentedInElement(String elemName, String cssName) {
        assertEquals("none", alfaScenario.getCurrentPage().getElement(elemName).getCssValue(cssName));
    }

/*    @И("^элемент \"([^\"]*)\" в атрибуте \"([^\"]*)\" содержит значение \"([^\"]*)\"$")
    public void elemCssIsNotPresentedInElement(String elemName, String attributeName, String attributeValue) {

        assertEquals("none", alfaScenario.getCurrentPage().getElement(elemName).getCssValue(cssName));
    }*/

    @Тогда("^поле \"([^\"]*)\" отображено: ([^\"]*)$")
    public void hasBusinessValidationError(String fieldName, Boolean hasErrorMessage) {
        SelenideElement field = alfaScenario.getCurrentPage().getElement(fieldName);
        assertEquals(field.isDisplayed(), hasErrorMessage);
    }

    @Тогда("^(\\d+)-й элемент списка \"([^\"]*)\" (содержит|не содержит) иконку моментального выпуска$")
    public void fieldWithIconByNum(Integer number, String listName, String existOrNot) {
        List<SelenideElement> listOfElements = alfaScenario.getCurrentPage().getElementsList(listName);
        SelenideElement currentField;
        try {
            currentField = listOfElements.get(number);
        } catch (IndexOutOfBoundsException ex) {
            throw new IndexOutOfBoundsException(
                    String.format("В списке %s нет элемента с номером %s. Количество элементов списка = %s",
                            listName, number, listOfElements.size()));
        }
        SelenideElement icon = currentField.find(By.xpath(".//img[contains(@class, 'embossed-card-icon_theme')]"));
        if ("содержит".equals(existOrNot)) {
            assertTrue(String.format("Элемент списка [%s] не содержит иконку [%s]", currentField, icon), icon.exists());
        } else {
            assertTrue(String.format("Элемент списка [%s] содержит иконку", currentField), icon.is(not(exist)));
        }
    }

    @Тогда("в списке подразделений доставки, офис с наименованием \"([^\"]*)\" (содержит|не содержит) иконку моментального выпуска$")
    public void fieldWithIconByName(String nameOffice, String existOrNot) {

        List<SelenideElement> listOfElements = alfaScenario.getCurrentPage().getElementsList("Список отделений");
        SelenideElement currentField = null;
        for (SelenideElement element : listOfElements) {
            if (element.getText().equals(getPropertyOrValue(nameOffice))) {
                currentField = element;
                break;
            }
        }
        if (currentField != null) {
            SelenideElement icon = currentField.find(By.xpath(".//img[contains(@class, 'embossed-card-icon_theme')]"));
            if ("содержит".equals(existOrNot)) {
                assertTrue(String.format("Элемент списка [%s] не содержит иконку [%s]", currentField, icon), icon.exists());
            } else {
                assertTrue(String.format("Элемент списка [%s] содержит иконку", currentField), icon.is(not(exist)));
            }
        } else
            fail("Элемент:" + nameOffice + " не найден в списке");
    }

    @Тогда("^переменная \"([^\"]*)\" не содержит значений$")
    public void environmentVariableIsEmpty(String environmentVariable) {
        Object obj = alfaScenario.getVar(environmentVariable);
        assertThat(String.format("Переменная [%s] пустая", environmentVariable),
                obj.toString(),
                isEmptyOrNullString());
    }
    @Тогда("^переменная \"([^\"]*)\" содержит значение$")
    public void environmentVariableIsNotEmpty(String environmentVariable) {
        Object obj = alfaScenario.getVar(environmentVariable);
        assertNotNull(String.format("Переменная [%s] не пустая", environmentVariable),
                obj.toString());
    }

    @Когда("^валидно заполнено Отчество")
    public void fillMiddleName() {
        inputInteractionSteps.setRandomCharSequence("Отчество", 35, "кириллице");
    }


    @Когда("^серия и номер актуального паспорта заполнены валидными значениями$")
    public void fillPassportData() {
        inputInteractionSteps.inputRandomNumSequence("Серия паспорта", 4);
        inputInteractionSteps.inputRandomNumSequence("Номер паспорта", 6);
    }

    @Когда("^сведения о выдаче паспорта заполнены валидными значениями$")
    public void fillPassportIssueData() {
        inputInteractionSteps.currentDate("Дата выдачи", "dd.MM.yyyy");
        inputInteractionSteps.inputRandomNumSequence("Код подразделения", 6);
        inputInteractionSteps.setRandomCharSequence("Кем выдан", 35, "кириллице");
        inputInteractionSteps.setFieldValue("Дата рождения", "18061989");
        inputInteractionSteps.setRandomCharSequence("Место рождения", 35, "кириллице");
    }

    @Когда("^клиент ранее менял паспорт и указал валидные данные предыдущего паспорта$")
    public void fillPreviousPassportData() {
        elementsInteractionSteps.clickOnElement("Менял паспорт");
        inputInteractionSteps.inputRandomNumSequence("Серия старого паспорта", 4);
        inputInteractionSteps.inputRandomNumSequence("Номер старого паспорта", 6);
    }

    @И("^заполнены все поля блока предыдущие ФИО$")
    public void setFullPrebiousName() {
        elementsInteractionSteps.clickOnElement("Менял ФИО");
        inputInteractionSteps.setRandomCharSequence("Предыдущая фамилия", 35, "кириллице");
        inputInteractionSteps.setRandomCharSequence("Предыдущее имя", 35, "кириллице");
        inputInteractionSteps.setRandomCharSequence("Предыдущее отчество", 35, "кириллице");
    }

    @Когда("^валидно заполнены все поля адреса регистрации$")
    public void fillRegistrationAddressFull() {
        inputInteractionSteps.setFieldValue("Адрес регистрации", "м");
        new RoundUpSteps().pushButtonOnKeyboard("ENTER");
        sleep(1000);
        inputInteractionSteps.setRandomCharSequence("Район регистрации", 35, "кириллице");
        inputInteractionSteps.setRandomCharSequence("Город регистрации", 35, "кириллице");
        inputInteractionSteps.setRandomCharSequence("Улица регистрации", 35, "кириллице");
        elementsInteractionSteps.clickOnElement("Регион регистрации");
        listInteractionSteps.selectElementNumberFromList(1, "Выпадающий список регион регистрации");
        textFieldsSteps.inputRandomNumInRange("Дом регистрации", 1, 6);
        textFieldsSteps.inputRandomNumInRange("Корпус регистрации", 1, 6);
        textFieldsSteps.inputRandomNumInRange("Квартира регистрации", 1, 6);
        inputInteractionSteps.inputRandomNumSequence("Индекс регистрации", 6);
    }

    @Когда("^валидно заполнены все поля адреса проживания")
    public void fillLivingAddressFull() {
        elementsInteractionSteps.clickOnElement("Адрес не совпадает");
        inputInteractionSteps.setFieldValue("Адрес проживания", "м");
        new RoundUpSteps().pushButtonOnKeyboard("ENTER");
        sleep(1000);
        inputInteractionSteps.setRandomCharSequence("Район проживания", 35, "кириллице");
        inputInteractionSteps.setRandomCharSequence("Город проживания", 35, "кириллице");
        inputInteractionSteps.setRandomCharSequence("Улица проживания", 35, "кириллице");
        elementsInteractionSteps.clickOnElement("Регион проживания");
        listInteractionSteps.selectElementNumberFromList(1, "Выпадающий список регион проживания");
        textFieldsSteps.inputRandomNumInRange("Дом проживания", 1, 6);
        textFieldsSteps.inputRandomNumInRange("Корпус проживания", 1, 6);
        textFieldsSteps.inputRandomNumInRange("Квартира проживания", 1, 6);
        inputInteractionSteps.inputRandomNumSequence("Индекс проживания", 6);
    }

    @Когда("^валидно заполнен блок сведений о работе в статусе (трудоустроен|не трудоустроен)$")
    public void fillWork(String workStatus) {
        if ("не трудоустроен".equals(workStatus)) {
            elementsInteractionSteps.clickOnElement("Тип занятости");
            comboboxsSteps.chooseElementFromPopup(String.valueOf(4), "Выпадающий список тип занятости");
        } else {
            Random random = new Random();
            int min = 1;
            int max = 3;
            int number = min + random.nextInt(max - min + 1);
            elementsInteractionSteps.clickOnElement("Тип занятости");
            sleep(500);
            comboboxsSteps.chooseElementFromPopup(String.valueOf(number), "Выпадающий список тип занятости");
            inputInteractionSteps.setRandomCharSequence("Название организации", 35, "кириллице");
            inputInteractionSteps.setRandomCharSequence("Должность", 35, "кириллице");
        }
    }

    @Когда("^валидно указаны параметры заказываемой (Рублевой|Мультивалютной) карты$")
    public void fillValidCardParams(String packetId) {
        if ("Мультивалютной".equals(packetId)) {
            listInteractionSteps.selectRandomElementFromList("Кнопки валюты");
        }
        inputInteractionSteps.setFieldValue("Кодовое слово", "альфа123");
    }

    @Когда("^валидно заполнены данные доставки по Москве$")
    public void fillDeliveryToOffice() {
        elementsInteractionSteps.clickOnElement("Регион доставки");
        listInteractionSteps.checkIfSelectedListElementMatchesValue("Выпадающий список регион доставки", "Москва");
        sleep(1000);
        elementsInteractionSteps.clickOnElement("Город доставки");
        listInteractionSteps.selectRandomElementFromList("Выпадающий список город доставки");
        sleep(1000);
        if (!alfaScenario.getCurrentPage().getElement("В отделение").exists()) {
            elementsInteractionSteps.clickOnElement("Курьером");
        } else {
            elementsInteractionSteps.clickOnElement("В отделение");
            sleep(1000);
            elementsInteractionSteps.clickOnElement("Выбор отделения");
            sleep(1000);
            listInteractionSteps.selectRandomElementFromList("Список отделений");
        }
    }

    @Тогда("^значение переменной \"([^\"]*)\" соответствует значению \"([^\"]*)\"$")
    public void variableValueContainsString(String variableName, String variableValue) {
        String name = alfaScenario.getVar(variableName).toString();
        String value = getPropertyOrValue(variableValue);
        assertTrue(name.contains(value));
    }

    @Тогда("^в переменной \"([^\"]*)\" есть подстрока \"([^\"]*)\"$")
    public void variableValueCompare(String variableName, String variableValue) {
        Object name = alfaScenario.getVar(variableName);
        Object value = getPropertyOrValue(variableValue);
        assertEquals(name, value);
    }

        @Тогда("^страница Спасибо загрузилась (под клиентом|под оператором) с доставкой (Курьером|Отделение|Отделение МВК|В связной|В логистику) для платформы (Десктоп|Мобайл|ВебВью|Айфрейм)$")
    public void loadLastPage(String isFillingBy, String typePage, String typePlatfotm) {
        if ("под клиентом".equals(isFillingBy)) {
            switch (typePage) {
                case "Курьером":
                    elementsVerificationSteps.elemIsPresentedOnPage("Картинка");
                    elementsVerificationSteps.testFieldContainsInnerText("Заголовок Н", "Карта заказана");
                    elementsVerificationSteps.testFieldContainsInnerText("Текст 1 Н", "Дождитесь звонка сотрудника банка для уточнения деталей доставки");
                    elementsVerificationSteps.testFieldContainsInnerText("Текст 2 Н", "Наш сотрудник привезет карту в удобное для вас место и время");
                    elementsVerificationSteps.testFieldContainsInnerText("Текст о статусе карты", "textStatus");
                    break;
                case "Отделение":
                    elementsVerificationSteps.elemIsPresentedOnPage("Картинка");
                    elementsVerificationSteps.testFieldContainsInnerText("Заголовок Н", "Карта заказана");
                    check1TextInCourierThankYouPage();
                    elementsVerificationSteps.testFieldContainsInnerText("Текст 2 Н", "вы сможете забрать карту по адресу: г. архангельск, ул карла маркса, д. 15");
                    elementsVerificationSteps.testFieldContainsInnerText("Текст о статусе карты", "textStatus");
                    break;
                case "В связной":
                    elementsVerificationSteps.elemIsPresentedOnPage("Картинка");
                    elementsVerificationSteps.testFieldContainsInnerText("Заголовок Н", "карта ждёт вас в «связном»");
                    elementsVerificationSteps.testFieldContainsInnerText("Текст Связной", "вы можете уже сейчас обратиться в любой салон сети «связной» с паспортом, и получить карту.");
                    break;
                case "Отделение МВК":
                    elementsVerificationSteps.elemIsPresentedOnPage("Картинка");
                    elementsVerificationSteps.testFieldContainsInnerText("Заголовок Н", "Карта уже ждет вас");
                    elementsVerificationSteps.testFieldContainsInnerText("Текст МВК", "вы можете уже сейчас получить свою карту в\u00A0выбранном отделении банка.");
                    break;
                case "В логистику":
                    elementsVerificationSteps.elemIsPresentedOnPage("Картинка");
                    elementsVerificationSteps.testFieldContainsInnerText("Заголовок Н", "Карта заказана");
                    elementsVerificationSteps.testFieldContainsInnerText("Текст 1 Н", "Дождитесь звонка сотрудника банка для уточнения деталей доставки");
                    elementsVerificationSteps.testFieldContainsInnerText("Текст 2 Н", "Наш сотрудник привезет карту в выбранное место и время");
                    elementsVerificationSteps.testFieldContainsInnerText("Текст о статусе карты", "textStatus");
                    break;
                default:
                    fail("Не верной указан тип доставки");
                    }

            switch (typePlatfotm) {
                case "Десктоп":
                case "Мобайл":
                    isPresentedMobileSoftLink();
                    isPresentedCreditOfferLink();
                    isPresentedbankLink();
                    break;
                case "Айфрейм":
                case "ВебВью":
                    isNotPresentedMobileSoftLink();
                    isNotPresentedCreditOfferLink();
                    isNotPresentedbankLink();
                    break;
                default:
                    fail("Не верно указана платформа отображения");
            }
        } else { //под оператором
            switch (typePage) {
                case "В связной":
                    elementsVerificationSteps.elemIsPresentedOnPage("officeCourierOperatorImage");
                    elementsVerificationSteps.testFieldContainsInnerText("todoHeaderText", "todoTextForOperator");
                    elementsVerificationSteps.testFieldContainsInnerText("step1Text", "step1Text");
                    elementsVerificationSteps.testFieldContainsInnerText("step2Text", "step2TextSviaznoy");
                    elementsVerificationSteps.elemIsPresentedOnPage("Начать новую заявку");
                    break;
                case "Отделение":
                    elementsVerificationSteps.elemIsPresentedOnPage("officeCourierOperatorImage");
                    elementsVerificationSteps.testFieldContainsInnerText("todoHeaderText", "todoTextForOperator");
                    elementsVerificationSteps.testFieldContainsInnerText("step1Text", "step1Text");
                    elementsVerificationSteps.testFieldContainsInnerText("step2Text", "step2TextOffice");
                    elementsVerificationSteps.testFieldContainsInnerText("step3Text", "step3TextOffice");
                    elementsVerificationSteps.testFieldContainsInnerText("step4Text", "step4Text");
                    elementsVerificationSteps.elemIsPresentedOnPage("Начать новую заявку");
                    break;
                case "Курьером":
                    elementsVerificationSteps.elemIsPresentedOnPage("officeCourierOperatorImage");
                    elementsVerificationSteps.testFieldContainsInnerText("todoHeaderText", "todoTextForOperator");
                    elementsVerificationSteps.testFieldContainsInnerText("step1Text", "step1Text");
                    elementsVerificationSteps.testFieldContainsInnerText("step2Text", "step2TextCourier");
                    elementsVerificationSteps.testFieldContainsInnerText("step3Text", "step3TextCourier");
                    elementsVerificationSteps.testFieldContainsInnerText("step4Text", "step4Text");
                    elementsVerificationSteps.elemIsPresentedOnPage("Начать новую заявку");
                    break;
                case "Отделение МВК":
                    elementsVerificationSteps.elemIsPresentedOnPage("officeCourierOperatorImage");
                    elementsVerificationSteps.testFieldContainsInnerText("todoHeaderText", "todoTextForOperator");
                    elementsVerificationSteps.testFieldContainsInnerText("step1Text", "step1Text");
                    elementsVerificationSteps.testFieldContainsInnerText("step2Text", "step2TextMVK");
                    elementsVerificationSteps.testFieldContainsInnerText("step3Text", "step3TextMVK");
                    elementsVerificationSteps.elemIsPresentedOnPage("Начать новую заявку");
                    break;
                case "В логистику":
                    elementsVerificationSteps.elemIsPresentedOnPage("officeCourierOperatorImage");
                    elementsVerificationSteps.testFieldContainsInnerText("todoHeaderText", "todoTextForOperator");
                    elementsVerificationSteps.testFieldContainsInnerText("step1Text", "step1Text");
                    elementsVerificationSteps.testFieldContainsInnerText("step2Text", "step2TextLogistic");
                    elementsVerificationSteps.testFieldContainsInnerText("step3Text", "step3TextLogistic");
                    elementsVerificationSteps.testFieldContainsInnerText("step4Text", "step4Text");
                    elementsVerificationSteps.elemIsPresentedOnPage("Начать новую заявку");
                    break;
                default:
                    fail("Указан тип доставки не описанный для оператора");
            }
        }
    }

    //Проверка что на текущей странице присутствуют ссылки на приложения
    public void isPresentedMobileSoftLink() {
        elementsVerificationSteps.elemIsPresentedOnPage("appleLink");
        elementsVerificationSteps.elemIsPresentedOnPage("androidLink");
    }

    //Проверяем отсутствие ссылок на мобаил
    public void isNotPresentedMobileSoftLink() {
        elementsVerificationSteps.elementIsNotVisible("appleLink");
        elementsVerificationSteps.elementIsNotVisible("androidLink");
    }

    //Проверка что на текущей странице присутствует блок с предложением кредиток
    public void isPresentedCreditOfferLink() {
        elementsVerificationSteps.elemIsPresentedOnPage("creditCardLink");
        elementsVerificationSteps.elemIsPresentedOnPage("creditCardIcon");
        elementsVerificationSteps.elemIsPresentedOnPage("creditCardText");
    }

    //Проверка что на текущей странице отсутствует блок с предложением кредиток
    public void isNotPresentedCreditOfferLink() {
        elementsVerificationSteps.elementIsNotVisible("creditCardLink");
        elementsVerificationSteps.elementIsNotVisible("creditCardIcon");
        elementsVerificationSteps.elementIsNotVisible("creditCardText");
    }

    //Проверка что на текущей странице есть ссылка на  банк
    public void isPresentedbankLink() {
        elementsVerificationSteps.elemIsPresentedOnPage("bankLink");
    }

    //Проверка что на текущей странице отсутствует ссылка на  банк
    public void isNotPresentedbankLink() {
        elementsVerificationSteps.elementIsNotVisible("bankLink");
    }

    public void check1TextInCourierThankYouPage() {
        SelenideElement field = alfaScenario.getCurrentPage().getElement("Текст 1 Н");
        String Value = field.getText();
        assertTrue(Value.contains("Мы сообщим о готовности карты, прислав SMS на номер +7 (···) ··· "));
    }

    //todo 22.02.2019 : U_M112P[ОнищукВВ] - Добавить в Akite поддержку null у переменных
    //Если значение переменной при установке в AkitaScenario было null, то переменная не создается
    @Тогда("^у переменной \"([^\"]*)\" нет значения$")
    public void variableValueIsNull(String variableName) {
        try {
            Object var = alfaScenario.getVar(variableName);
        } catch (IllegalArgumentException e) {
            if (e.getMessage().equals("Переменная " + variableName + " не найдена")) {
                assertTrue(true);
                return;
            } else {
                fail();
            }
        }
        fail();
    }

    //в качестве результата соответствия могут быть true, Да, да, false, нет, Нет.
    @Тогда("^результат проверки соответствия строки \"([^\"]*)\" регулярному выражению \"([^\"]*)\" равен \"([^\"]*)\"$")
    public void regularVariableValueCompare(String variableName, String regularVariableValue, String isCorr) {
        Object name = getCodeWordYandexFromVariable(variableName);
        Pattern compiledPatternRegExp = Pattern.compile(getPropertyOrValue(regularVariableValue));
        Matcher matchResult = compiledPatternRegExp.matcher(name.toString());
        Boolean shouldMatch = convertToBoolean(isCorr);
        assertEquals(matchResult.matches(), shouldMatch);
    }


    public boolean convertToBoolean(String value) {
        if (("false").equals(value) || ("нет").equals(value) || ("Нет").equals(value)) {
            return false;
        }
        return true;
    }


}


