import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class ExtendedFilter {
    private WebDriver driver;
    private WebDriverWait wait;
    private String priceFrom;
    private String priceTo;

    // в коснструкторе задается только начальная и конечная цена в расширенном фильтре
    public ExtendedFilter(WebDriver driver, String priceFrom, String priceTo) {
        this.driver = driver;
        this.priceFrom = priceFrom;
        this.priceTo = priceTo;
    }

    public void useExtendedFilters() {
        // явное ожидание для некоторых операций
        wait = new WebDriverWait(driver, 5);

        // переход на вкладку Закупки
        driver.findElement(By.xpath("//div//ul/li/a[text()='Закупки']")).click();
        // нажать на Расширенный фильтр
        driver.findElement(By.xpath("//div/p/a[text()='Расширенный поиск']")).click();
        // ожидание открытия в фильтре нужных выпадающих списков
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[@id='placingWaysTag']")));    // ожидание элемента Способ определения поставщика
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[@id='orderStages']")));       // ожидание элемента Этап закупки
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[@class='fz44_tag fz94_tag fz223_tag ppRf615_tag search_tag fz223_l_tag divPriceContractSearch']")));  // ожидание элемента Поиск по цене контракта

// заданием параметры для расширенного фильтра
        // первый пункт Способ определения поставщика можно попробовать закоментить, может ничего не найтись на сайте, но с код рабочий
// -------------- раскрыть список Способ определения поставщика list1
        // раскрываем список
        WebElement searchElement2 = driver.findElement(By.xpath("//li[@id='placingWaysTag']/div[@class='manySelect width630 margBtm5 inlineBlock']"));
        searchElement2.click();
        searchElement2.click();
        // ждем появления синей кнопки Выбрать
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[@id='placingWaysTag']/div[1]/div[2]/div[2]/span[text()='Выбрать']")));
        // в List заносятся все элементы в выпдадающем списке
        List<WebElement> list1 = driver.findElements(By.xpath("//li[@id='placingWaysTag']/div/div[2]/div/ul/li/input"));
        System.out.println("Elements in list1 is " + list1.size()); // сколько элементов в выпадающем списке
        // прожимаем по 8 элементу
        list1.get(7).click();
        // клик на кнопку Выбрать
        driver.findElement(By.xpath("//li[@id='placingWaysTag']/div[1]/div[2]/div[2]/span[text()='Выбрать']")).click();
        // ожидание появления нового выпадающего списка Предусмотрены особенности осуществения закупки
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[@id='placingChildWaysTag']")));
// -------------- Новый выпадающий список
        // раскрываем список
        WebElement searchElement4 = driver.findElement(By.xpath("//li[@id='placingChildWaysTag']/div[@class='manySelect width630 margBtm5 inlineBlock']"));
        searchElement4.click();
        searchElement4.click();
        // ждем появление синей кнопки Выбрать
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[@id='placingChildWaysTag']/div/div[2]/div[2]/span[text()='Выбрать']")));
        // в List заносятся все элементы в выпдадающем списке
        List<WebElement> list2 = driver.findElements(By.xpath("//li[@id='placingChildWaysTag']/div/div[2]/div/ul/li/input"));
        System.out.println("Elements in list2 is " + list2.size()); // сколько элементов в выпадающем списке
        // выбираем первое значение в писке Открытый конкурс НИОКР
        list2.get(0).click();
        // нажать Выбрать
        driver.findElement(By.xpath("//li[@id='placingChildWaysTag']/div/div[2]/div[2]/span[text()='Выбрать']")).click();

        // -------------- раскрыть список Этап закупки
        // раскрываем список
        WebElement searchElement3 = driver.findElement(By.xpath("//li[@id='orderStages']/div[1]"));
        searchElement3.click();
        searchElement3.click();
        // ожидаем появления синей кноки Выбрать
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[@id='orderStages']/div/div[2]/div[2]/span[text()='Выбрать']")));
        // в List заносятся все элементы в выпдадающем списке
        List<WebElement> list3 = driver.findElements(By.xpath("//li[@id='orderStages']/div/div[2]/div/ul/li/input"));
        System.out.println("Elements in list3 is " + list3.size()); // сколько элементов в выпадающем списке
        // проклик по нужныем элементам (убираем ненужные)
        for(int i=1; i<list3.size(); i++) { list3.get(i).click(); }
        // нажать Выбрать
        driver.findElement(By.xpath("//li[@id='orderStages']/div/div[2]/div[2]/span[text()='Выбрать']")).click();

        // -------------- установка цены от 100 000 000 до 300 000 000
        WebElement priceSetFrom = driver.findElement(By.xpath("//input[@id='priceFromGeneral']"));
        priceSetFrom.click();
        priceSetFrom.sendKeys(priceFrom); // установка цены ОТ 100 000 000
        WebElement priceSetTo = driver.findElement(By.xpath("//input[@id='priceToGeneral']"));
        priceSetTo.click();
        priceSetTo.sendKeys(priceTo); // установка цены ДО 300 000 000
        // установка Валюты
        WebElement currancyElement1 = driver.findElement(By.xpath("//div[@id='currencyChangecurrencyIdGeneral']"));
        currancyElement1.click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[@style='display: block;']"))));
        driver.findElement(By.xpath("//div[@style='display: block;']/ul/li/span[@id='1']")).click(); // Российский рубль

        // -------------- завершение поиска
        // Нажать Найти
        driver.findElement(By.xpath("//div[@class='floatRight']/span[text()='Найти']")).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div/a[text()='Новый поиск']"))));
        System.out.println("You can start a new search");
    }
}
