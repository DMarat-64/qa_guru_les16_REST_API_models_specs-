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
            .log().uri()
            .log().body()
            .log().headers()
            .header("x-api-key", apiKey);

    public static ResponseSpecification createUserResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(201)
            .log(STATUS)
            .log(BODY)
            .build();

    public static ResponseSpecification updateResponseUserSpec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(STATUS)
            .log(BODY)
            .build();

    public static ResponseSpecification deleteUserResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(204)
            .log(STATUS)
            .log(BODY)
            .build();

    public static RequestSpecification getListUserSpec = with()
            .filter(withCustomTemplates())
            .log().uri()
            .log().body()
            .log().headers()
            .queryParam("page", "2");
          //  .basePath("/api/users");

    public static ResponseSpecification getListUserResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(ALL)
            .log(BODY)
            .build();

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
