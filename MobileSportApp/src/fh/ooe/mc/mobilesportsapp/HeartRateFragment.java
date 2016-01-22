package fh.ooe.mc.mobilesportsapp;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.parse.ParseObject;
import com.parse.ParseUser;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class HeartRateFragment extends Fragment {

	private static final int REQUEST_ENABLE_BT = 12;
	private TextView tvHeartrate;
	private TextView tvAvgHeartrate;
	private TextView tvMaxHeartrate;
	private TextView tvMinHeartrate;
	private TrainingSession session;
	private TextView tvTime;
	private Button btnStartStop;
	private boolean activityStarted = false;
	private ImageView ivHeart;
	private ParseUser user;
	private Handler handler = new Handler();
	private Runnable runnable = new Runnable() {
		@Override
		public void run() {
			session.duration++;

			String x = tvHeartrate.getText().toString();
			if (x != null && !x.equals("---")) {
				session.heartValues.add(Integer.parseInt(x));

				LayoutParams p = (LayoutParams) ivHeart.getLayoutParams();
				if (p.height == 100) {
					p.height = 80;
				} else {
					p.height = 100;
				}
				ivHeart.setLayoutParams(p);

			}
			tvTime.setText(toMinutes());
			handler.postDelayed(this, 1000);
		}
	};
	BluetoothGatt mBluetoothGatt;
	public final static UUID UUID_HEART_RATE_MEASUREMENT = UUID.fromString("00002a37-0000-1000-8000-00805f9b34fb");
	public final static UUID CLIENT_CHARACTERISTIC_CONFIG = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");

	private BluetoothAdapter.LeScanCallback leScanCallback = new BluetoothAdapter.LeScanCallback() {
		@Override
		public void onLeScan(final BluetoothDevice device, final int rssi, final byte[] scanRecord) {
			// your implementation here
			if (device != null && device.getName() != null && device.getName().contains("Heart"))
				;
			Log.i("hey", "ho");
			mBluetoothGatt = device.connectGatt(getActivity(), false, btleGattCallback);
		}
	};

	private final BluetoothGattCallback btleGattCallback = new BluetoothGattCallback() {

		@Override
		public void onCharacteristicChanged(BluetoothGatt gatt, final BluetoothGattCharacteristic characteristic) {
			// this will get called anytime you perform a read or write
			// characteristic operation
			Log.i("hey", "onCharacteristicChanged");
			byte[] data = characteristic.getValue();
			if (data != null && data.length > 1) {
				Byte b = data[1];
				final int x = b.intValue();

				getActivity().runOnUiThread(new Runnable() {

					@Override
					public void run() {
						Log.i("hey", "data = " + x);
						tvHeartrate.setText(String.valueOf(x));
						int minVal = 0;
						ivHeart.setImageDrawable(getResources().getDrawable(R.drawable.heart_red));
						if (session != null && session.heartValues.size() > 0) {
							 minVal = session.heartValues.get(session.heartValues.size() - 1);

							for (int val : session.heartValues) {
								if (val < minVal) {
									minVal = val;
								}
								session.avgHeartRate += val;
							}

							session.avgHeartRate = session.avgHeartRate / session.heartValues.size();
							tvAvgHeartrate.setText(String.valueOf(session.avgHeartRate));
						}
						int age = user.getInt("age");
						double max = 220 - age;
						if (session != null && session.heartValues.size() > 0) {
							double percent = session.heartValues.get(session.heartValues.size() - 1) / max * 100;
							tvMaxHeartrate.setText(String.valueOf((int)percent) + "%");
						}
						tvMinHeartrate.setText(String.valueOf(minVal));
					}
				});

			}
		}

		@Override
		public void onConnectionStateChange(final BluetoothGatt gatt, final int status, final int newState) {
			// this will get called when a device connects or disconnects
			Log.i("hey", "onConnectionStateChange");
			gatt.discoverServices();
		}

		@Override
		public void onServicesDiscovered(final BluetoothGatt gatt, final int status) {
			Log.i("hey", "onServicesDiscovered");
			List<BluetoothGattCharacteristic> characteristics = new ArrayList<BluetoothGattCharacteristic>();
			// this will get called after the client initiates a
			// BluetoothGatt.discoverServices() call
			List<BluetoothGattService> services = gatt.getServices();
			for (BluetoothGattService service : services) {
				characteristics = service.getCharacteristics();
				for (BluetoothGattCharacteristic c : characteristics) {
					if (c.getUuid().equals(UUID_HEART_RATE_MEASUREMENT)) {
						Log.i("hey", "hey");
						setCharacteristicNotification(c, true);
					}
				}
			}
		}

	};

	public void setCharacteristicNotification(BluetoothGattCharacteristic characteristic, boolean enabled) {

		mBluetoothGatt.setCharacteristicNotification(characteristic, enabled);

		// This is specific to Heart Rate Measurement.
		if (UUID_HEART_RATE_MEASUREMENT.equals(characteristic.getUuid())) {
			BluetoothGattDescriptor descriptor = characteristic.getDescriptor(CLIENT_CHARACTERISTIC_CONFIG);

			if (descriptor != null) {
				descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
				boolean status = mBluetoothGatt.writeDescriptor(descriptor);
				if (status)
					Log.d("hey", "Client Characteristic Config is changed to 2");
				else
					Log.e("hey", "Cannot set Client Characteristic Config");
			}
		}
	}

	public static HeartRateFragment newInstance() {
		HeartRateFragment fragment = new HeartRateFragment();
		return fragment;
	}

	public HeartRateFragment() {
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_heartrate, container, false);
		tvHeartrate = (TextView) rootView.findViewById(R.id.tv_heartrate);
		tvAvgHeartrate = (TextView) rootView.findViewById(R.id.tv_avgHeartrate);
		tvMaxHeartrate = (TextView) rootView.findViewById(R.id.tv_maxHeartrate);
		tvMinHeartrate = (TextView) rootView.findViewById(R.id.tv_minHeartrate);
		btnStartStop = (Button) rootView.findViewById(R.id.buttonStartStop);
		ivHeart = (ImageView) rootView.findViewById(R.id.iv_heart);
		tvTime = (TextView) rootView.findViewById(R.id.tvTime);
		
		user = ParseUser.getCurrentUser();
		
		BluetoothManager btManager = (BluetoothManager) getActivity().getSystemService(Context.BLUETOOTH_SERVICE);
		BluetoothAdapter btAdapter = btManager.getAdapter();
		if (btAdapter != null && !btAdapter.isEnabled()) {
			Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
		}

		btAdapter.startLeScan(leScanCallback);

		btnStartStop.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!activityStarted) {
					btnStartStop.setBackgroundResource(R.drawable.stop);
					session = new TrainingSession();
					tvTime.setText("00:00");
					handler.postDelayed(runnable, 1000);
					
				} else {
					btnStartStop.setBackgroundResource(R.drawable.start);
					handler.removeCallbacks(runnable);
					ParseObject trainingSession = new ParseObject("TrainingSession");
					trainingSession.put("user", user);
					trainingSession.put("duration", toMinutes());
					trainingSession.put("avgHeartRate", session.avgHeartRate);
					trainingSession.saveInBackground();
					
				}
				activityStarted = !activityStarted;
			}
		});

		return rootView;
	}
	
	public String toMinutes() {
		int min = session.duration / 60;
		int sec = session.duration % 60;
		String smin = String.valueOf(min);
		String ssec = String.valueOf(sec);
		if (min < 10) {
			smin = "0" + smin;
		}
		if (sec < 10) {
			ssec = "0" + ssec;
		}
		return smin + ":" + ssec;
	}
}
