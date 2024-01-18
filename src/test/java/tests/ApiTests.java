package tests;

import models.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static data.ApiEndpoints.REGISTER;
import static data.ApiEndpoints.USERS;
import static data.TestData.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.*;
import static specs.UserActionsSpec.*;

@DisplayName("API тесты reqres.in")
public class ApiTests extends TestBase {

    @DisplayName("Проверка создания пользователя")
    @Test
    void createUserTest() {
        CreateAndUpdateUserRequest userData = new CreateAndUpdateUserRequest();
        userData.setName(NAME);
        userData.setJob(JOB_TITLE);

        CreateAndUpdateUserResponse response = step("Отправляем запрос на создание пользователя", () ->
            given(requestPostPutSpec)
                    .body(userData)
                    .when()
                    .post(USERS)
                    .then()
                    .spec(response201)
                    .extract().as(CreateAndUpdateUserResponse.class));

        step("Проверяем, что пользователь создан с правильными параметрами", () -> {
            assertThat(response.getName()).isEqualTo(NAME);
            assertThat(response.getJob()).isEqualTo(JOB_TITLE);
            assertThat(response.getId()).isNotNull();
            assertThat(response.getCreatedAt()).isNotNull();
        });
    }

    @DisplayName("Проверка обновления пользователя")
    @Test
    void updateUserTest() {
        CreateAndUpdateUserRequest userData = new CreateAndUpdateUserRequest();
        userData.setName(NAME);
        userData.setJob(NEW_JOB_TITLE);

        CreateAndUpdateUserResponse response = step("Отправляем запрос на обнолвение пользователя", () ->
                given(requestPostPutSpec)
                        .body(userData)
                        .when()
                        .post(USERS)
                        .then()
                        .spec(response201)
                        .extract().as(CreateAndUpdateUserResponse.class));

        step("Проверяем, что пользователь обновлен с правильными параметрами", () -> {
            assertThat(response.getName()).isEqualTo(NAME);
            assertThat(response.getJob()).isEqualTo(NEW_JOB_TITLE);
            assertThat(response.getId()).isNotNull();
            assertThat(response.getCreatedAt()).isNotNull();
        });
    }

    @DisplayName("Проверка успешной регистрации пользователя")
    @Test
    void successfulRegisterUserTest() {
        RegisterUserRequest userData = new RegisterUserRequest();
        userData.setEmail(EMAIL);
        userData.setPassword(PASSWORD);

        RegisterUserResponse response = step("Отправляем запрос на регистрацию " +
                "пользователя со всеми обязательными параметрами", () ->
                given(requestPostPutSpec)
                        .body(userData)
                        .when()
                        .post(REGISTER)
                        .then()
                        .spec(response200)
                        .extract().as(RegisterUserResponse.class));

        step("Проверяем, что пользователь зарегистрирован и получен токен", () -> {
            assertThat(response.getId()).isEqualTo(ID);
            assertThat(response.getToken()).isEqualTo(TOKEN);
        });
    }

    @DisplayName("Проверка неуспешной регистрации пользователя")
    @Test
    void unsuccessfulRegisterUserTest() {
        RegisterUserRequest userData = new RegisterUserRequest();
        userData.setEmail(EMAIL);

        RegisterUserResponse response = step("Отправляем запрос на регистрацию пользователя " +
                "без одного обязательного параметра", () ->
                given(requestPostPutSpec)
                        .body(userData)
                        .when()
                        .post(REGISTER)
                        .then()
                        .spec(response400)
                        .extract().as(RegisterUserResponse.class));

        step("Проверяем, что вернулась ожидаемая ошибка", () -> {
            assertThat(response.getError()).isEqualTo(ERROR_MESSAGE);
        });
    }

    @DisplayName("Проверка получения данных единичного пользователя")
    @Test
    void getSingleUserTest() {

        SingleUserResponse response = step("Отправляем запрос на получение данных о пользователе", () ->
                given(requestGetDeleteSpec)
                        .get(USERS + "/2")
                        .then()
                        .spec(response200)
                        .extract().as(SingleUserResponse.class));

        step("Проверяем, что значения параметров в ответе совпадают с ожидаемыми", () -> {
            assertThat(response.getData().getId()).isEqualTo(SINGLE_USER_ID);
            assertThat(response.getData().getEmail()).isEqualTo(SINGLE_USER_EMAIL);
            assertThat(response.getData().getFirstName()).isEqualTo(FIRST_NAME);
            assertThat(response.getData().getLastName()).isEqualTo(LAST_NAME);
            assertThat(response.getData().getAvatar()).isEqualTo(AVATAR);
            assertThat(response.getSupport().getUrl()).isEqualTo(URL);
            assertThat(response.getSupport().getText()).isEqualTo(TEXT);
        });
    }

    @DisplayName("Проверка удаления пользователя")
    @Test
    void deleteUserTest() {

        step("Отправляем запрос на удаление пользователя и проверяем, что в ответе " +
                "вернулся ожидаемый status code", () -> {
            given(requestGetDeleteSpec)
                    .delete(USERS + "/2")
                    .then()
                    .spec(response204);
        });
    }
}
