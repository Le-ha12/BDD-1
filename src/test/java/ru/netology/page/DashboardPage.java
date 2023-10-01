package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private static final String balanceStart = "баланс: ";
    private static final String balanceFinish = " р.";
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private static ElementsCollection cards = $$(".list__item");

    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public int getCardBalance(DataHelper.CardDetails cardDetails) {
        var text = cards.findBy(Condition.text(cardDetails.getNumber().substring(12, 16))).getText();
        return extractBalance(text);
    }

    public TransBalancePage selectCardToTransfer(DataHelper.CardDetails cardDetails) {
        cards.findBy(Condition.text(cardDetails.getNumber().substring(12, 16))).$("button").click();
        return new TransBalancePage();
    }

    private int extractBalance(String text) {
        var start = text.indexOf(balanceStart);
        var finish = text.indexOf(balanceFinish);
        var value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }
}