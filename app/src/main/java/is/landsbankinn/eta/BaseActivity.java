package is.landsbankinn.eta;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import is.landsbankinn.eta.utils.PreferenceHandler;
import is.landsbankinn.eta.utils.RequestHandler;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class BaseActivity extends AppCompatActivity {

    public RequestHandler requestHandler;
    public PreferenceHandler preferenceHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RequestHandler.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        requestHandler = retrofit.create(RequestHandler.class);
        preferenceHandler = new PreferenceHandler(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.nav_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.one:
                // do something
                return true;

            case R.id.two:
                //do something
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
