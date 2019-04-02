package is.landsbankinn.eta;


import android.content.Intent;
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


        // Þegar notandi ýtir á Submit hnapp
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = mRestName.getText().toString();
                String address = mRestAddress.getText().toString();
                String about = mAboutRest.getText().toString();
                String price;
                List<String> genres = new ArrayList<>();

                // Athuga hvort fyllt hafi verið í nauðsynlega reiti
                if (name.isEmpty()) {
                    Toast toastNoName = Toast.makeText(InsertActivity.this, "Nafn vantar", Toast.LENGTH_LONG);
                    toastNoName.show();
                    return;
                }
                if (address.isEmpty()) {
                    Toast toastNoAddress = Toast.makeText(InsertActivity.this, "Heimilisfang vantar", Toast.LENGTH_LONG);
                    toastNoAddress.show();
                    return;
                }


                // Safna saman tegundum sem notandi velur
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

                // Verðbil sem valið er
                int selectedRadioButtonID = rg.getCheckedRadioButtonId();
                selectedPrice = findViewById(selectedRadioButtonID);
                price = selectedPrice.getText().toString();

                // Búa til Restaurant
                Restaurant restaurant = new Restaurant();
                restaurant.setName(name.trim());
                restaurant.setLocation(address);
                restaurant.setDescription(about);
                restaurant.setGenres(genres);
                restaurant.setPrice(price);

                // Sækja notanda
                User user = new User();
                user.setUsername( preferenceHandler.getUserName() );
                user.setPassword( preferenceHandler.getUserPassword() );
                user.setType( preferenceHandler.getUserType() );

                InsertInformation information = new InsertInformation();
                information.setRestaurant(restaurant);
                information.setUser(user);

                // Veitingastað bætt við
                Call<Restaurant> restaurantCall = requestHandler.insertRestaurant(information);
                restaurantCall.enqueue(new Callback<Restaurant>() {
                    @Override
                    public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {
                        Toast toasty = Toast.makeText(InsertActivity.this, Integer.toString(response.code()), Toast.LENGTH_LONG);
                        toasty.show();
                        if (response.code() == 200) {
                            Toast toastSuccess = Toast.makeText(InsertActivity.this, "Veitingastað hefur verið bætt við", Toast.LENGTH_LONG);
                            toastSuccess.show();
                            startActivity(RestaurantActivity.getIntent( InsertActivity.this, response.body().getId() ));
                            finish();
                        }
                        else {
                            Toast toastFail = Toast.makeText(InsertActivity.this, "Eitthvað fór úrskeiðis", Toast.LENGTH_LONG);
                            toastFail.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Restaurant> call, Throwable t) {
                        Toast toastFail = Toast.makeText(InsertActivity.this, "Eitthvað fór úrskeiðis", Toast.LENGTH_LONG);
                        toastFail.show();
                    }
                });
                     // Athuga hvort notandi sé manager
                        String userType = preferenceHandler.getUserType();
                        if (!getString(R.string.user_type_manager).equals(userType)) {
                           Toast toastNotMananger = Toast.makeText(InsertActivity.this, "Þú ert ekki eigandi", Toast.LENGTH_LONG);
                           toastNotMananger.show();
                        }
            }
        });

    }
}

