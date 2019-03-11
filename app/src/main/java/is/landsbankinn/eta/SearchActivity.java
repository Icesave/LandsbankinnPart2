package is.landsbankinn.eta;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import is.landsbankinn.eta.models.Restaurant;
import is.landsbankinn.eta.models.SearchParam;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends BaseActivity {

    private final String[] TAGS = new String[] {
        "Indverskur",
        "Tælenskur",
        "Pizza",
        "Skyndibiti",
        "Vegan",
        "Ítalskur",
        "Hollt",
        "Samlokur"
    };

    private final Map<String, Boolean> tags = new HashMap<>();
    private LinearLayout tagLayout1;
    private LinearLayout tagLayout2;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        tagLayout1 = findViewById(R.id.tagCol1);
        tagLayout1.removeAllViews();

        tagLayout2 = findViewById(R.id.tagCol2);
        tagLayout2.removeAllViews();

        submit = findViewById(R.id.submitButton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRestaurants();
            }
        });


        initTags();
    }

    private void initTags() {
        for( int i = 0; i < TAGS.length; i++ ) {
            final String tag = TAGS[i];


            CheckBox cb = new CheckBox(this);
            cb.setText( tag );
            cb.setChecked( false );
            tags.put(tag, false);
            cb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tags.put(tag, !tags.get(tag));;
                }
            });

            tags.put( tag, false );

            if( i%2 == 0 ) {
                tagLayout1.addView(cb);
            }
            else {
                tagLayout2.addView(cb);
            }


        }
    }

    private void getRestaurants() {
        SearchParam searchParam = new SearchParam();
        EditText name = (EditText) findViewById(R.id.name);
        searchParam.setName(name.getText().toString());


        // Setja price
        RadioGroup rgc = findViewById(R.id.radioGroupCost);
        RadioButton price = findViewById(rgc.getCheckedRadioButtonId());
        searchParam.setPrice( price.getText().toString() );

        // Setja tags
        List<String> chosenTags = new ArrayList<>();
        for( int i = 0; i < TAGS.length; i++ ) {
            if( tags.get(TAGS[i]) ) {
                chosenTags.add(TAGS[i]);
            }
        }
        searchParam.setTags( chosenTags );


/*
        Restaurant restaurant = new Restaurant();

        EditText name = (EditText) findViewById(R.id.name);
        restaurant.setName(name.getText().toString());

        restaurant.setLocation("ee");
        restaurant.setDescription("ee");
        List<String> list = null;
        restaurant.setGenres(list);
        restaurant.setPrice("Ódýrt");


        /*
        // Setja name
        String name = findViewById(R.id.name).toString();
        params.setName(name);

        // Setja price
        RadioGroup rgc = findViewById(R.id.radioGroupCost);
        RadioButton price = findViewById(rgc.getCheckedRadioButtonId());
        params.setPrice( price.getText().toString() );

        // Setja tags
        ArrayList<String> chosenTags = new ArrayList<>();
        for( int i = 0; i < TAGS.length; i++ ) {
            if( tags.get(TAGS[i]) ) {
                chosenTags.add( TAGS[i] );
            }
        }
        params.setTags( chosenTags );
        */
        Call<List<Restaurant>> search;
        if( searchParam.getName().equals("") ) {
            search = requestHandler.searchForRestaurant( searchParam );
        }
        else {
            search = requestHandler.searchForRestaurantName( searchParam );
        }
        search.enqueue(new Callback<List<Restaurant>>() {
            @Override
            public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
                if (response.isSuccessful()) {
                    if( response.body().size() == 0) {
                        Toast.makeText(SearchActivity.this, "Enginn veitingastaður fannst", Toast.LENGTH_LONG).show();
                    }
                    else {
                        List<Restaurant> results = response.body();
                        showRestaurants( results );
                    }
                }
                else {
                    try {
                        Toast.makeText(SearchActivity.this, "Error code 400: "+response.errorBody().string(), Toast.LENGTH_LONG).show();
                    }
                    catch (Exception e) {
                        Toast.makeText(SearchActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Restaurant>> call, Throwable t) {
                Toast toastFail = Toast.makeText(SearchActivity.this, "Óskilgreind villa", Toast.LENGTH_LONG);
                toastFail.show();
                call.cancel();
            }
        });

    }

    private void showRestaurants(List<Restaurant> restaurants) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(SearchActivity.this);
        builderSingle.setTitle("Veldu veitingastað:");


        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                SearchActivity.this,
                android.R.layout.select_dialog_item
        );

        final Map<Integer, Long> ids = new HashMap<>();

        for( int i = 0; i < restaurants.size(); i++ ) {
            String name = restaurants.get(i).getName();
            Long id = restaurants.get(i).getId();
            ids.put( i, id );
            arrayAdapter.add( name );
        }

        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Long id = ids.get(which);
                Intent i = new Intent(SearchActivity.this, RestaurantActivity.class);
                i.putExtra("restaurantId", id);
                startActivity(i);
            }
        });
        builderSingle.show();
    }
}
