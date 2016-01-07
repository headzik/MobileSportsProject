package fh.ooe.mc.mobilesportsapp;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseUser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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
		
		Parse.initialize(this, "lflIbMjfPWl23Hdqj6Z7EMjuqBix8YreTIwNkJLh", "cTLgLxd1ybMeH5roNY6a83xDyUHEaVzyp9KMwNC0");
		 
        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
 
        // If you would like all objects to be private by default, remove this
        // line.
        defaultACL.setPublicReadAccess(true);
 
        ParseACL.setDefaultACL(defaultACL, true);
        
        if (ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser())) {
			// If user is anonymous, send the user to LoginSignupActivity.class
			Intent intent = new Intent(MainActivity.this,
					LoginSignupActivity.class);
			startActivity(intent);
		} else {
			// If current user is NOT anonymous user
			// Get current user data from Parse.com
			ParseUser currentUser = ParseUser.getCurrentUser();
			if (currentUser == null) {
				// Send user to LoginSignupActivity.class
				Intent intent = new Intent(MainActivity.this,
						LoginSignupActivity.class);
				startActivity(intent);
				finish();
			}
		}
		
	}


	protected void onResume() {
		super.onResume();
	}

	protected void onPause() {
		super.onPause();
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		switch (position) {
		case 0:
			setTitle(getString(R.string.title_section1));
			fragmentManager.beginTransaction().replace(R.id.container, PedoFragment.newInstance())
					.commit();
			break;
		case 1:
			setTitle(getString(R.string.title_section2));
			fragmentManager.beginTransaction().replace(R.id.container, StatisticsFragment.newInstance()).commit();
			break;
		case 2:
			setTitle(getString(R.string.title_section3));
			fragmentManager.beginTransaction().replace(R.id.container, HeartRateFragment.newInstance()).commit();
			break;
		case 3:
			setTitle(getString(R.string.title_section4));
			fragmentManager.beginTransaction().replace(R.id.container, SettingsFragment.newInstance()).commit();
			break;
		}

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
		case 4:
			mTitle = getString(R.string.title_section4);
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

}