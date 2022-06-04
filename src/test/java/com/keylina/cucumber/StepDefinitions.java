package com.keylina.cucumber;

import com.keylina.Account;
import com.keylina.pages.GoogleCreateEmailPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class StepDefinitions {

    private Account account;
    private GoogleCreateEmailPage initPage;

    @Given("open url {string}")
    public void openUrl(String url){
        initPage.open(url);
    }

    @Given("account balance is {double}")
    public void givenAccountBalance(Double initialBalance) {
        account = new Account(initialBalance);
    }

    @When("the account is credited with {double}")
    public void whenAccountIsCredited(Double amount) {
        account.credit(amount);
    }

    @Then("account should have a balance of {double}")
    public void thenAccountShouldHaveBalance(Double expectedBalance) {
        assertEquals(expectedBalance, account.getBalance());
    }
}