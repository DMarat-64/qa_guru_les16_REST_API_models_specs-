package tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {

    public String createUser = "/users";
    public String updateUser = "/users/2";
    public static String apiKey = "reqres-free-v1";
    public String usersEndpoint = "/users/";

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }
}
