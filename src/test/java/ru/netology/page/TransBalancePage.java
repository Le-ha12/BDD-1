package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

import static java.time.Duration.ofSeconds;

public class TransBalancePage {
    private SelenideElement addReplenishmentSection = $(withText("Пополнение карты"));
    private SelenideElement amount = $("[data-test-id='amount'] input");
    private SelenideElement transferButton = $("[data-test-id='action-transfer']");
    private SelenideElement from = $("[data-test-id='from'] input");
    private SelenideElement actionError = $("[data-test-id='error-notification']");

    public TransBalancePage() {
        addReplenishmentSection.shouldBe(visible, ofSeconds(10));
    }

    public DashboardPage madeTransfer(int amountForTransfer, DataHelper.CardDetails cardDetails) {
        amount.setValue(String.valueOf(amountForTransfer));
        from.setValue(cardDetails.getNumber());
        transferButton.click();
        return new DashboardPage();
    }
    public DashboardPage validTransfer(String amountTransfer, DataHelper.CardDetails cardInfo) {
        amount.setValue(amountTransfer);
        from.setValue(cardInfo.getNumber());
        transferButton.click();
        return new DashboardPage();
    }
    public void findErrorMessage(String expectedText) {
        actionError.shouldHave(exactText(expectedText), Duration.ofSeconds(20)).shouldBe(visible);
    }
}
