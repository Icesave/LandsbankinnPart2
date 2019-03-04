package is.landsbankinn.eta;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import is.landsbankinn.eta.models.Restaurant;



public class InsertActivity extends BaseActivity  {

    Button mSubmit;
    CheckBox CheckBox7;
    CheckBox CheckBox8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);


        mSubmit = findViewById(R.id.submitButton);
        //when User taps Submit Button
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editTextName = findViewById(R.id.editTextName);
                String Name = editTextName.getText().toString();

                EditText editTextAddress = findViewById(R.id.editTextAddress);
                String Address = editTextAddress.getText().toString();

                CheckBox7 = findViewById(R.id.checkBox7);
                CheckBox8 = findViewById(R.id.checkBox8);
                String cb = "";

                if(CheckBox7.isChecked() == true) {
                    cb = cb + "" + CheckBox7.getText();
                }
                if(CheckBox8.isChecked() == true){
                    cb = cb + "" + CheckBox8.getText();
                }

                Toast toast = Toast.makeText(InsertActivity.this,Name+Address+cb, Toast.LENGTH_LONG);
                toast.show();




            }
        });







    }
}

