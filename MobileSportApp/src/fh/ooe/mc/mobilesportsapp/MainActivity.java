package fh.ooe.mc.mobilesportsapp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity implements SensorEventListener {
	// implements NavigationDrawerFragment.NavigationDrawerCallbacks {

	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	// private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;
	private SensorManager mSensorManager;
	private Sensor mSensor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

		/*
		 * mNavigationDrawerFragment = (NavigationDrawerFragment)
		 * getSupportFragmentManager()
		 * .findFragmentById(R.id.navigation_drawer); mTitle = getTitle();
		 * 
		 * // Set up the drawer.
		 * mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
		 * (DrawerLayout) findViewById(R.id.drawer_layout));
		 */

	}

	protected void onResume() {
		super.onResume();
		mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
	}

	protected void onPause() {
		super.onPause();
		mSensorManager.unregisterListener(this);
	}

	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER)
			return;
		TextView tvx = (TextView) findViewById(R.id.tvx);
		TextView tvy = (TextView) findViewById(R.id.tvy);
		TextView tvz = (TextView) findViewById(R.id.tvz);
		
		tvx.setText("X: " + String.valueOf(event.values[0]));
		tvy.setText("Y: " + String.valueOf(event.values[1]));
		tvz.setText("Z: " + String.valueOf(event.values[2]));
	}

	/*
	 * @Override public void onNavigationDrawerItemSelected(int position) { //
	 * update the main content by replacing fragments FragmentManager
	 * fragmentManager = getSupportFragmentManager();
	 * fragmentManager.beginTransaction().replace(R.id.container,
	 * PlaceholderFragment.newInstance(position + 1)).commit(); }
	 * 
	 * public void onSectionAttached(int number) { switch (number) { case 1:
	 * mTitle = getString(R.string.title_section1); break; case 2: mTitle =
	 * getString(R.string.title_section2); break; case 3: mTitle =
	 * getString(R.string.title_section3); break; } }
	 */

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

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	/*
	 * public static class PlaceholderFragment extends Fragment {
	 * 
	 * private static final String ARG_SECTION_NUMBER = "section_number";
	 * 
	 * public static PlaceholderFragment newInstance(int sectionNumber) {
	 * PlaceholderFragment fragment = new PlaceholderFragment(); Bundle args =
	 * new Bundle(); args.putInt(ARG_SECTION_NUMBER, sectionNumber);
	 * fragment.setArguments(args); return fragment; }
	 * 
	 * public PlaceholderFragment() { }
	 * 
	 * @Override public View onCreateView(LayoutInflater inflater, ViewGroup
	 * container, Bundle savedInstanceState) { View rootView =
	 * inflater.inflate(R.layout.fragment_main, container, false); return
	 * rootView; }
	 * 
	 * @Override public void onAttach(Activity activity) {
	 * super.onAttach(activity); ((MainActivity) activity).onSectionAttached(
	 * getArguments().getInt(ARG_SECTION_NUMBER)); }
	 * 
	 * }
	 */

}
