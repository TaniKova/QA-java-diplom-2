package practicum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import practicum.entity.User;

public class UserProfileTest {

    private User user;
    private String accessToken;
    UserApi userApi = new UserApi();

    @Before
    public void setUser() {
        user = User.generateRandomUser();
    }

    //с авторизацией,
    @Test
    @DisplayName("Change all fields in user profile with authorization")
    public void updateUserWithAuthTest() {
        userApi.create(user);
        ValidatableResponse response = userApi.login(user);
        accessToken = response.extract().path("accessToken").toString();

        User newUser  = User.generateRandomUser();

        userApi.edit(accessToken, newUser);
        ValidatableResponse updatedResponse = userApi.getData(accessToken);

        updatedResponse.body("user.name", Matchers.equalTo(newUser.getName()));
        updatedResponse.body("user.email", Matchers.equalTo(newUser.getEmail().toLowerCase()));
    }

    //без авторизации,
    @Test
    @DisplayName("Change all fields in user profile without authorization")
    public void updateUserWithoutAuthTest() {
        userApi.create(user);
        User newUser = User.generateRandomUser();
        user.setName(newUser.getName());
        user.setEmail(newUser.getEmail());
        ValidatableResponse response = userApi.edit("", user);
        response.statusCode(401).and().assertThat().body("message", Matchers.equalTo("You should be authorised"));
    }

    @After
    public void cleanUp() {
        if (accessToken != null) {
            userApi.delete(accessToken);
            accessToken = null;
        }
    }
}
