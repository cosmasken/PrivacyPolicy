package ke.co.harambeeapps.privacypolicy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by 2ndgengod on 2/28/2020.
 */
public class StartActivity extends AppCompatActivity {

    public static final int POLICYCREATE = 1;

    private static final String DATA_RECEIVED = "data_received";
    private static final String INFORMATION = "information";

    private FloatingActionButton fab;
    private TextView information;
    private boolean dataReceived = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivityForResult(intent, POLICYCREATE);
            }
        });

        information = findViewById(R.id.information);
    }

    @Override
    public void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        dataReceived = savedInstanceState.getBoolean(DATA_RECEIVED, false);
        if(dataReceived) {
            information.setText(savedInstanceState.getString(INFORMATION));
          //  disclaimer.setText(savedInstanceState.getString(DISCLAIMER));
          //  disclaimer.setVisibility(View.VISIBLE);
        } else {
            information.setText(R.string.main_activity_explanation);
           // disclaimer.setVisibility(View.GONE);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putBoolean(DATA_RECEIVED, dataReceived);
        if (dataReceived) {
            savedInstanceState.putString(INFORMATION, information.getText().toString());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK
                && requestCode == POLICYCREATE
                && data != null
                && data.hasExtra(MainActivity.STATE_NEW_POLICY_CREATED)) {

            dataReceived = true;

            String name = data.getExtras().getString(MainActivity.STATE_NAME);
            String appname = data.getExtras().getString(MainActivity.STATE_APP_NAME);
            String license = data.getExtras().getString(MainActivity.STATE_LICENSE);


            String alertInformationText = getResources().getString(R.string.main_activity_alarm_added_info, name, appname,license);
            information.setText(alertInformationText);

            Snackbar.make(fab, getString(R.string.new_alarm_added), Snackbar.LENGTH_LONG).show();
        } else {
            dataReceived = false;

            information.setText(R.string.main_activity_explanation);
        }
    }
}