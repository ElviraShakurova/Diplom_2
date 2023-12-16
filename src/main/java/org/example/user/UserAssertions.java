package org.example.user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalToIgnoringCase;

public class UserAssertions {

    @Step("Проверка успешного создания пользователя")
    public void createdSuccessfully(ValidatableResponse response, User user) {
        response.assertThat()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("user.email", equalToIgnoringCase(user.getEmail()))
                .body("user.name", equalTo(user.getName()))
                .body("accessToken", notNullValue())
                .body("refreshToken", notNullValue());
    }

    @Step("Проверка создания уже зарегистрированного пользователя")
    public void alreadyExists(ValidatableResponse response, String email) {
        response.assertThat()
                .statusCode(403)
                .body("success", equalTo(false))
                .body("message", equalTo("User already exists"));

    }

    @Step("Проверка неуспешного создания пользователя, без заполнения одного из обязательных полей")
    public void createdWithError(ValidatableResponse response) {
        response.assertThat()
                .statusCode(403)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Step("Проверка успешного обновления данных о пользователе")
    public void updateUser(ValidatableResponse response) {
        response.assertThat()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Step("Проверка неуспешного обновления данных о пользователе, без авторизации")
    public void updateUserError(ValidatableResponse response) {
        response.assertThat()
                .statusCode(401)
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }

    @Step("Проверка успешного удаления пользователя")
    public void deleteSuccessfully(ValidatableResponse response) {
        response.assertThat()
                .statusCode(202)
                .body("success", equalTo(true))
                .body("message", equalTo("User successfully removed"));
    }
}