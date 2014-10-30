package com.elena.latencymeter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class MainActivity extends Activity {

	public static int screenWidth;
	public static int screenHeight;
	public static boolean clockWise;
	public static final String TAG = "LatencyMeter";
	public String appVersion;
	public static int speedValue;
	public final String MYPREFS = "my shared prefs";

	SeekBar speedBar;
	AnimationView myView;
	CheckBox mCheckBox;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		PreferenceManager.setDefaultValues(this, R.xml.pref_general, false);

		try {
			appVersion = this.getPackageManager().getPackageInfo(
					this.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			Log.d(TAG, "App version not found " + e.getMessage());
		}

		setContentView(R.layout.activity_main);
		// Log.d(TAG, "here");

		mCheckBox = (CheckBox) findViewById(R.id.checkBox);
		if (mCheckBox.isChecked()) {
			clockWise = false;
		} else {
			clockWise = true;
		}

		speedBar = (SeekBar) findViewById(R.id.speedBar);
		myView = (AnimationView) findViewById(R.id.animView);
		float defaultSpeed = (float) (speedBar.getProgress())
				/ (float) (speedBar.getMax());
		myView.setBallSpeed(defaultSpeed);

		speedBar.setOnSeekBarChangeListener(speedBarOnSeekBarChangeListener);

	}

	@Override
	public void onResume() {
		super.onResume();
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		screenWidth = displaymetrics.widthPixels;
		screenHeight = displaymetrics.heightPixels;
		// Log.d(TAG, "screen size: " + screenWidth + ", " + screenHeight);
		/*
		 * SharedPreferences userPref = PreferenceManager
		 * .getDefaultSharedPreferences(this);
		 * 
		 * speedValue = Integer.parseInt(userPref.getString("speed", "3"));
		 * Log.d(TAG, "speed on main " + speedValue);
		 * com.elena.latencymeter.AnimationView animationView =
		 * (com.elena.latencymeter.AnimationView) findViewById(R.id.animView);
		 * animationView.invalidate(); Toast.makeText(getBaseContext(),
		 * "Measuring speed, please wait...", Toast.LENGTH_LONG).show(); //
		 * animationView.draw(new Canvas());
		 */
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// if (item.getItemId() == R.id.action_settings) {
		// Intent intent = new Intent(this, SetPreferences.class);
		// startActivity(intent);
		// return true;
		// }
		if (item.getItemId() == R.id.about) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);

			builder.setMessage("\u00a9 2014 Elena Last").setTitle(
					"Latency Meter v." + appVersion);

			builder.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});

			AlertDialog dialog = builder.create();
			dialog.show();
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}

	}

	OnSeekBarChangeListener speedBarOnSeekBarChangeListener = new OnSeekBarChangeListener() {

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			float defaultSpeed = (float) (speedBar.getProgress())
					/ (float) (speedBar.getMax());
			myView.setBallSpeed(defaultSpeed);
			AnimationView.count = 0;
			AnimationView.distance = 0;
			myView.invalidate();
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
		}

	};

	public void boxClicked(View view) {
		if (mCheckBox.isChecked()) {
			clockWise = false;
		} else {
			clockWise = true;
		}
		Log.d(TAG, "direction clockWise: " + clockWise);
		AnimationView.count = 0;
		AnimationView.distance = 0;
		myView.invalidate();
	}

}
