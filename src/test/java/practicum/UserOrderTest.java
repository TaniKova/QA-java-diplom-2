package practicum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Test;
import practicum.entity.Order;
import practicum.entity.User;

public class UserOrderTest {

    private final OrderApi orderApi = new OrderApi();
    private final UserApi userApi = new UserApi();
    private User user;
    String accessToken;

    //неавторизованный пользователь,
    @Test
    @DisplayName("Get orders without authorization")
    public void getOrdersWithoutAuthTest() {
        ValidatableResponse response = orderApi.getUsersOrdersWithoutAuth();
        response.statusCode(401).and().assertThat().body("message", Matchers.equalTo("You should be authorised"));
    }

    //авторизованный пользователь.
    @Test
    @DisplayName("Get orders with authorization")
    public void getOrdersWithAuthTest() {
        user = User.generateRandomUser();
        ValidatableResponse response = userApi.create(user);
        accessToken = response.extract().path("accessToken").toString();
        orderApi.createWithAuth(Order.getWithDefaultIngredients(),accessToken);
        ValidatableResponse response2 = orderApi.getUsersOrdersWithAuth(accessToken);
        response2.statusCode(200).and().assertThat().body("success", Matchers.is(true));
    }

    @After
    public void cleanUp() {
        if (accessToken != null) {
            userApi.delete(accessToken);
            accessToken = null;
        }
    }
}
