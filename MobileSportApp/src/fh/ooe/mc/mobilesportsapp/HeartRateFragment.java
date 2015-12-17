package fh.ooe.mc.mobilesportsapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HeartRateFragment extends Fragment{

	public static HeartRateFragment newInstance() {
		HeartRateFragment fragment = new HeartRateFragment();
		return fragment;
	}

	public HeartRateFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_heartrate, container, false);
		HardwareConnectorServiceConnection con = new HardwareConnectorServiceConnection(getActivity(), new HardwareConnectorServiceConnection.Listener() {
			
			@Override
			public void onHardwareConnectorServiceDisconnected() {
				Log.i("connector", "disconnected");
			}
			
			@Override
			public void onHardwareConnectorServiceConnected(HardwareConnectorService hardwareConnectorService) {
				Log.i("connector", "connected");
				
			}
		});
		
		return rootView;
	}
	

}
