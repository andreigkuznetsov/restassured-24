package tests;

import data.TestData;
import models.*;
import org.junit.jupiter.api.Test;

import static data.ApiEndpoints.REGISTER;
import static data.ApiEndpoints.USERS;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import static specs.userActionsSpec.*;

public class ApiTests extends TestBase {

    @Test
    void createUserTest() {
        CreateAndUpdateUserRequest userData = new CreateAndUpdateUserRequest();
        userData.setName(TestData.NAME);
        userData.setJob(TestData.JOB_TITLE);

        CreateAndUpdateUserResponse response = step("Make request", () ->
            given(requestPostPutSpec)
                    .body(userData)
                    .when()
                    .post(USERS)
                    .then()
                    .spec(response201)
                    .extract().as(CreateAndUpdateUserResponse.class));

        step("Check response", () -> {
            assertEquals(TestData.NAME, response.getName());
            assertEquals(TestData.JOB_TITLE, response.getJob());
            assertNotNull(response.getId());
            assertNotNull(response.getCreatedAt());
        });
    }

    @Test
    void updateUserTest() {
        CreateAndUpdateUserRequest userData = new CreateAndUpdateUserRequest();
        userData.setName(TestData.NAME);
        userData.setJob(TestData.NEW_JOB_TITLE);

        CreateAndUpdateUserResponse response = step("Make request", () ->
                given(requestPostPutSpec)
                        .body(userData)
                        .when()
                        .post(USERS)
                        .then()
                        .spec(response201)
                        .extract().as(CreateAndUpdateUserResponse.class));

        step("Check response", () -> {
            assertEquals(TestData.NAME, response.getName());
            assertEquals(TestData.NEW_JOB_TITLE, response.getJob());
            assertNotNull(response.getId());
            assertNotNull(response.getCreatedAt());
        });
    }

    @Test
    void successfulRegisterUserTest() {
        RegisterUserRequest userData = new RegisterUserRequest();
        userData.setEmail(TestData.EMAIL);
        userData.setPassword(TestData.PASSWORD);

        RegisterUserResponse response = step("Make request", () ->
                given(requestPostPutSpec)
                        .body(userData)
                        .when()
                        .post(REGISTER)
                        .then()
                        .spec(response200)
                        .extract().as(RegisterUserResponse.class));

        step("Check response", () -> {
            assertEquals(TestData.ID, response.getId());
            assertEquals(TestData.TOKEN, response.getToken());
        });
    }

    @Test
    void unsuccessfulRegisterUserTest() {
        RegisterUserRequest userData = new RegisterUserRequest();
        userData.setEmail(TestData.EMAIL);

        RegisterUserResponse response = step("Make request", () ->
                given(requestPostPutSpec)
                        .body(userData)
                        .when()
                        .post(REGISTER)
                        .then()
                        .spec(response400)
                        .extract().as(RegisterUserResponse.class));

        step("Check response", () -> {
            assertEquals(TestData.ERROR_MESSAGE, response.getError());
        });
    }

    @Test
    void getSingleUserTest() {

        SingleUserResponse response = step("Make request", () ->
                given(requestGetDeleteSpec)
                        .get(USERS + "/2")
                        .then()
                        .spec(response200)
                        .extract().as(SingleUserResponse.class));

        step("Check response", () -> {
            assertEquals(TestData.SINGLE_USER_ID, response.getData().getId());
            assertEquals(TestData.SINGLE_USER_EMAIL, response.getData().getEmail());
            assertEquals(TestData.FIRST_NAME, response.getData().getFirstName());
            assertEquals(TestData.LAST_NAME, response.getData().getLastName());
            assertEquals(TestData.AVATAR, response.getData().getAvatar());
            assertEquals(TestData.URL, response.getSupport().getUrl());
            assertEquals(TestData.TEXT, response.getSupport().getText());
        });
    }

    @Test
    void deleteUserTest() {

        given(requestGetDeleteSpec)
                .delete(USERS + "/2")
                .then()
                .spec(response204);
    }

}
