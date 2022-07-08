package ru.alfabank.pages;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import org.openqa.selenium.support.FindBy;
import ru.alfabank.alfatest.cucumber.annotations.Name;
import ru.alfabank.alfatest.cucumber.annotations.Optional;
import ru.alfabank.alfatest.cucumber.api.AkitaPage;


@Getter
@Name("Дебетовая анкета шаг 1")
public class FirstPage extends AkitaPage {

    @FindBy(xpath = "//*[@name='lastName']")
    @Name("Фамилия")
    private SelenideElement inputLastName;

    @FindBy(xpath = "//*[@name='firstName']")
    @Name("Имя")
    private SelenideElement inputFirstName;

    @FindBy(xpath = "//*[@name='middleName']")
    @Name("Отчество")
    private SelenideElement inputMiddleName;

    @Optional
    @FindBy(xpath = "//*[@class='radio-group__inner']")
    @Name("Пол")
    private SelenideElement inputGenderContainer;

    @Optional
    @FindBy(xpath = "//*[text()='Женский']/ancestor::button")
    @Name("Пол_ж")
    private SelenideElement inputGenderButtonF;

    @Optional
    @FindBy(xpath = "//*[text()='Мужской']/ancestor::button")
    @Name("Пол_м")
    private SelenideElement inputGenderButtonM;

    @FindBy(xpath = "//*[@name='phone']")
    @Name("Телефон")
    private SelenideElement inputMobilePhone;

    @Optional
    @FindBy(xpath = "//*[@name='phone']/ancestor::span[contains(@class, 'input__inner')]/child::span[contains(@class, 'input__sub')]/div")
    @Name("Телефон валидация")
    private SelenideElement validationMobilePhone;

    @Optional
    @FindBy(xpath = "//*[@name='phone']/ancestor::span[3]")
    @Name("Телефон обертка")
    private SelenideElement wrapperClassMobilePhone;

    @FindBy(xpath = "//*[@name='email']")
    @Name("Емейл")
    private SelenideElement inputPersonalEmail;

    @Optional
    @FindBy(xpath = "//*[@name='email']/ancestor::span[contains(@class, 'input__inner')]/child::span[contains(@class, 'input__sub')]/div")
    @Name("Валидация Емейл")
    private SelenideElement validationPersonalEmail;

    @Optional
    @FindBy(xpath = "//*[@name='email']/ancestor::span[3]")
    @Name("Емейл обертка")
    private SelenideElement wrapperClassPersonalEmail;

    @Optional
    @FindBy (xpath = "(//div[contains(@class, 'input__menu')])[1]")
    @Name("Выпадающий список фамилия")
    private SelenideElement popupLastname;

    @Optional
    @FindBy (xpath = "(//div[contains(@class, 'input__menu')])[2]")
    @Name("Выпадающий список имя")
    private SelenideElement popupFirstname;

    @Optional
    @FindBy (xpath = "(//div[contains(@class, 'input__menu')])[3]")
    @Name("Выпадающий список отчество")
    private SelenideElement popupMiddlename;

    @Optional
    @FindBy (xpath = "//div[@data-for='email']")
    @Name("Выпадающий список емейл")
    private SelenideElement popupEemail;

    @Optional
    @FindBy (css = ".checkbox")
    @Name("Флаг СОПД")
    private SelenideElement sopdCheckbox;

    @Optional
    @FindBy (css = ".consent-checkbox_invalid")
    @Name("Флаг СОПД не отмечен")
    private SelenideElement sopdCheckboxInvalid;

    @Optional
    @FindBy (css = ".checkbox_checked")
    @Name("Флаг СОПД отмечен")
    private SelenideElement sopdCheckboxChecked;

    @FindBy(xpath = "(//*[@class='link__text'])[1]")
    @Name("Условия")
    private SelenideElement linkTextConditions;

    @FindBy(xpath = "(//*[@class='link__text'])[2]")
    @Name("Согласие")
    private SelenideElement linkTextconsent;

    @Optional
    @FindBy(xpath = "//*[@target='_blank']")
    @Name("Обратная связь")
    private SelenideElement buttonFeedback;

    @FindBy(xpath = "//*[text()='Продолжить']")
    @Name("Продолжить")
    private SelenideElement buttonSubmit;

    @Optional
    @FindBy(xpath = "//*[@name='appId']")
    @Name("Текущая заявка")
    private SelenideElement appIdValue;

