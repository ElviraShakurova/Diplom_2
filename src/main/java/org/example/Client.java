package org.example;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class Client {
    public static final String BASE_PATH = "api/auth";
    public static final String BASE_URI = "https://stellarburgers.nomoreparties.site/";

    public static RequestSpecification spec() {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .basePath(BASE_PATH)
                ;
    }

    public static RequestSpecification specOrders() {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                ;
    }
}