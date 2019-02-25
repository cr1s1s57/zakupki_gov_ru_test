import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class GetPurchaseInfo {
    private WebDriver driver;
    private WebDriverWait wait;

    public GetPurchaseInfo(WebDriver driver) {
        this.driver = driver;
    }

    public void getUsePurchaseInfo() throws IOException {
        // ожидание для некоторых операций
        wait = new WebDriverWait(driver, 5);

        // стандартный райтер для записи в тхт файл полученых данных
        FileWriter writer = new FileWriter("testTextFile.txt", false);

        // получение массива всех лотов на начальной странице
        List<WebElement> lotList1 = driver.findElements(By.xpath("//div[@class='registerBox registerBoxBank margBtm20']//dt/a"));
        // проверяем размер (не обязательно)
        System.out.println("check size of lotList1 " + lotList1.size());

        // в данном цикле (выставлено на 7 итераций) проиходит проход по каждой найденной позиции из сформированного списка
        // тут выбирается только контактное лицо и заносится в файл
        // в условии может быть как //td, так и //td/span. если условие не совпадает, проиходит выход из цикла и программа закрывается с ошибкой.
        // можно цеплять больше условий, используя нужный синтаксис xpath (записываются в втроки s1 - s5, но тут только s1, если нужно могу добавить)
        for(int i=0; i<7; i++) {
            lotList1.get(i).click();
            String parentWindowHandler = driver.getWindowHandle(); // для возврата в исходное окно
            // переход в открывшуюся вкладку
            driver.switchTo().window(new WebDriverWait(driver, 10).until(newWindowForm(By.xpath("//table[@class='contentTabsWrapper']"))));
            List<WebElement> headers = driver.findElements(By.xpath("//h2"));
            // String s1 - фио
            if((driver.findElement(By.xpath("//td[contains(text(), 'Контактное лицо')]")).isEnabled())) {
                String s1 = driver.findElement(By.xpath("//td[contains(text(), 'Контактное лицо')]/following-sibling::td")).getText();
                System.out.println("if "+s1);
                try {
                    writer.write("Имя подписанта\r\n");
                    writer.write(s1);
                    writer.append("\r\n");
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            } else if(driver.findElement(By.xpath("//td[contains(text(), 'Ответственное должностное лицо')]")).isEnabled()){
                String s1 = driver.findElement(By.xpath("//td[contains(text(), 'Ответственное должностное лицо')]/following-sibling::td")).getText();
                System.out.println("else "+s1);
                try {
                    writer.write("Имя подписанта\r\n");
                    writer.write(s1);
                    writer.append("\r\n");
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            // закрываем вкладку и переходим в стартовое окно
            driver.close();
            driver.switchTo().window(parentWindowHandler); // переключаемся на исходное окно
        }
        // прекращаем запись
        writer.close();
    }

    // метод для работы с вкладкой браузера
    public static ExpectedCondition<String> newWindowForm(final By locator) {
        return new ExpectedCondition<String>() {
            @NullableDecl
            public String apply(@NullableDecl WebDriver driver) {
                String initialWindowHandle = driver.getWindowHandle(); // запомнить в начале в каком окне находимся
                String found = null;
                Set<String> windowHandles = driver.getWindowHandles(); // возвращаем множество идентификаторов и в цикле потом проходим каждое окно и запоминаем
                // имеется ли необходимый элемент в новом окне, нет - тогда переключаемся в следующее, если совпадает, то true
                for (String handle : windowHandles) {
                    try {
                        driver.switchTo().window(handle);
                        if (driver.findElement((locator)).isDisplayed()) {
                            found = handle;
                            break;
                        }
                    } catch (WebDriverException e) { // игнорируем все исключения
                    }
                }
                driver.switchTo().window(initialWindowHandle);
                return found;
            }
        };
    }
}
