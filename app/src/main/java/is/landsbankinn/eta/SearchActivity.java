package is.landsbankinn.eta;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import java.util.Map;
import java.util.HashMap;

public class SearchActivity extends BaseActivity {

    private String[] TAGS = new String[] {"Test1", "Test2", "Test3", "Test4", "Test5"};

    private Map<String, Boolean> tags = new HashMap<>();
    private LinearLayout tagLayout1;
    private LinearLayout tagLayout2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        tagLayout1 = findViewById(R.id.tagCol1);
        tagLayout1.removeAllViews();

        tagLayout2 = findViewById(R.id.tagCol2);
        tagLayout2.removeAllViews();

        initTags();
    }

    private void initTags() {
        for( int i = 0; i < TAGS.length; i++ ) {
            String tag = TAGS[i];


            CheckBox cb = new CheckBox(this);
            cb.setText( tag );
            cb.setChecked( false );

            tags.put( tag, false );

            if( i%2 == 0 ) {
                tagLayout1.addView(cb);
            }
            else {
                tagLayout2.addView(cb);
            }


        }
    }
}
