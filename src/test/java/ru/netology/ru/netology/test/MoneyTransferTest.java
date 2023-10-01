package ru.netology.ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static com.codeborne.selenide.Selenide.open;

public class MoneyTransferTest {
    @Test
    void shouldTransferMoneyBetweenOwnCards() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var cardInfoFirst = DataHelper.getFirstCardDetails();
        var cardInfoSecond = DataHelper.getSecondCardDetails();
        var firstCardDetails = dashboardPage.getCardBalance(cardInfoFirst);
        var secondCardDetails = dashboardPage.getCardBalance(cardInfoSecond);

        int amount = 1000;
        var expectedFirstCardBalance = firstCardDetails + amount;
        var expectedSecondCardBalance = secondCardDetails - amount;
        var transBalancePage = dashboardPage.selectCardToTransfer(cardInfoFirst);
        transBalancePage.validTransfer(String.valueOf(amount), cardInfoSecond);

        assertEquals(expectedFirstCardBalance, dashboardPage.getCardBalance(cardInfoFirst));
        assertEquals(expectedSecondCardBalance, dashboardPage.getCardBalance(cardInfoSecond));
    }

    @Test
    void shouldTransferFromSecondToFirst() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var autInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(autInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(autInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var cardInfoSecond = DataHelper.getSecondCardDetails();
        var cardInfoFirst = DataHelper.getFirstCardDetails();
        var firstCardDetails = dashboardPage.getCardBalance(cardInfoFirst);
        var secondCardDetails = dashboardPage.getCardBalance(cardInfoSecond);

        int amount = 10000;
        var expectedFirstCardBalance = firstCardDetails - amount;
        var expectedSecondCardBalance = secondCardDetails + amount;

        var transBalancePage = dashboardPage.selectCardToTransfer(cardInfoSecond);
        transBalancePage.validTransfer(String.valueOf(amount), cardInfoFirst);

        assertEquals(expectedFirstCardBalance, dashboardPage.getCardBalance(cardInfoFirst));
        assertEquals(expectedSecondCardBalance, dashboardPage.getCardBalance(cardInfoSecond));

    }
    @Test
    void shouldFailToAuthorizeWithInvalidAuthData() {
        open("http://localhost:9999");
        val loginPage = new LoginPage();
        val badAuthInfo = DataHelper.getOtherAuthInfo(DataHelper.getAuthInfo());
        loginPage.invalidVerify(badAuthInfo);
    }

    @Test
    void shouldFailToAuthorizeWithInvalidVerificationCode() {
        open("http://localhost:9999");
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val badVerificationCode = DataHelper.getVerificatioonCodeInvalid(authInfo);
        verificationPage.invalidVerify(badVerificationCode);
    }
}
