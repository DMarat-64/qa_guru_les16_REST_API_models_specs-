package tests;

import models.lombok.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.RegressSpecTest.*;

public class RegressTests extends TestBase {
    public int validUserId = 2;
    public int notValidUserId = 333;
    public String expectedEmail = "janet.weaver@reqres.in";

    @Test
    @DisplayName("Получение списка пользователей")
    public void getListUser() {
        getResponseListUserModels response = step("Получение списка пользователей", () ->
                given(getListUserSpec)

                        .when()
                        .queryParam("page", "2")
                        .get("/users")
                        .then()
                        .spec(responseSpec200)
                        .extract().as(getResponseListUserModels.class));

        step("Проверка ответа", () -> {
            assertEquals(2, response.getPage());
        });
    }

    @Test
    @DisplayName("Получение одиночного пользователя")
    public void getSingleUser() {
        getResponseSingleUserModels response = step("Получение одиночного пользователя", () ->
                given(getSingleUserSpec)

                        .when()
                        .get(usersEndpoint + validUserId)
                        .then()
                        .spec(responseSpec200)
                        .extract().as(getResponseSingleUserModels.class));

        step("Проверка ответа", () -> {
            assertEquals(validUserId, response.getData().getId());
            assertEquals(expectedEmail, response.getData().getEmail());
        });
    }

    @Test
    @DisplayName("Ошибка при получении одиночного пользователя")
    public void getNotSingleUser() {
        getResponseSingleUserModels response = step("Ошибка при получении одиночного пользователя", () ->
                given(getSingleUserSpec)

                        .when()
                        .get(usersEndpoint + notValidUserId)
                        .then()
                        .spec(getNotSingleUserResponseSpec)
                        .extract().as(getResponseSingleUserModels.class));

        step("Проверка ответа", () ->
                assertEquals(null, response.getData()));
    }

    @Test
    @DisplayName("Создание пользователя")
    void successfulCreateUser() {
        testBodyUserModels authData = new testBodyUserModels();
        authData.setName("Jon");
        authData.setJob("teacher");

        createResponseUserModels response = step("Ответ о создании пользователя", () ->
                given(userSpec)
                        .body(authData)
                        .when()
                        .post(createUser)
                        .then()
                        .spec(createUserResponseSpec)
                        .extract().as(createResponseUserModels.class));

        step("Проверка ответа", () -> {
            assertEquals("Jon", response.getName());
            assertEquals("teacher", response.getJob());
        });
    }

    @Test
    @DisplayName("Изменение пользователя, метод PUT")
    void successfulPutUpdateUser() {
        testBodyUserModels authData = new testBodyUserModels();
        authData.setName("Joe Black");
        authData.setJob("teacher");


        updateResponseUserModels response = step("Ответ об изменении пользователя", () ->
                given(userSpec)
                        .body(authData)
                        .when()
                        .put(updateUser)
                        .then()
                        .spec(responseSpec200)
                        .extract().as(updateResponseUserModels.class));

        step("Проверка ответа", () -> {
            assertEquals("Joe Black", response.getName());
            assertEquals("teacher", response.getJob());
        });
    }

    @Test
    @DisplayName("Частичное изменение пользователя, метод PATCH")
    void successfulPatchUpdateUser() {
        testBodyUserModels authData = new testBodyUserModels();
        authData.setName("Joe Black");
        authData.setJob("director");

        updateResponseUserModels response = step("Ответ об частичном изменении пользователя", () ->
                given(userSpec)
                        .body(authData)
                        .when()
                        .put(updateUser)
                        .then()
                        .spec(responseSpec200)
                        .extract().as(updateResponseUserModels.class));

        step("Проверка ответа", () -> {
            assertEquals("Joe Black", response.getName());
            assertEquals("director", response.getJob());
        });
    }

    @Test
    @DisplayName("Удаление пользователя")
    void successfulDeleteUser() {
        String response = step("Запрос удаления пользователя", () ->
                given(userSpec)
                        .when()
                        .delete(updateUser)
                        .then()
                        .spec(deleteUserResponseSpec)
                        .extract().asString());

        step("Проверка ответа", () -> {
            assertEquals("", response, "Ответ пустой");
        });
    }
}
