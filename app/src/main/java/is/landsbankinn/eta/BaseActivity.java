package is.landsbankinn.eta;

import android.app.DownloadManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import is.landsbankinn.eta.utils.RequestHandler;
import is.landsbankinn.eta.utils.Restaurant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class BaseActivity extends AppCompatActivity {

    public RequestHandler requestHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RequestHandler.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        requestHandler = retrofit.create(RequestHandler.class);

    }
}
