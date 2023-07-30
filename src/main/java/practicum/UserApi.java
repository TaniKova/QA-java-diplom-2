package practicum;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import practicum.entity.User;

import static io.restassured.RestAssured.given;
import static practicum.MainData.getSpecification;

public class UserApi {
    private final String userBaseUrl = "/api/auth";
    private final String userRegistrationUrl = userBaseUrl+"/register";
    private final String userLoginUrl = userBaseUrl + "/login";
    private final String userUrl = userBaseUrl + "/user";


    @Step("Create user")
    public ValidatableResponse create(User user) {
        return given()
                .log().all()
                .spec(getSpecification())
                .body(user)
                .when()
                .post(userRegistrationUrl)
                .then()
                .log().all();
    }


    @Step("Login user")
    public ValidatableResponse login(User user) {
        return given()
                .log().all()
                .spec(getSpecification())
                .body(user.getLogin())
                .when()
                .post(userLoginUrl)
                .then()
                .log().all();
    }

    @Step("Delete user")
    public ValidatableResponse delete(String accessToken) {
        return given()
                .log().all()
                .spec(getSpecification())
                .header("Authorization", accessToken)
                .when()
                .delete(userUrl)
                .then()
                .log().all();
    }

    @Step("Get user info")
    public ValidatableResponse getData(String accessToken) {
        return given()
                .log().all()
                .spec(getSpecification())
                .header("Authorization", accessToken)
                .when()
                .get(userUrl)
                .then()
                .log().all();
    }

    @Step("Update user info")
    public ValidatableResponse edit(String accessToken, User user) {
        return given()
                .log().all()
                .spec(getSpecification())
                .header("Authorization", accessToken)
                .body(user)
                .when()
                .patch(userUrl)
                .then()
                .log().all();
    }


}
