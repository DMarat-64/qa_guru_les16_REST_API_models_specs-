package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.*;
import static io.restassured.http.ContentType.JSON;
import static tests.TestBase.apiKey;

public class RegressSpecTest {
    public static RequestSpecification userSpec = with()
            .filter(withCustomTemplates())
            .contentType(JSON)
            .log().all()
            .header("x-api-key", apiKey);

    public static ResponseSpecification createUserResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(201)
            .log(ALL)
            .log(BODY)
            .build();

    public static ResponseSpecification responseSpec200 = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(ALL)
            .log(BODY)
            .build();

    public static ResponseSpecification deleteUserResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(204)
            .log(ALL)
            .log(BODY)
            .build();

    public static RequestSpecification getListUserSpec = with()
            .filter(withCustomTemplates())
            .log().all();

    public static RequestSpecification getSingleUserSpec = with()
            .filter(withCustomTemplates())
            .log().all()
            .header("x-api-key", apiKey);

    public static ResponseSpecification getNotSingleUserResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(404)
            .log(ALL)
            .log(BODY)
            .build();
}
