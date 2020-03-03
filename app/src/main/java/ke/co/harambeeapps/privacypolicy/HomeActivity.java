package ke.co.harambeeapps.privacypolicy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {
    public static final int POLICYCREATE = 1;

    private static final String DATA_RECEIVED = "data_received";
    private static final String INFORMATION = "information";

    private FloatingActionButton fab;
    private TextView information;
    private boolean dataReceived = false;

    private AppBarConfiguration mAppBarConfiguration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
         fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivityForResult(intent, POLICYCREATE);
            }
        });
        information = findViewById(R.id.information);
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_app, R.id.nav_website, R.id.nav_history,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        final NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                boolean handled = NavigationUI.onNavDestinationSelected(item, navController);

                if (!handled) {
                    switch (item.getItemId()) {
                        case R.id.nav_app: {
                            Toast.makeText(HomeActivity.this, "gbfg", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        case R.id.nav_website:{
                            break;
                        }


                        case R.id.nav_slideshow: {
                            break;

                    }
                }

                drawer.closeDrawer(GravityCompat.START);
                return handled;
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
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
