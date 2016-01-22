package fh.ooe.mc.mobilesportsapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import fh.ooe.mc.mobilesportsapp.NavigationDrawerFragment.NavigationDrawerCallbacks;
 
public class Welcome extends ActionBarActivity implements NavigationDrawerCallbacks {
 
	// Declare Variable
	private Button logout;
	private NavigationDrawerFragment mNavigationDrawerFragment;
	private CharSequence mTitle;
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
 
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);                      
		
		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()                        
				.findFragmentById(R.id.navigation_drawer);                                                        
		mTitle = getTitle();                                                                                      

		// mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		
		// Set up the drawer.                                                                                     
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
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
		Fragment mFragment = fragmentManager.findFragmentById(R.id.container);
		switch (position) {
		case 0:
			setTitle(getString(R.string.title_section1));
			if (mFragment instanceof PedoFragment) {
			    return;
			} else {
				fragmentManager.beginTransaction().replace(R.id.container, PedoFragment.newInstance()).commit();
			}
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
