package is.landsbankinn.eta;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import is.landsbankinn.eta.models.Restaurant;
import is.landsbankinn.eta.models.InsertInformation;
import is.landsbankinn.eta.models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InsertActivity extends BaseActivity  {

    Button mSubmit;
    CheckBox mCheckBox1, mCheckBox2, mCheckBox3, mCheckBox4, mCheckBox5, mCheckBox6, mCheckBox7, mCheckBox8;
    RadioGroup rg;
    RadioButton selectedPrice;
    EditText mRestName;
    EditText mRestAddress;
    EditText mAboutRest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        mSubmit = findViewById(R.id.submitButton);
        mCheckBox1 = findViewById(R.id.checkBox1);
        mCheckBox2 = findViewById(R.id.checkBox2);
        mCheckBox3 = findViewById(R.id.checkBox3);
        mCheckBox4 = findViewById(R.id.checkBox4);
        mCheckBox5 = findViewById(R.id.checkBox5);
        mCheckBox6 = findViewById(R.id.checkBox6);
        mCheckBox7 = findViewById(R.id.checkBox7);
        mCheckBox8 = findViewById(R.id.checkBox8);
        mRestName = findViewById(R.id.editTextName);
        mRestAddress = findViewById(R.id.editTextAddress);
        mAboutRest = findViewById(R.id.editTextAbout);
        rg = findViewById(R.id.radioGroup);


        //when User taps Submit Button
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = mRestName.getText().toString();
                String address = mRestAddress.getText().toString();
                String about = mAboutRest.getText().toString();
                String price;
                List<String> genres = new ArrayList<>();

                // Collect genres to list
                if(mCheckBox1.isChecked() == true) {
                    genres.add(mCheckBox1.getText().toString());
                }
                if(mCheckBox2.isChecked() == true) {
                    genres.add(mCheckBox2.getText().toString());
                }
                if(mCheckBox3.isChecked() == true) {
                    genres.add(mCheckBox3.getText().toString());
                }
                if(mCheckBox4.isChecked() == true) {
                    genres.add(mCheckBox4.getText().toString());
                }
                if(mCheckBox5.isChecked() == true) {
                    genres.add(mCheckBox5.getText().toString());
                }
                if(mCheckBox6.isChecked() == true) {
                    genres.add(mCheckBox6.getText().toString());
                }
                if(mCheckBox7.isChecked() == true) {
                    genres.add(mCheckBox7.getText().toString());
                }
                if(mCheckBox8.isChecked() == true) {
                    genres.add(mCheckBox8.getText().toString());
                }

                // Get the price
                int selectedRadioButtonID = rg.getCheckedRadioButtonId();
                selectedPrice = findViewById(selectedRadioButtonID);
                price = selectedPrice.getText().toString();

                // Test Toast
                Toast toast = Toast.makeText(InsertActivity.this, name + "" + price + genres, Toast.LENGTH_LONG);
                toast.show();


                Restaurant restaurant = new Restaurant();
                restaurant.setName(name);
                restaurant.setLocation(address);
                restaurant.setDescription(about);
                restaurant.setGenres(genres);
                restaurant.setPrice(price);

                User user = new User();


                InsertInformation information = new InsertInformation();
                information.setRestaurant(restaurant);
                information.setUser(user);

                Call<Restaurant> restaurantCall = requestHandler.insertRestaurant(information);
                restaurantCall.enqueue(new Callback<Restaurant>() {
                    @Override
                    public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {
                        if (response.code() == 200) {
                            Toast toastSuccess = Toast.makeText(InsertActivity.this, "Veitingastað hefur verið bætt við", Toast.LENGTH_LONG);
                            toastSuccess.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Restaurant> call, Throwable t) {
                        Toast toastFail = Toast.makeText(InsertActivity.this, "Eitthvað fór úrskeiðis", Toast.LENGTH_LONG);
                        toastFail.show();
                    }
                });

                // Check if User is logged in
                if (preferenceHandler.isUserLoggedIn() == false) {
                    Toast toastLogIn = Toast.makeText(InsertActivity.this, "Vinsamlegast skráðu þig inn", Toast.LENGTH_LONG);
                    toastLogIn.show();
                }

            }
        });

    }
}

