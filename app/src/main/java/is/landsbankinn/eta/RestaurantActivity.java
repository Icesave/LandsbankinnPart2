package is.landsbankinn.eta;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import is.landsbankinn.eta.models.Restaurant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantActivity extends BaseActivity {

    TextView mRestaurantName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        mRestaurantName = findViewById(R.id.restaurant_name);
        long id = getIntent().getLongExtra("restaurantId", 0);

        Call<Restaurant> restaurant = requestHandler.getRestaurant(id);
        restaurant.enqueue(new Callback<Restaurant>() {
            @Override
            public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {
                mRestaurantName.setText(response.body().getName());
            }

            @Override
            public void onFailure(Call<Restaurant> call, Throwable t) {
                mRestaurantName.setText(getResources().getString(R.string.get_restaurant_error));
            }
        });
    }

    public static Intent getIntent(Context packageContext, long restaruantId) {
        Intent intent = new Intent(packageContext, RestaurantActivity.class);
        intent.putExtra("restaurantId", restaruantId);
        return intent;
    }
}
