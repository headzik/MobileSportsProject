package fh.ooe.mc.mobilesportsapp;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import fh.ooe.mc.mobilesportsapp.NavigationDrawerFragment.NavigationDrawerCallbacks;

public class MainActivity extends ActionBarActivity implements NavigationDrawerCallbacks {

	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;
	private SensorManager mSensorManager;

	private float mAcceleration;
	private float mCurrentY;
	private float mPreviousY;
	private int mNumSteps;
	private int mTreshold;
	private TextView mTvNumSteps;
	private ProgressBar mProgressBarNumSteps;
	private ProgressBar mProgressBarCalories;
	private ProgressBar mProgressBarKm;
	private ProgressBar mProgressBarSpeed;
	private final int STEPS_TO_REACH = 700;
	private EditText mEtHeight;
	private EditText mEtWeight;
	private TextView mTvCal;
	private TextView mTvDistance;
	private TextView mTvSpeed;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		// mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
		mTvNumSteps = (TextView) findViewById(R.id.tv_numsteps);
		mProgressBarNumSteps = (ProgressBar) findViewById(R.id.progressBar);
		mProgressBarCalories = (ProgressBar) findViewById(R.id.progressBar_calories);
		mProgressBarKm = (ProgressBar) findViewById(R.id.progressBar_km);
		mProgressBarSpeed = (ProgressBar) findViewById(R.id.progressBar_kmh);
		// TODO get todays steps from server
		mTvNumSteps.setText("0");
		mProgressBarNumSteps.setProgress(0);
		mEtHeight = (EditText) findViewById(R.id.et_height);
		mEtWeight = (EditText) findViewById(R.id.et_weight);
		mTvCal = (TextView) findViewById(R.id.tv_calories);
		mTvDistance = (TextView) findViewById(R.id.tv_km);
		mTvSpeed = (TextView) findViewById(R.id.tv_kmh);

		mTreshold = 5;
		mCurrentY = 0;
		mPreviousY = 0;
		mNumSteps = 0;
		mAcceleration = 0.0f;
		enableAccelerometerListening();

	}

	private void enableAccelerometerListening() {
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mSensorManager.registerListener(mSensorEventListener,
				mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

	}

	protected void onResume() {
		super.onResume();
		enableAccelerometerListening();
	}

	protected void onPause() {
		super.onPause();
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
				.commit();
	}

	public void onSectionAttached(int number) {
		switch (number) {
		case 1:
			mTitle = getString(R.string.title_section1);
			break;
		case 2:
			mTitle = getString(R.string.title_section2);
			break;
		case 3:
			mTitle = getString(R.string.title_section3);
			break;
		}
	}

	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// if (!mNavigationDrawerFragment.isDrawerOpen()) {
		// Only show items in the action bar relevant to this screen
		// if the drawer is not showing. Otherwise, let the drawer
		// decide what to show in the action bar.
		// getMenuInflater().inflate(R.menu.main, menu);
		// restoreActionBar();
		// return true;
		// }
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */

	public static class PlaceholderFragment extends Fragment {

		private static final String ARG_SECTION_NUMBER = "section_number";

		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container, false);
			return rootView;
		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
		}

	}

	private int updateSmallCircles() {
		if (mEtHeight.getText() != null && mEtWeight.getText() != null){
		double height = Integer.valueOf(mEtHeight.getText().toString());
		double weight = Integer.valueOf(mEtWeight.getText().toString());

		final double walkingFactor = 0.57;
		double CaloriesBurnedPerMile;
		double strip;
		double stepCountMile; // step/mile
		double conversationFactor;
		double CaloriesBurned;
		NumberFormat formatter = new DecimalFormat("#0.00");
		double distance;
		
		CaloriesBurnedPerMile = walkingFactor * (weight * 2.2);
		strip = height * 0.415;
		stepCountMile = 160934.4 / strip;
		conversationFactor = CaloriesBurnedPerMile / stepCountMile;
		
		CaloriesBurned = mNumSteps * conversationFactor;
		distance = (mNumSteps * strip) / 100000;
		
		mTvCal.setText(String.valueOf(formatter.format(CaloriesBurned)));
		mTvDistance.setText(String.valueOf(formatter.format(distance)));
		
		height = height/100;
		Log.i("hey", String.valueOf(height) + " w:" + String.valueOf(weight));
		double bmi = weight / (height*height);
		mTvSpeed.setText(String.valueOf(formatter.format(bmi)));
		if (bmi < 18.5){
					mProgressBarSpeed.setBackgroundColor(Color.RED);

		} else if (bmi < 24.9){
			mProgressBarSpeed.setBackgroundColor(Color.GREEN);
		}else{
			mProgressBarSpeed.setBackgroundColor(Color.RED);
		}
		}
		return 0;
	}

	private SensorEventListener mSensorEventListener = new SensorEventListener() {

		@Override
		public void onSensorChanged(SensorEvent event) {
			float x = event.values[0];
			float y = event.values[1];
			float z = event.values[2];
			mCurrentY = y;
			if (Math.abs(mCurrentY - mPreviousY) > mTreshold) {
				mNumSteps++;
				mTvNumSteps.setText(String.valueOf(mNumSteps));
				if (mNumSteps > 80)
				updateSmallCircles();
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						float percent = (float) ((float) mNumSteps / (float) STEPS_TO_REACH) * 100;
						mProgressBarNumSteps.setProgress((int) percent);
						mProgressBarNumSteps.refreshDrawableState();
						
					}
				});
			}
			mPreviousY = y;
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// TODO Auto-generated method stub

		}
	};

}
