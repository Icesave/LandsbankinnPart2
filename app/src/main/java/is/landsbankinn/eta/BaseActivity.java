package is.landsbankinn.eta;

import android.content.Intent;
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
        selectMenu( menu );
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        selectMenu( menu );
        return true;
    }

    private void selectMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        menu.clear();
        inflater.inflate(R.menu.nav_menu, menu);

        if( preferenceHandler.isUserLoggedIn() ) {
            menu.removeItem(R.id.login);
            menu.removeItem(R.id.register);
        }
        else {
            menu.removeItem(R.id.logout);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.login:
                startActivity(new Intent( this, AuthenticationActivity.class ));
                return true;
            case R.id.register:
                startActivity(new Intent( this, RegisterActivity.class ));
                return true;
            case R.id.logout:
                preferenceHandler.userHasLoggedOut();
                this.invalidateOptionsMenu();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
