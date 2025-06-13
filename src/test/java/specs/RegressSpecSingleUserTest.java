package specs;

import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;
import tests.TestBase;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.filter.log.LogDetail.BODY;


public class RegressSpecSingleUserTest extends TestBase {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://reqres.in";
    }

    public static RequestSpecification getSingleUserSpec = with()
            .filter(withCustomTemplates())
            .log().uri()
            .log().method()
            .log().body()
            .header("x-api-key", apiKey);

    public static ResponseSpecification getSingleUserResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(ALL)
            .log(BODY)
            .build();

    public static ResponseSpecification getNotSingleUserResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(404)
            .log(ALL)
            .log(BODY)
            .build();
}
