package fh.ooe.mc.mobilesportsapp;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

public class PedoFragment extends Fragment {
	
	private static final String ARG_SECTION_NUMBER = "section_number";
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

	private TextView mTvCal;
	private TextView mTvDistance;
	private TextView mTvSpeed;
	private SensorManager mSensorManager;
	private ParseUser user;
	private ParseObject stepCount;
	private Welcome mActivity;

	
	public static PedoFragment newInstance() {
		PedoFragment fragment = new PedoFragment();

		return fragment;
	}

	public PedoFragment() {
		// TODO Auto-generated constructor stub
	}

	private void enableAccelerometerListening() {
		mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
		mSensorManager.registerListener(mSensorEventListener,
				mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	 @Override
	 public void onAttach(Activity activity) {
	        super.onAttach(activity);
	        mActivity = (Welcome) activity;
	    }
	 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container, false);

		user = ParseUser.getCurrentUser();
		getStepCount();
		mTvNumSteps = (TextView) rootView.findViewById(R.id.tv_numsteps);
		mProgressBarNumSteps = (ProgressBar) rootView.findViewById(R.id.progressBar);
		mProgressBarCalories = (ProgressBar) rootView.findViewById(R.id.progressBar_calories);
		mProgressBarKm = (ProgressBar) rootView.findViewById(R.id.progressBar_km);
		mProgressBarSpeed = (ProgressBar) rootView.findViewById(R.id.progressBar_kmh);
		// TODO get todays steps from server
		mProgressBarNumSteps.setProgress(0);

		mTvCal = (TextView) rootView.findViewById(R.id.tv_calories);
		mTvDistance = (TextView) rootView.findViewById(R.id.tv_km);
		mTvSpeed = (TextView) rootView.findViewById(R.id.tv_kmh);

		mTreshold = 10;
		mCurrentY = 0;
		mPreviousY = 0;
		mAcceleration = 0.0f;
		enableAccelerometerListening();

		mTvNumSteps.setText(Integer.toString(mNumSteps));
				
		return rootView;
	}
	
	
	@Override
	public void onResume() {
		super.onResume();
		user = ParseUser.getCurrentUser();
		getStepCount();
	}

	@Override
	public void onPause() {
		super.onPause();
		user = ParseUser.getCurrentUser();
		getStepCount();
		stepCount.put("numberOfSteps", mNumSteps);
		stepCount.saveInBackground();
	}
	
	private void getStepCount() {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("stepCount")
				.whereEqualTo("user", user);
				query.getFirstInBackground(new GetCallback<ParseObject>() {
				public void done(ParseObject object, ParseException e) {
				    if (object == null) {
				      Log.i("QueryStatus", "Couldn't retrieve the object.");
				    } else {
				      Log.i("QueryStatus", "Retrieved the object.");
				      stepCount = object;
				      afterRetrieving();
				    }
				  }
				});
	}
	
	private void afterRetrieving() {
		if(stepCount != null) {
			mNumSteps = stepCount.getInt("numberOfSteps");	
		} else {
			mNumSteps = 0;
		}
		mTvNumSteps.setText(Integer.toString(mNumSteps));
		float percent = (float) ((float) mNumSteps / (float) STEPS_TO_REACH) * 100;
		mProgressBarNumSteps.setProgress((int) percent);
		mProgressBarNumSteps.refreshDrawableState();
	}

	private int updateSmallCircles() {
			double height =  user.getDouble("height");
			double weight =  user.getDouble("weight");
			
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

			height = height / 100;
			Log.i("hey", String.valueOf(height) + " w:" + String.valueOf(weight));
			double bmi = weight / (height * height);
			mTvSpeed.setText(String.valueOf(formatter.format(bmi)));
			if (bmi < 18.5) {
				mProgressBarSpeed.setBackgroundColor(Color.RED);
			} else if (bmi < 24.9) {
				mProgressBarSpeed.setBackgroundColor(Color.GREEN);
			} else {
				mProgressBarSpeed.setBackgroundColor(Color.RED);
			
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
				mActivity.runOnUiThread(new Runnable() {

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