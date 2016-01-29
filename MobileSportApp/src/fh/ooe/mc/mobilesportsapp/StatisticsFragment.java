package fh.ooe.mc.mobilesportsapp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class StatisticsFragment extends Fragment {

	private LineChart mWeekChart;
	private LineChart mMonthChart;

	public static StatisticsFragment newInstance() {
		StatisticsFragment fragment = new StatisticsFragment();
		return fragment;
	}

	public StatisticsFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_statistics, container, false);
		mWeekChart = (LineChart) rootView.findViewById(R.id.chart);
		mMonthChart = (LineChart) rootView.findViewById(R.id.chart2);
			
		getWeekStepCounts();
		getMonthStepCounts();
		
		return rootView;
	}
	
	
	void getWeekStepCounts() {

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 6);
		Date date = calendar.getTime();
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("stepCount");
		query.whereEqualTo("user", ParseUser.getCurrentUser())
		.whereGreaterThanOrEqualTo("createdAt", date)
		.orderByAscending("createdAt");
		query.findInBackground(new FindCallback<ParseObject>() {
		    public void done(List<ParseObject> list, ParseException e) {
		        if (e == null) {
		        	afterRetrievingWeek(list);
		        	Log.i("QueryResult", "Succesfully retrieved.");
		        } else {
		            Log.i("queryS", "Couldn't retrieve objects.");
		        }
		    }
		});		
	}
	
	void getMonthStepCounts() {

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -30);
		Date date = calendar.getTime();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("stepCount");
		query.whereEqualTo("user", ParseUser.getCurrentUser())
		.whereGreaterThanOrEqualTo("createdAt", date)
		.orderByAscending("createdAt");
		query.findInBackground(new FindCallback<ParseObject>() {
		    public void done(List<ParseObject> list, ParseException e) {
		        if (e == null) {
		        	afterRetrievingMonth(list);
		        	Log.i("QueryResult", "Succesfully retrieved.");
		        } else {
		            Log.i("queryS", "Couldn't retrieve objects.");
		        }
		    }
		});		
	}
	
	private void afterRetrievingMonth(List<ParseObject> list) {
		ArrayList<Entry> steps = new ArrayList<Entry>();   
		ArrayList<String> xVals = new ArrayList<String>(); 
		ArrayList<ParseObject> stepCounts = new ArrayList<ParseObject>(list);
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -30);

		for(int i = 0; i < 31; i++) {     
			xVals.add(calendar.get(Calendar.DAY_OF_MONTH) + "." + (calendar.get(Calendar.MONTH)+1));
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			steps.add(new Entry(0, i));
		}      
				
		for(ParseObject step: stepCounts) {        
			Date date = step.getCreatedAt();
			calendar.setTime(date);
			int creationDay = calendar.get(Calendar.DAY_OF_MONTH);
			int creationMonth = calendar.get(Calendar.MONTH);
			for(int i = 0; i < 31; i++) { 
				if((creationDay + "." + (creationMonth+1)).equals(xVals.get(i))) {
					steps.set(i, new Entry(step.getInt("numberOfSteps"), i));
				} 
			}
		}          
    	
		LineDataSet setSteps = new LineDataSet(steps, "Steps");
		setSteps.setAxisDependency(AxisDependency.LEFT);
		setSteps.setColors(new int[] { R.color.primary }, getActivity().getBaseContext());
		setSteps.setLineWidth(2);

		ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
		dataSets.add(setSteps);
		
		LineData data = new LineData(xVals, dataSets);
		mMonthChart.setData(data);
		mMonthChart.animateX(200);
		mMonthChart.invalidate();
	}
	
	private void afterRetrievingWeek(List<ParseObject> list) {
		ArrayList<Entry> steps = new ArrayList<Entry>();   
		ArrayList<String> xVals = new ArrayList<String>(); 
		ArrayList<ParseObject> stepCounts = new ArrayList<ParseObject>(list);
		
		Calendar calendar = Calendar.getInstance();   
		int day = calendar.get(Calendar.DAY_OF_MONTH) - 6;
		int month = calendar.get(Calendar.MONTH);     
		             
		for(int i = 0; i < 7; i++) {     
			xVals.add(day+i + "." + (month+1));
			steps.add(new Entry(0, i));
		}      
				
		for(ParseObject step: stepCounts) {        
			Date date = step.getCreatedAt();
			calendar.setTime(date);
			int creationDay = calendar.get(Calendar.DAY_OF_MONTH);
			for(int i = 0; i < 7; i++) {     
				if(creationDay == day+i) {
					steps.set(i, new Entry(step.getInt("numberOfSteps"), i));
				} 
			}
		}          
    	
		LineDataSet setSteps = new LineDataSet(steps, "Steps");
		setSteps.setAxisDependency(AxisDependency.LEFT);
		setSteps.setColors(new int[] { R.color.primary }, getActivity().getBaseContext());
		setSteps.setLineWidth(2);

		ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
		dataSets.add(setSteps);
		
		LineData data = new LineData(xVals, dataSets);
		mWeekChart.setData(data);
		mWeekChart.animateX(200);
		mWeekChart.invalidate();
	}
	
	
	

}
