package fh.ooe.mc.mobilesportsapp;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SignUpActivity extends Activity {
	
	private String usernametxt;
	private String passwordtxt;
	private String emailtxt;
	private String gendertxt;
	private double stepLengthnum;
	private double heightnum;
	private double weightnum;
	private double agenum;
	
	private EditText etPassword;
	private EditText etUsername;
	private EditText etEmail;
	private EditText etWeight;
	private EditText etHeight;
	private EditText etAge;
	private EditText etStepLength;
	private RadioGroup rgGender;
	private Button bSignup;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		
		etUsername = (EditText) findViewById(R.id.username);
		etPassword = (EditText) findViewById(R.id.password);
		etEmail = (EditText) findViewById(R.id.email);
		etHeight = (EditText) findViewById(R.id.height);
		etWeight = (EditText) findViewById(R.id.weight);
		etAge = (EditText) findViewById(R.id.age);
		rgGender = (RadioGroup) findViewById(R.id.gender);
		etStepLength = (EditText) findViewById(R.id.step_length);
		bSignup = (Button) findViewById(R.id.signup);
		
		rgGender.clearCheck();
		
		rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if(rb != null && checkedId > -1){
                    if(checkedId == R.id.radioButtonMale) {
                    	gendertxt = "M";
                    	Log.i("a", "m");
                    } else if(checkedId == R.id.radioButtonFemale) {
                    	gendertxt = "F";
                    	Log.i("a", "f");
                    }
                }

            }
        });
		
		bSignup.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				usernametxt = etUsername.getText().toString();
				passwordtxt = etPassword.getText().toString();
				emailtxt = etEmail.getText().toString();
				heightnum = Integer.valueOf(etHeight.getText().toString());
				weightnum = Integer.valueOf(etWeight.getText().toString());
				agenum = Integer.valueOf(etAge.getText().toString());
				stepLengthnum = Integer.valueOf(etStepLength.getText().toString());
								
				// Force user to fill up the form
				if (usernametxt.equals("") || passwordtxt.equals("") || emailtxt.equals("")) {
					Toast.makeText(getApplicationContext(),
							"Please complete the sign up form",
							Toast.LENGTH_LONG).show();
				} else {
					// Save new user data into Parse.com Data Storage
					final ParseUser user = new ParseUser();
					user.setUsername(usernametxt);
					user.setPassword(passwordtxt);
					user.setEmail(emailtxt);
					user.put("height", heightnum);
					user.put("weight", weightnum);
					user.put("age", agenum);
					user.put("gender", gendertxt);
					user.put("stepLength", stepLengthnum);
					
					user.signUpInBackground(new SignUpCallback() {
						public void done(ParseException e) {
							if (e == null) {
								// Show a simple Toast message upon successful registration
								Toast.makeText(getApplicationContext(),
										"Successfully Signed up, please log in.",
										Toast.LENGTH_LONG).show();
								
								ParseObject stepCount = new ParseObject("stepCount");
								stepCount.put("user", ParseUser.getCurrentUser());
								stepCount.saveInBackground();
									
								finish();
							} else {
								Toast.makeText(getApplicationContext(),
										"Sign up Error", Toast.LENGTH_LONG)
										.show();
							}
						}
					});

				}
			
			}
			
		});
}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sign_up, menu);
		return true;
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
}
