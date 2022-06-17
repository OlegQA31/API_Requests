package scheduler;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import org.jetbrains.annotations.TestOnly;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import schedulerdto.DateDto;
import schedulerdto.RecordsDto;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class DeleteRecordByIdTests {
    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Ik1hZ2ExQGdtYWlsLmNvbSJ9.4l4fX1XUamDaCsupTro3jAS9Hla5kGBqDPIrnkiP2yg";
    int id;

    @BeforeMethod
    public void preCondition() {
        RestAssured.baseURI = "https://super-scheduler-app.herokuapp.com/";
        RestAssured.basePath = "api";

        RecordsDto record = RecordsDto.builder()
                .breaks(1)
                .currency("ILS")
                .date(DateDto.builder()
                        .dayOfMonth(11)
                        .dayOfWeek("1")
                        .month(2)
                        .year(2022)
                        .build())
                .hours(4)
                .id(0)
                .timeFrom("18:00")
                .timeTo("21:00")
                .title("Title")
                .type("Type")
                .totalSalary(300)
                .wage(100)
                .build();

        id = given()
                .body(record)
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .when()
                .post("record")
                .then()
                .assertThat().statusCode(200)
                .extract().path("id");
    }

    @Test
    public void deleteRecordById() {
        String str = given()
                .header("Authorization", token)
                .when()
                .delete("record/" + id)
                .then()
                .assertThat().statusCode(200)
                .assertThat().body("status", containsString("was deleted"))
                .extract().path("status");
        System.out.println(str);
    }

    @Test
    public void deleteRecordByWrongId() {
        String str = given()
                .header("Authorization", token)
                .when()
                .delete("record/" + 11)
                .then()
                .assertThat().statusCode(400)
                .assertThat().body("message", containsString("Record with id 11 doesn't exist!"))
                .extract().path("message");
        System.out.println(str);
    }
}
