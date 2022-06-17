package scheduler;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import schedulerdto.AuthRequestDto;
import schedulerdto.AuthResponseDto;
import schedulerdto.ErrorScDto;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class LoginTests {
    @BeforeMethod
    public void preCondition(){
        RestAssured.baseURI="https://super-scheduler-app.herokuapp.com/";
        RestAssured.basePath="api";
    }
    @Test
    public void loginSuccess(){
        AuthRequestDto auth=AuthRequestDto.builder()
                .email("Maga1@gmail.com")
                .password("Mm12345$")
                .build();
        AuthResponseDto responseDto = given()
                .body(auth)
                .contentType("application/json")
                .when()
                .post("login")
                .then()
                .assertThat().statusCode(200)
                .extract().response().as(AuthResponseDto.class);

        Assert.assertFalse(responseDto.isRegistration());
        Assert.assertEquals(responseDto.getStatus(),"Login success");
    }

    @Test
    public void loginSuccessNew(){
        String token=given()
                .body(AuthRequestDto.builder().email("Maga1@gmail.com")
                        .password("Mm12345$").build())
                .contentType(ContentType.JSON)
                .when()
                .post("login")
                .then()
                .assertThat().statusCode(200)
                .assertThat().body("status",containsString("Login success"))
                .assertThat().body("registration",equalTo(false))
                .extract().path("token");

        System.out.println(token);
    }

    @Test
    public void loginUnsuccessfulWP(){
        AuthRequestDto auth=AuthRequestDto.builder()
                .email("Maga1@gmail.com")
                .password("Mm123456$")
                .build();
        ErrorScDto response = given()
                .body(auth)
                .contentType(ContentType.JSON)
                .when()
                .post("login")
                .then()
                .assertThat().statusCode(401)
                .extract().response().as(ErrorScDto.class);

        Assert.assertEquals(response.getMessage(),"Wrong email or password");
    }

    @Test
    public void loginUnsuccesOneRow(){
        given().body(AuthRequestDto.builder()
                .email("Maga1@gmail.com")
                .password("Mm123456$")
                .build())
                .contentType(ContentType.JSON)
                .when()
                .post("login")
                .then()
                .assertThat().statusCode(401)
                .assertThat().body("message",containsString("Wrong email or password"));
    }

}
