import io.github.bonigarcia.wdm.WebDriverManager;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.jayway.restassured.RestAssured.given;

public class DemoQa {
WebDriver wd;
@BeforeMethod
    public void init(){
    WebDriverManager.chromedriver().setup();
    wd=new ChromeDriver();
    wd.manage().window().maximize();
    wd.navigate().to("https://demoqa.com/broken");
    wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
}
@Test
    public void brokenLinks(){
    checkBrokenLinks();
}

    private void checkBrokenLinks() {
        List<WebElement> list = wd.findElements(By.xpath("//div[@class='col-12 mt-4 col-md-6']//a[@href]"));
        for (WebElement el:list) {
            String url=el.getAttribute("href");

            verifyLinkRestAssured(url);
            
            verifyLinkOkHttp(url);
        }
    }

    private void verifyLinkOkHttp(String url) {
        OkHttpClient client=new OkHttpClient();
        try{
            Request request=new Request.Builder()
                    .url(url)
                    .head()
                    .build();

            Response response = client.newCall(request).execute();

            if(response.isSuccessful()){
                System.out.println(url+" code "+response.code() +" isn't broken");
            }else{
                System.out.println(url+" code "+response.code() +" is broken");
            }

        }catch(Exception e){
            System.out.println(url);
            System.out.println("Your error--->"+e.getMessage());
        }
    }

    private void verifyLinkRestAssured(String url) {
    try{
        int code=given().when()
                .head(url)
                .then()
                .extract().statusCode();

        if(code==200){
            System.out.println(url+" code "+code +" isn't broken");
        }else {
            System.out.println(url+" code "+code +" is broken");
        }
    }catch(Exception e){
        System.out.println(url);
        System.out.println("Your error--->"+e.getMessage());
    }
    }
}
