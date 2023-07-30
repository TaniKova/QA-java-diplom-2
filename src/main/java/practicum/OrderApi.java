package practicum;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import practicum.entity.Order;

import static io.restassured.RestAssured.given;
import static practicum.MainData.getSpecification;

public class OrderApi {
    private final String orderBaseUrl = "/api";
    private final String ordersUrl = orderBaseUrl + "/orders";
    private final String orderIngredientsUrl = orderBaseUrl + "/ingredients";

    @Step("Create order with auth")
    public ValidatableResponse createWithAuth(Order order, String accessToken) {
        return given()
                .log().all()
                .spec(getSpecification())
                .header("Authorization", accessToken)
                .body(order)
                .when()
                .post(ordersUrl)
                .then()
                .log().all();
    }

    @Step("Create order without auth")
    public ValidatableResponse createWithoutAuth(Order order) {
        return given()
                .log().all()
                .spec(getSpecification())
                .body(order)
                .when()
                .post(ordersUrl)
                .then()
                .log().all();
    }


    @Step("Get ingredients info")
    public ValidatableResponse getIngredientsInfo() {
        return given()
                .log().all()
                .spec(getSpecification())
                .when()
                .get(orderIngredientsUrl)
                .then()
                .log().all();
    }

    @Step("Get user orders with auth")
    public ValidatableResponse getUsersOrdersWithAuth(String accessToken) {
        return given()
                .log().all()
                .spec(getSpecification())
                .header("Authorization", accessToken)
                .when()
                .get(ordersUrl)
                .then()
                .log().all();
    }

    @Step("Get user orders without auth")
    public ValidatableResponse getUsersOrdersWithoutAuth() {
        return given()
                .log().all()
                .spec(getSpecification())
                .when()
                .get(ordersUrl)
                .then()
                .log().all();
    }

}
