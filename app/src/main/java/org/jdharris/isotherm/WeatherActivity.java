package org.jdharris.isotherm;

import android.app.Fragment;
import android.location.Location;
import android.location.LocationManager;
import android.os.StrictMode;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class WeatherActivity extends ActionBarActivity {
	protected TextView tvTempF;
	protected TextView tvConditions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO: Here's a Band-Aid, get rid of it..
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_weather);

		// Locate UI Elements
		tvTempF = (TextView)findViewById(R.id.tvTempF);
		tvConditions = (TextView)findViewById(R.id.tvConditions);

		updateUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_weather, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



	public void updateUI() {
		// WeatherMan Testing
		LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
		Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		WeatherMan wunder = new WeatherMan(this, location);
		tvTempF.setText(wunder.getValue("temp_f"));
		tvConditions.setText(wunder.getValue("weather"));
//		Toast.makeText(this, String.valueOf(tempF), Toast.LENGTH_SHORT).show();
	}
}
