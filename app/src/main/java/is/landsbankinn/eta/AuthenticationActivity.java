package is.landsbankinn.eta;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import is.landsbankinn.eta.models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthenticationActivity extends BaseActivity {

    Button mLoginButton;
    EditText mUsername;
    EditText mPassword;
    TextView mInformation;
    Button mRegister;
    private final int REGISTER_REQUEST_CODE = 1;
    private final int REQUEST_RESULT_SUCCESS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        mLoginButton = findViewById(R.id.authentication_login_button);
        mUsername = findViewById(R.id.login_username);
        mPassword = findViewById(R.id.login_password);
        mInformation = findViewById(R.id.authentication_login_information);
        mRegister = findViewById(R.id.authentication_register_button);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = mUsername.getText().toString();
                String password = mPassword.getText().toString();
                mUsername.setBackgroundResource(R.color.white);

                boolean allInputsOk  = true;
                if (username.isEmpty()) {
                    allInputsOk = false;
                    mUsername.setBackgroundResource(R.color.input_needed);
                }

                if (password.isEmpty()) {
                    allInputsOk = false;
                    mPassword.setBackgroundResource(R.color.input_needed);
                }

                if (allInputsOk) {
                    User user = new User();
                    user.setPassword(password);
                    user.setUsername(username);

                    Call<User> userRequest = requestHandler.getUser(user);


                    userRequest.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            mInformation.setVisibility(View.VISIBLE);
                            if (response.code() == 200) {
                                mInformation.setText(getResources().getString(R.string.authentication_login_success));
                                User user = response.body();
                                preferenceHandler.setUserEmail(user.getEmail());
                                preferenceHandler.setUserName(user.getUsername());
                                preferenceHandler.setUserPassword(user.getPassword());
                                preferenceHandler.setUserType(user.getType());
                                preferenceHandler.userHasLoggedIn();

                                finish();
                            } else {
                                mInformation.setText(getResources().getString(R.string.authentication_login_error));
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            mInformation.setVisibility(View.VISIBLE);
                            mInformation.setText(getResources().getString(R.string.authentication_login_error));
                            Log.d("Authentication", "onFailure: " + t);
                        }
                    });
                }
            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(RegisterActivity.getIntent(AuthenticationActivity.this), REGISTER_REQUEST_CODE);
/*
                startActivity(RegisterActivity.getIntent(AuthenticationActivity.this));
*/

            }
        });
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, AuthenticationActivity.class);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        if (requestCode == REGISTER_REQUEST_CODE && resultCode == REQUEST_RESULT_SUCCESS) {
            finish();
        }
    }

}
