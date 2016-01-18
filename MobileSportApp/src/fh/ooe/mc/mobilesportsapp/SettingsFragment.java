package fh.ooe.mc.mobilesportsapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RadioButton;

public class SettingsFragment extends Fragment{
	private EditText etHeight;
	private EditText etWeight;
	private EditText etAge;
	private EditText etAvgStepLength;
	private RadioButton rbMale;
	private RadioButton rbFemale;
	private SharedPreferences prefs;
	public static String PREF_ID = "myPrefs";

	public static SettingsFragment newInstance() {
		SettingsFragment fragment = new SettingsFragment();
		return fragment;
	}

	public SettingsFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
		
		etHeight = (EditText) rootView.findViewById(R.id.et_height);
		etWeight = (EditText) rootView.findViewById(R.id.et_weight);
		etAge = (EditText) rootView.findViewById(R.id.et_age);
		etAvgStepLength = (EditText) rootView.findViewById(R.id.et_steplength);
		rbMale = (RadioButton) rootView.findViewById(R.id.radioButtonMale);
		rbFemale = (RadioButton) rootView.findViewById(R.id.radioButtoFemale);
		prefs = this.getActivity().getSharedPreferences(PREF_ID, Context.MODE_PRIVATE);
		
		etHeight.addTextChangedListener(new TextWatcher() {			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {	
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			@Override
			public void afterTextChanged(Editable s) {
				prefs.edit().putInt("height", Integer.valueOf(s.toString())).commit();
			}
		});
		
		etWeight.addTextChangedListener(new TextWatcher() {			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {	
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			@Override
			public void afterTextChanged(Editable s) {
				prefs.edit().putInt("weight", Integer.valueOf(s.toString())).commit();
			}
		});
		
		etAge.addTextChangedListener(new TextWatcher() {			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {	
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			@Override
			public void afterTextChanged(Editable s) {
				prefs.edit().putInt("age", Integer.valueOf(s.toString())).commit();
			}
		});
		
		etAvgStepLength.addTextChangedListener(new TextWatcher() {			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {	
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			@Override
			public void afterTextChanged(Editable s) {
				prefs.edit().putInt("steplength", Integer.valueOf(s.toString())).commit();
			}
		});
		
		rbMale.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked){
					prefs.edit().putBoolean("isMale", true).commit();
				}else{
					prefs.edit().putBoolean("isMale", false).commit();
				}
			}
		});
		
		rbFemale.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked){
					prefs.edit().putBoolean("isFemale", true).commit();
				}else{
					prefs.edit().putBoolean("isFemale", false).commit();
				}
			}
		});
		return rootView;
	}
	
	
}

