package tests;

import models.lombok.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegressTests extends TestBase {
    public int validUserId = 2;
    public int notValidUserId = 333;
    public String expectedEmail = "janet.weaver@reqres.in";

    @Test
    @DisplayName("Получение списка пользователей")
    public void getListUser (){
        getResponseListUserModels response = step("Получение списка пользователей",()->
        given()
                .filter(withCustomTemplates())
                .log().uri()
        .when()
                .queryParam("page", "2")
                .get("/users")
        .then()
                .statusCode(200)
                .log().all()
                .extract().as(getResponseListUserModels.class));

        step("Проверка ответа", () -> {
            assertEquals(2, response.getPage());
        });
    }

    @Test
    @DisplayName("Получение одиночного пользователя")
    public void getSingleUser (){
        getResponseSingleUserModels response = step("Получение одиночного пользователя",()->
        given()
                .filter(withCustomTemplates())
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
                .extract().as(getResponseSingleUserModels.class));

        step("Проверка ответа", () -> {
            assertEquals(validUserId, response.getData().getId());
            assertEquals(expectedEmail, response.getData().getEmail());
        });
    }

    @Test
    @DisplayName("Ошибка при получении одиночного пользователя")
    public void getNotSingleUser (){
        getResponseSingleUserModels response = step("Ошибка при получении одиночного пользователя",()->
        given()
                .filter(withCustomTemplates())
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
                .extract().as(getResponseSingleUserModels.class));

        step("CПроверка ответа", () ->
            assertEquals(null,response.getData()));
    }

    @Test
    @DisplayName("Создание пользователя")
    void successfulCreateUser() {
        createBodyUserModels authData = new createBodyUserModels();
        authData.setName("Jon");
        authData.setJob("teacher");

        createResponseUserModels response = step("Ответ о создании пользователя", () ->
        given()
                .filter(withCustomTemplates())
                .body(authData)
                .contentType(JSON)
                .log().uri()
                .log().body()
                .log().headers()
                .header("x-api-key", apiKey)
        .when()
                .post(createUser)
        .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .extract().as(createResponseUserModels.class));

        step("Проверка ответа", ()-> {
            assertEquals("Jon", response.getName());
            assertEquals("teacher", response.getJob());
        });
    }

    @Test
    @DisplayName("Изменение пользователя, метод PUT")
    void successfulPutUpdateUser() {
        updateBodyUserModels authData = new updateBodyUserModels();
        authData.setName("Joe Black");
        authData.setJob("teacher");


        updateResponseUserModels response = step("Ответ об изменении пользователя", ()->
        given()
                .filter(withCustomTemplates())
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
                .extract().as(updateResponseUserModels.class));

        step("Проверка ответа", ()-> {
            assertEquals("Joe Black", response.getName());
            assertEquals("teacher", response.getJob());
        });
    }

    @Test
    @DisplayName("Частичное изменение пользователя, метод PATCH")
    void successfulPatchUpdateUser() {
        updateBodyUserModels authData = new updateBodyUserModels();
        authData.setName("Joe Black");
        authData.setJob("director");

        updateResponseUserModels response = step("Ответ об частичном изменении пользователя", ()->
        given()
                        .filter(withCustomTemplates())
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
                        .extract().as(updateResponseUserModels.class));

        step("Проверка ответа", ()-> {
            assertEquals("Joe Black", response.getName());
            assertEquals("director", response.getJob());
        });
    }

    @Test
    @DisplayName("Удаление пользователя")
    void successfulDeleteUser() {
        String response = step("Запрос удаления пользователя", () ->
        given()
                .header("x-api-key", apiKey)
        .when()
                .delete(updateUser)
        .then()
                .log().status()
                .log().body()
                .statusCode(204)
                .extract().asString());

        step("Проверка ответа", () -> {
            assertEquals("", response, "Ответ пустой");
        });
    }
}
