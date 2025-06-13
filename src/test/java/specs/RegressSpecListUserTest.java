package specs;

import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.*;
import static io.restassured.filter.log.LogDetail.*;


public class RegressSpecListUserTest {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://reqres.in";
    }

    public static RequestSpecification getListUserSpec = with()
            .filter(withCustomTemplates())
            .log().uri()
            .log().body()
            .log().headers()
            .queryParam("page", "2")
            .basePath("/api/users");

    public static ResponseSpecification getListUserResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(ALL)
            .log(BODY)
            .build();
}
