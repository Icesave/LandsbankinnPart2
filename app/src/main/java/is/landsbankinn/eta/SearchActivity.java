package is.landsbankinn.eta;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import java.util.Map;
import java.util.HashMap;

public class SearchActivity extends BaseActivity {

    private String[] TAGS = new String[] {"Test1", "Test2", "Test3", "Test4", "Elvar"};

    private Map<String, Boolean> tags = new HashMap<>();
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
                showResults();
            }
        });


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

    private void showResults() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(SearchActivity.this);
        builderSingle.setTitle("Select One Name:-");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(SearchActivity.this, android.R.layout.select_dialog_item);
        arrayAdapter.add("Hardik");
        arrayAdapter.add("Archit");
        arrayAdapter.add("Jignesh");
        arrayAdapter.add("Umang");
        arrayAdapter.add("Gatti");

        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                AlertDialog.Builder builderInner = new AlertDialog.Builder(SearchActivity.this);
                builderInner.setMessage(strName);
                builderInner.setTitle("Your Selected Item is");
                builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int which) {
                        dialog.dismiss();
                    }
                });
                builderInner.show();
            }
        });
        builderSingle.show();
    }
}
