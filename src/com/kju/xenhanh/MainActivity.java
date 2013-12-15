package com.kju.xenhanh;

import com.google.android.gcm.GCMRegistrar;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.kju.xenhanh.CommonUtilities.SENDER_ID;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.util.Log;

public class MainActivity extends Activity {

	static final LatLng Center = new LatLng(21.021087, 105.829625);
	private GoogleMap googleMap;
	public String TAG = "Check";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		try {
			if (googleMap == null) {
				googleMap = ((MapFragment) getFragmentManager()
						.findFragmentById(R.id.map)).getMap();

			}
			googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(
					Center, 14);
			googleMap.animateCamera(yourLocation);
			googleMap.setMyLocationEnabled(true);
			// Marker TP = googleMap.addMarker(new MarkerOptions().
			//		 position(Center).title("Current"));

		} catch (Exception e) {
			e.printStackTrace();
		}
		checkNotNull(SENDER_ID, "SENDER_ID");

		GCMRegistrar.checkDevice(this);
		GCMRegistrar.checkManifest(this);

		 String regId = GCMRegistrar.getRegistrationId(this);

		if (regId.equals("")) {
			GCMRegistrar.register(this, SENDER_ID);
			regId = GCMRegistrar.getRegistrationId(this);
		} else {
			Log.d(TAG, "Already registered: "+ regId);
		}

	}

	private void checkNotNull(Object reference, String name) {
		if (reference == null) {
			throw new NullPointerException(getString(R.string.error_config,
					name));
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		GCMRegistrar.unregister(this);
	}
	
}
