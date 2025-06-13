package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class RegressTests extends TestBase {
    public int validUserId = 2;
    public int notValidUserId = 333;
    public String expectedEmail = "janet.weaver@reqres.in";

    @Test
    @DisplayName("Получение списка пользователей")
    public void getListUser (){
        given()
                .log().uri()
                .when()
                .queryParam("page", "2")
                .get("/users")
                .then()
                .statusCode(200)
                .body("page", equalTo(2))
                .log().all();

    }

    @Test
    @DisplayName("Получение одиночного пользователя")
    public void getSingleUser (){
        given()
                .log().uri()
                .log().method()
                .log().body()
                .header("x-api-key", apiKey)
                .when()
                .get(usersEndpoint + validUserId)
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.id", is(2))
                .body("data.email", is(expectedEmail));

    }
    @Test
    @DisplayName("Ошибка при получении одиночного пользователя")
    public void getNotSingleUser (){
        given()
                .log().uri()
                .log().method()
                .log().body()
                .header("x-api-key", apiKey)
                .when()
                .get(usersEndpoint + notValidUserId)
                .then()
                .log().status()
                .log().body()
                .statusCode(404)
                .body(equalTo("{}"));


    }

    @Test
    @DisplayName("Создание пользователя")
    void successfulCreateUser() {
        String authData = "{\"name\": \"Jon\", \"job\": \"teacher\"}";

        given()
                .body(authData)
                .contentType(JSON)
                .log().uri()
                .header("x-api-key", apiKey)
                .when()
                .post(createUser)
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("Jon"));
    }
    @Test
    @DisplayName("Изменение пользователя, метод PUT")
    void successfulPutUpdateUser() {
        String authData = "{\"name\": \"Joe Black\", \"job\": \"teacher\"}";

        given()
                .body(authData)
                .contentType(JSON)
                .log().uri()
                .header("x-api-key", apiKey)
                .when()
                .put(updateUser)
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("name", is("Joe Black"));
    }
    @Test
    @DisplayName("Частичное изменение пользователя, метод PATCH")
    void successfulPatchUpdateUser() {
        String authData = "{\"name\": \"Joe Black\", \"job\": \"teacher\"}";

        given()
                .body(authData)
                .contentType(JSON)
                .log().uri()
                .header("x-api-key", apiKey)
                .when()
                .patch(updateUser)
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("name", is("Joe Black"));
    }
    @Test
    @DisplayName("Удаление пользователя")
    void successfulDeleteUser() {
        given()
                .header("x-api-key", apiKey)
                .when()
                .delete(updateUser)
                .then()
                .log().status()
                .log().body()
                .statusCode(204);

    }
}
