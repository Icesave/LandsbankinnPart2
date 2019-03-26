package is.landsbankinn.eta;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import is.landsbankinn.eta.models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Sér um að nýskrá notanda
 */
public class RegisterActivity extends BaseActivity {

    RadioGroup mTypeButtons;
    EditText mUserName;
    EditText mPassword;
    EditText mEmail;
    Button mRegisterButton;
    TextView mRegisterInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mTypeButtons = findViewById(R.id.register_type);
        mUserName = findViewById(R.id.register_username);
        mPassword = findViewById(R.id.register_password);
        mRegisterButton = findViewById(R.id.register_register_button);
        mEmail = findViewById(R.id.register_email);
        mRegisterInfo = findViewById(R.id.register_login_information);

        // Virkni fyrir takka sem að nýskráir notanda
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPassword.setBackgroundResource(R.color.GreyBG);
                mUserName.setBackgroundResource(R.color.GreyBG);
                mEmail.setBackgroundResource(R.color.GreyBG);
                mTypeButtons.setBackgroundResource(R.color.GreyBG);

                // Farið yfir öll input, ef að eitthvað vantar þá er svæði litað rautt
                boolean allInputsOk = true;
                String username = mUserName.getText().toString();
                String pass = mPassword.getText().toString();
                String email = mEmail.getText().toString();
                int selectedRadioId = mTypeButtons.getCheckedRadioButtonId();

                if (username.isEmpty()) {
                    allInputsOk = false;
                    mUserName.setBackgroundResource(R.color.input_needed);
                }

                else if (pass.isEmpty()) {
                    allInputsOk = false;
                    mPassword.setBackgroundResource(R.color.input_needed);
                }

                else if (pass.length() < 4) {
                    allInputsOk = false;
                    mPassword.setBackgroundResource(R.color.input_needed);
                    Toast.makeText(RegisterActivity.this, R.string.authentication_password_to_short, Toast.LENGTH_SHORT).show();
                }

                else if (email.isEmpty()) {
                    allInputsOk = false;
                    mEmail.setBackgroundResource(R.color.input_needed);
                    Toast.makeText(RegisterActivity.this, R.string.restaurant_email_input_error, Toast.LENGTH_SHORT).show();
                }

                else if (!email.contains("@")) {
                    mEmail.setBackgroundResource(R.color.input_needed);
                    Toast.makeText(RegisterActivity.this, R.string.restaurant_email_input_error, Toast.LENGTH_SHORT).show();
                }

                else if (selectedRadioId == -1) {
                    allInputsOk = false;
                    mTypeButtons.setBackgroundResource(R.color.input_needed);
                }

                // Ef öll input eru í lagi
                if (allInputsOk) {

                    String managerType = getString(R.string.user_type_manager);
                    if (mTypeButtons.getCheckedRadioButtonId() == R.id.register_type_non_manager) {
                        managerType = getString(R.string.user_type_casual);
                    }

                    User user = new User();
                    user.setUsername(username);
                    user.setPassword(pass);
                    user.setEmail(email);
                    user.setType(managerType);

                    // Nýskráning send á bakenda
                    Call<User> registerResponse = requestHandler.insertUser(user);

                    registerResponse.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            // Ef gekk að nýskrá eru allar upplýsingar um notanda vistaðar í RequestHandler
                            if (response.code() == 200) {
                                User newUser = response.body();
                                mRegisterInfo.setText(R.string.register_info_success);
                                mRegisterInfo.setVisibility(View.VISIBLE);
                                preferenceHandler.setUserType(newUser.getType());
                                preferenceHandler.setUserPassword(newUser.getPassword());
                                preferenceHandler.setUserName(newUser.getUsername());
                                preferenceHandler.setUserEmail(newUser.getEmail());
                                preferenceHandler.userHasLoggedIn();
                                setResult(2);
                                finish();

                                // Villuskilaboð birt ef að eitthvað fór úrskeiðis
                            } else {
                                mRegisterInfo.setText(R.string.register_info_error);
                                mRegisterInfo.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            mRegisterInfo.setText(R.string.register_info_error);
                        }
                    });
                }
            }
        });
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, RegisterActivity.class);
    }
}
