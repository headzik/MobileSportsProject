package fh.ooe.mc.mobilesportsapp;

import com.parse.ParseUser;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class SettingsFragment extends Fragment{
	private EditText etHeight;
	private EditText etWeight;
	private EditText etAge;
	private EditText etAvgStepLength;
	private RadioButton rbMale;
	private RadioButton rbFemale;
	private Button bSubmit;
	private Button bLogout;	
	private Button bExit;	
	
	//private SharedPreferences prefs;
	//public static String PREF_ID = "myPrefs";
	
	private ParseUser user;

	public static SettingsFragment newInstance() {
		SettingsFragment fragment = new SettingsFragment();
		return fragment;
	}

	public SettingsFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
		
		user = ParseUser.getCurrentUser();
		
		etHeight = (EditText) rootView.findViewById(R.id.et_height);
		etWeight = (EditText) rootView.findViewById(R.id.et_weight);
		etAge = (EditText) rootView.findViewById(R.id.et_age);
		etAvgStepLength = (EditText) rootView.findViewById(R.id.et_steplength);
		rbMale = (RadioButton) rootView.findViewById(R.id.radioButtonMale);
		rbFemale = (RadioButton) rootView.findViewById(R.id.radioButtonFemale);
		bSubmit = (Button) rootView.findViewById(R.id.submit);
		bLogout = (Button) rootView.findViewById(R.id.logout);
		bExit = (Button) rootView.findViewById(R.id.exit);
		//prefs = this.getActivity().getSharedPreferences(PREF_ID, Context.MODE_PRIVATE);
		
		etHeight.setText(Integer.toString(user.getInt("height")));
		etWeight.setText(Integer.toString(user.getInt("weight")));
		etAge.setText(Integer.toString(user.getInt("age")));
		etAvgStepLength.setText(Integer.toString(user.getInt("stepLength")));
		String gender = user.getString("gender");
		if(gender.equals("M")) {
			rbMale.setChecked(true);
			rbFemale.setChecked(false);
		} else if(gender.equals("F")){
			rbFemale.setChecked(true);
			rbMale.setChecked(false);
		}
		
		rbMale.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked){
					//prefs.edit().putBoolean("isMale", true).commit();
					rbFemale.setChecked(false);
				}else{
					//prefs.edit().putBoolean("isMale", false).commit();
				}
			}
		});
		
		rbFemale.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked){
					//prefs.edit().putBoolean("isFemale", true).commit();
					rbMale.setChecked(false);
				}else{
					//prefs.edit().putBoolean("isFemale", false).commit();
				}
			}
		});
		
		bSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				user.put("height", Integer.valueOf(etHeight.getText().toString()));
				user.put("weight", Integer.valueOf(etWeight.getText().toString()));
				user.put("age", Integer.valueOf(etAge.getText().toString()));
				user.put("stepLength", Integer.valueOf(etAvgStepLength.getText().toString()));
				if(rbFemale.isChecked()){
					user.put("gender", "F");
				} else if (rbMale.isChecked()){
					user.put("gender", "M");			
				}
				user.saveInBackground();
				Toast.makeText(
						getContext(),
						"Submited changes.",
						Toast.LENGTH_SHORT).show();
			}
		});
		
		bLogout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ParseUser.logOut();
				Toast.makeText(
						getContext(),
						"Succesfully logged out.",
						Toast.LENGTH_SHORT).show();
				getActivity().finish();
			}
		});
		
		bExit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(
						getContext(),
						"See you next time!",
						Toast.LENGTH_SHORT).show();
				getActivity().finish();
			}
		});
		return rootView;
	}
	

}

