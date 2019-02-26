package is.landsbankinn.eta.utils;

import java.util.List;

import is.landsbankinn.eta.models.Restaurant;
import is.landsbankinn.eta.models.Review;
import is.landsbankinn.eta.models.SearchParam;
import is.landsbankinn.eta.models.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RequestHandler {
    String BASE_URL = "https://eta-bakendi.herokuapp.com/";
    @Headers({"Accept: application/json"})

    @GET("restaurant/{id}")
    Call<Restaurant>getRestaurant(@Path("id") long id);

    @POST("insert")
    Call<Restaurant> insertRestaurant(@Body Restaurant restaurant);

    @POST("restaurant/{id}")
    Call<Restaurant> submitReview(@Body Review Review);

    @POST("login")
    Call<User> getUser(@Body User user);

    @POST("signup")
    Call<User> insertUser(@Body User user);

    @POST("search")
    Call<List<Restaurant>> searchForRestaurant(@Body SearchParam searchParam);

}
