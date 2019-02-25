import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CryptoSignCheck {
    private WebDriver driver;
    private WebDriverWait wait;

    // в констркторе только драйвер
    public CryptoSignCheck(WebDriver driver) { this.driver = driver; }

    public void getCryptoSign() throws IOException {
        String s1, // ФИО подписанта
                s2; // текст электронной подписи
        // явное ожидание для некоторых операций
        wait = new WebDriverWait(driver, 5);

        // стандартный райтер для вывода полученых данных в тхт файл
        FileWriter writer = new FileWriter("test.txt", false);

        // массив кнопок для получения инфы электронной подписи
        List<WebElement> signList1 = driver.findElements(By.xpath("//div[@class='parametrs margBtm10']/div[@class='registerBox registerBoxBank margBtm20']/div/a[@class='cryptoSignLink linkPopUp pWidth_840']"));
        System.out.println(signList1.size()); // вывод количества кнопок присутствующих на странице в данный момент (должно быть 10 по умолчанию, но можно получить до 50)

        for(int i=0; i<2; i++) {
            // практичсески всегда при нажатии на некоторые кнопки подписи, сайт кидает сообщение об ошибке
            signList1.get(i).click(); // клик по кнопке
            // ожидание полявления окна
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='modal-content modal-content-edsContainer edsContainer']")));
            // получение имени подписанта
            s1 = driver.findElement(By.xpath("//div[@class='modal-text-block']/div[1]/h3")).getText();
            // показать электронную подпись
            driver.findElement(By.xpath("//div/table/tbody/tr/td/span[@class='expandTr']")).click();
            // ожидание раскрытия текста
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div/table/tbody/tr[@class='toggleTr expandRow']")));
            // получение текста подписи
            s2 = driver.findElement(By.xpath("//div/table/tbody/tr[2]/td/div")).getText();

            // запись полученого имени и подписи в файл
            try {
                writer.write("Имя подписанта\r\n");
                writer.write(s1);
                writer.append("\r\n");
                writer.write("Электронная подпись\r\n");
                writer.write(s2);
                writer.append("\r\n\r\n");
                writer.flush();
            } catch(IOException ex) {
                System.out.println(ex.getMessage());
            }

            // закрыть окно
            driver.findElement(By.xpath("//span[@class='btn-close closePopUp']")).click();
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div/a[text()='Новый поиск']"))));
        }
        writer.close();
    }
}