    @Optional
    @FindBy(xpath = "(//*[contains(@class,'autofill')]//button)[1]")
    @Name("Выбор клиента")
    private SelenideElement inputAutofillClient;

    @Optional
    @FindBy(xpath = "//*[@class='popup__inner']//div[contains(@class, 'select__menu')]")
    @Name("Выпадающий список данных клиента")
    private SelenideElement popupAutofillClient;

    @Optional
    @FindBy(xpath = "//footer")
    @Name("Footer")
    private SelenideElement footer;

    @Optional
    @FindBy(xpath = "//div[contains(@class, 'top top_tiny_true top_theme_alfa-on-white')]")
    @Name("Header tiny")
    private SelenideElement header_tiny;

    @Optional
    @FindBy(xpath = "//div[contains(@class, 'top top_theme_alfa-on-white')]")
    @Name("Header")
    private SelenideElement header;

    @Optional
    @FindBy(xpath = "//*[@data-test-id=\"form-header\"]")
    @Name("Заголовок страницы")
    private SelenideElement headerPage;

    /////////////////////////////////////////////////////////////////////////////
    /////               Предпросмотр заказываемой карты                     /////
    /////////////////////////////////////////////////////////////////////////////

    @Optional
    @FindBy(xpath = "//*[@class='card-preview__image']")
    @Name("Изображение заказываемой карты")
    private SelenideElement card_preview__image;

    @Optional
    @FindBy(xpath = "//*[contains(@class,'card-preview__header')]")
    @Name("Название заказываемой карты")
    private SelenideElement card_preview__header;

    @Optional
    @FindBy(xpath = "//*[contains(@class,'card-preview__list')]")
    @Name("Описание заказываемой карты")
    private SelenideElement card_preview__list;

    /////////////////////////////////////////////////////////////////////////////
    /////               Прочие элементы                                     /////
    /////////////////////////////////////////////////////////////////////////////
    @Optional
    @FindBy(xpath = "//*[text()='Начать новую заявку']")
    @Name("Начать новую заявку")
    private SelenideElement newAppButton;

    @FindBy(css = ".security__icon")
    @Name("Иконка гарантии безопасности")
    private SelenideElement securityIcon;

    @FindBy(css = ".security__caption")
    @Name("Текст гарантии безопасности")
    private SelenideElement securityCaption;

    @FindBy(css = ".form-progress__text")
    @Name("Текст прогресс бара")
    private SelenideElement progressText;

    /////////////////////////////////////////////////////////////////////////////
    /////               Плашка что карта недоступна                         /////
    /////////////////////////////////////////////////////////////////////////////
    @Optional
    @FindBy(css = ".warning-plate")
    @Name("Плашка карта недоступна")
    private SelenideElement CardUnavaliablePlate;

    @Optional
    @FindBy(css = ".warning-plate__heading")
    @Name("Заголовок карта недоступна")
    private SelenideElement CardUnavaliableHeader;

    @Optional
    @FindBy(css = ".warning-plate__description")
    @Name("Описание карта недоступна")
    private SelenideElement CardUnavaliableDescription;

    @Optional
    @FindBy(css = ".warning-plate__link")
    @Name("Ссылка перейти на сайт alfabank")
    private SelenideElement CardUnavaliableLink;

    /////////////////////////////////////////////////////////////////////////////
    /////               Промокод                                            /////
    /////////////////////////////////////////////////////////////////////////////

    @Optional
    @FindBy(xpath = "//input[@name='promocode']")
    @Name("Промокод")
    private SelenideElement inputPromocode;

    @Optional
    @FindBy(xpath = "//*[@name='hasPromocode'][@value='no']//ancestor::label[contains(@class, 'radio')]")
    @Name("Нет промокода")
    private SelenideElement buttonHasPromocodeNo;

    @Optional
    @FindBy(xpath = "//*[@name='hasPromocode'][@value='yes']//ancestor::label[contains(@class, 'radio')]")
    @Name("Есть промокод")
    private SelenideElement buttonHasPromocodeYes;

    @Optional
    @FindBy(xpath = "//*[contains(@class, 'radio-group_name_hasPromocode')]//*[contains(@class, 'radio_checked')]")
    @Name("Текущее значение кнопки наличия промокода")
    private SelenideElement inputActiveChangePromocode;







}
