package is.landsbankinn.eta;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends BaseActivity {

    Button mSearchButton;
    Button mInsertButton;
    Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // A test that will be removed
        mSearchButton = findViewById(R.id.search_button);
        mInsertButton = findViewById(R.id.insert_button);
        mLoginButton = findViewById(R.id.login_button);
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(MainActivity.this, "SEARCH", Toast.LENGTH_LONG);
                toast.show();

                // TODO start search Activity
                //Intent intent = SearchActivity.getIntent(MainActivity.this,8);
                //startActivity(intent);
            }
        });

        mInsertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(MainActivity.this, "INSERT", Toast.LENGTH_LONG);
                toast.show();

                // TODO start insert Activity
                //Intent intent = InsertActivity.getIntent(MainActivity.this,8);
                //startActivity(intent);

                // Check if User is logged in
                if (preferenceHandler.isUserLoggedIn() == false) {
                    Toast toastLogIn = Toast.makeText(MainActivity.this, "Vinsamlegast skráðu þig inn", Toast.LENGTH_LONG);
                    toastLogIn.show();
                }
            }
        });
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (preferenceHandler.isUserLoggedIn()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage(R.string.already_logged_in_message)
                            .setPositiveButton(R.string.already_logged_in_yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            })
                            .setNegativeButton(R.string.already_logged_in_no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    startAuthentication();
                                }
                            });
                    builder.create().show();
                } else {
                    startAuthentication();
                }
            }
        });
    }

    private void startAuthentication() {
        Intent intent = AuthenticationActivity.getIntent(MainActivity.this);
        startActivity(intent);
    }
}




