package org.example.login;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.example.user.User;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalToIgnoringCase;

public class LoginAssertions {

    @Step("Проверка успешной авторизации")
    public void loginSuccessfully(ValidatableResponse response, User user) {
        response.assertThat()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("user.email", equalToIgnoringCase(user.getEmail()))
                .body("user.name", equalTo(user.getName()))
                .body("accessToken", notNullValue())
                .body("refreshToken", notNullValue());
    }

    @Step("Проверка неуспешной авторизации")
    public void errorLogin(ValidatableResponse response) {
        response.assertThat()
                .statusCode(401)
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));

    }

    @Step("Проверка успешного выхода из системы")
    public void logoutSuccessfully(ValidatableResponse response) {
        response.assertThat()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("message", equalTo("Successful logout"));
    }
}