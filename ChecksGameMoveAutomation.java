import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.List;
public class CheckersGameAutomation {
    private WebDriver driver;
    @BeforeClass
    public void setup() {
        // Set the path to the ChromeDriver executable
       System.setProperty(&quot;webdriver.chrome.driver&quot;, &quot;path/to/chromedriver&quot;);
        // Launch the Chrome browser
        driver = new ChromeDriver();
        // Step 1: Navigate to the website
       driver.get(&quot;https://www.gamesforthebrain.com/game/checkers/&quot;);
    }
    @Test
    public void checkersGameTest() {
        // Step 2: Confirm that the site is up
        WebElement pageHeader =
driver.findElement(By.xpath(&quot;//h1[contains(text(),&#39;Checkers&#39;)]&quot;));
       Assert.assertTrue(pageHeader.isDisplayed(), &quot;Page is not up and running&quot;);
        List&lt;String[]&gt; moves = new ArrayList&lt;&gt;();
        moves.add(new String[]{&quot;space62&quot;, &quot;space53&quot;});
        moves.add(new String[]{&quot;space71&quot;, &quot;space62&quot;});
        moves.add(new String[]{&quot;space71&quot;, &quot;space62&quot;});
        moves.add(new String[]{&quot;space22&quot;, &quot;space13&quot;});
        moves.add(new String[]{&quot;space42&quot;, &quot;space33&quot;});
        for (String[] move : moves) {

            makeMove(move[0], move[1]);
            validateMakeMoveButton();
        }
        restartGame();
    }
    private void makeMove(String moveFrom, String moveTo) {
        WebElement moveElement = driver.findElement(By.xpath(&quot;//img[@name=&#39;&quot; + moveFrom
+ &quot;&#39;]&quot;));
        WebElement moveToElement = driver.findElement(By.xpath(&quot;//img[@name=&#39;&quot; + moveTo +
&quot;&#39;]&quot;));
         moveElement.click ();
         moveToElement.click ();
    }
    private void validateMakeMoveButton() {
        WebDriverWait wait = new WebDriverWait(driver, 2);
        WebElement makeMoveText =
wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(&quot;//p[contains(text(),&#39;Make
a move&#39;)]&quot;)));
        Assert.assertTrue(makeMoveText.isDisplayed(), &quot;Make a move button is not displayed.&quot;);
    }
    private void restartGame() {
        WebElement restartGame = driver.findElement(By.xpath(&quot;//a[contains(text(),&#39;
Restart...&#39;)]&quot;));
         restartGame.click ();
    }
    @AfterClass
    public void teardown() {
        // Close the browser
        driver.quit();
    }
}
