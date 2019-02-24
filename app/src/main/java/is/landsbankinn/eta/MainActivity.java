package is.landsbankinn.eta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends BaseActivity {

    Button mTestButton;
    EditText mRestaurantId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // A test that will be removed
        mTestButton = findViewById(R.id.test_button);
        mRestaurantId = findViewById(R.id.restaurant_id_input);
        mTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = RestaurantActivity.getIntent(MainActivity.this, Long.parseLong(mRestaurantId.getText().toString()));
                startActivity(intent);
            }
        });
    }
}
