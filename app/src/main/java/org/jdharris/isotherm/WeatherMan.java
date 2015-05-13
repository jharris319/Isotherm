package org.jdharris.isotherm;


import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Locale;

public class WeatherMan {
	protected String wunderKey;
	protected Context cont;
	protected String state;
	protected String city;

	public WeatherMan(Context context, Location location) {
		wunderKey = context.getString(R.string.wunder_key);
		cont = context;
		Geocoder gcd = new Geocoder(context, Locale.getDefault());
		try {
			List<Address> addresses = gcd.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
			if (addresses.size() > 0)
				city = addresses.get(0).getLocality();
				state = addresses.get(0).getAdminArea();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public double getCurrentTemp() {
		String command = "http://api.wunderground.com/api/" + wunderKey + "/geolookup/conditions/q/"
				+ state + "/" + city + ".json";
		try {
			String result = runPost(command);
			try {
				JSONObject jObject = new JSONObject(result);
				JSONObject jObservation = jObject.getJSONObject("current_observation");
				String tempF = jObservation.getString("temp_f");
				return Double.valueOf(tempF);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 1;
	}

	private String runPost(String command) throws IOException {
		URL url = new URL(command);
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		try {
			InputStream in = new BufferedInputStream(urlConnection.getInputStream());
			return readStream(in);
		}
		finally {
			urlConnection.disconnect();
		}
	}

	private String readStream(InputStream is) throws IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader r = new BufferedReader(new InputStreamReader(is),1000);
		for (String line = r.readLine(); line != null; line =r.readLine()){
			sb.append(line);
		}
		is.close();
		return sb.toString();
	}
}
