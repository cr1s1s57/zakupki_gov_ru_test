import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class MainClass {
    static WebDriver driver;

    public static void main(String[] args) throws IOException {
        // много комменчу, даже самые очевидные вещи, чтобы не ломать голову, что для чего и откуда
        // обратить на путь к драйверу gecko
        System.setProperty("webdriver.gecko.driver", "C:\\java_exercises\\ideaProjects\\ZakupkiGovRu\\drivers\\geckodriver.exe");

        driver = new FirefoxDriver();

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        driver.get("http://www.zakupki.gov.ru");

        // задаем начальные значения для фильтра (только драйвер и цена от, до)
        ExtendedFilter extendedFilter = new ExtendedFilter(driver, "100000000", "300000000");
        // использование метода в классе для применения фильтра
        extendedFilter.useExtendedFilters();
        // создаем объект для класса получения имени подписанта и кода электронной подписи
        CryptoSignCheck crSing = new CryptoSignCheck(driver);
        // получение имени подписанта и кода электронной подписи
        crSing.getCryptoSign();
        // полчучаем нужные данные по открытии каждого пункта из сформированного списка закупок
        GetPurchaseInfo purchaseInfo = new GetPurchaseInfo(driver);
        purchaseInfo.getUsePurchaseInfo();
        // закрыть браузер и выход
        driver.quit();
    }
}
