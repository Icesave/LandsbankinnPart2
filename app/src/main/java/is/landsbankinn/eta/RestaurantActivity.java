package is.landsbankinn.eta;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import is.landsbankinn.eta.models.Restaurant;
import is.landsbankinn.eta.models.Review;
import is.landsbankinn.eta.utils.ReviewAdapter;
import is.landsbankinn.eta.utils.TagsAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantActivity extends BaseActivity {

    TextView mRestaurantName;
    RecyclerView mReviews;
    TextView mDescription;
    TextView mPrice;
    TextView mLocation;
    TextView mReviewHealine;
    Button mSubmitReview;
    Button mSendReview;
    RadioGroup mReviewGradeChoices;
    EditText mReviewText;
    Button mReviewCancel;
    List<Review> restaurantReviews;
    ReviewAdapter reviewAdapter;
    RecyclerView mTags;
    ImageView mLocationIcon;
    ImageView mPriceIcon;
    ImageView mReviewsIcon;
    ImageView mRestaurantIcon;
    long restaruantId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        restaruantId = getIntent().getLongExtra("restaurantId", 11);
        mDescription = findViewById(R.id.restaruant_description);
        mPrice = findViewById(R.id.restaruant_price);
        mLocation = findViewById(R.id.restaruant_location);
        mRestaurantName = findViewById(R.id.restaurant_name);
        mReviews = findViewById(R.id.restaurant_reviews);
        mReviewHealine = findViewById(R.id.restuarant_review_headline);
        mSubmitReview = findViewById(R.id.restaurant_insert_review_button);
        mTags = findViewById(R.id.restaurant_tags);
        mLocationIcon = findViewById(R.id.restaurant_location_icon);
        mPriceIcon = findViewById(R.id.restaurant_price_icon);
        mReviewsIcon = findViewById(R.id.restaurant_revies_icon);
        mRestaurantIcon = findViewById(R.id.restaurant_icon);

        mSubmitReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!preferenceHandler.isUserLoggedIn()) {
                    Toast toast = Toast.makeText(RestaurantActivity.this, "Notandi verður að vera skráður innn", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    // get prompts.xml view
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(RestaurantActivity.this);

                    LayoutInflater layoutInflater = LayoutInflater.from(RestaurantActivity.this);
                    View popupInputDialogView = layoutInflater.inflate(R.layout.restaurant_review_promt, null);
                    mSendReview = popupInputDialogView.findViewById(R.id.review_send_review);
                    mReviewGradeChoices = popupInputDialogView.findViewById(R.id.review_grade_choices);
                    mReviewText = popupInputDialogView.findViewById(R.id.review_text);
                    mReviewCancel = popupInputDialogView.findViewById(R.id.review_cancel);


                    alertDialogBuilder.setView(popupInputDialogView);
                    final AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                    mReviewCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.cancel();
                        }
                    });

                    mSendReview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int grade = 1;

                            switch (mReviewGradeChoices.getCheckedRadioButtonId()) {
                                case R.id.review_grade_2:
                                    grade = 2;
                                    break;
                                case R.id.review_grade_3:
                                    grade = 3;
                                    break;
                                case R.id.review_grade_4:
                                    grade = 4;
                                    break;
                                case R.id.review_grade_5:
                                    grade = 5;
                                    break;
                            }
                            String reviewText = mReviewText.getText().toString();
                            final Review review = new Review();
                            review.setRating(grade);
                            review.setText(reviewText);
                            review.setUsername(preferenceHandler.getUserName());

                            Call<Restaurant> reviewResult = requestHandler.submitReview(review, restaruantId);

                            reviewResult.enqueue(new Callback<Restaurant>() {
                                @Override
                                public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {
                                    if (response.code() == 200) {
                                        updateReviews(review);
                                        alertDialog.dismiss();
                                    } else {
                                        Toast.makeText(RestaurantActivity.this, R.string.review_post_error, Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Restaurant> call, Throwable t) {
                                    Toast.makeText(RestaurantActivity.this, R.string.review_post_error, Toast.LENGTH_SHORT).show();
                                }
                            });

                        }

                    });
                }

            }
        });

        final Call<Restaurant> restaurant = requestHandler.getRestaurant(restaruantId);
        restaurant.enqueue(new Callback<Restaurant>() {
            @Override
            public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {
                if (response.code() == 200) {
                    displayRestaurantInfo(response.body());
                }
            }

            @Override
            public void onFailure(Call<Restaurant> call, Throwable t) {
                System.out.println(t);
            }
        });
    }

    public static Intent getIntent(Context packageContext, long restaruantId) {
        Intent intent = new Intent(packageContext, RestaurantActivity.class);
        intent.putExtra("restaurantId", restaruantId);
        return intent;
    }

    private void displayRestaurantInfo(Restaurant restaurant){
        mRestaurantName.setText(restaurant.getName());
        mPrice.setText(restaurant.getPrice());
        mDescription.setText(restaurant.getDescription());
        mLocation.setText(restaurant.getLocation());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mReviews.setLayoutManager(layoutManager);
        restaurantReviews = restaurant.getReviewList();
        reviewAdapter = new ReviewAdapter(this, restaurantReviews);
        mReviews.setAdapter(reviewAdapter);
        mReviewHealine.setVisibility(View.VISIBLE);
        mSubmitReview.setVisibility(View.VISIBLE);
        mPriceIcon.setVisibility(View.VISIBLE);
        mReviewsIcon.setVisibility(View.VISIBLE);
        mLocationIcon.setVisibility(View.VISIBLE);
        mRestaurantIcon.setVisibility(View.VISIBLE);
        RecyclerView.LayoutManager tagManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mTags.setLayoutManager(new GridLayoutManager(this, 3));
        mTags.setAdapter(new TagsAdapter(this, restaurant.getGenres()));
    }

    private void updateReviews(Review newReview) {
        restaurantReviews.add(0, newReview);
        reviewAdapter.notifyDataSetChanged();
    }

}
