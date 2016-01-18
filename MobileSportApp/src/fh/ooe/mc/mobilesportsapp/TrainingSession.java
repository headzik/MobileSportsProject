package fh.ooe.mc.mobilesportsapp;

import java.util.ArrayList;
import java.util.List;

public class TrainingSession {
	int duration;
	int avgHeartRate;
	List<Integer> heartValues;
	
	public TrainingSession(){
		duration = 0;
		avgHeartRate = 0;
		heartValues = new ArrayList<Integer>();
	}
}
