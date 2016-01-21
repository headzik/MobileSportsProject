package fh.ooe.mc.mobilesportsapp;

import java.util.ArrayList;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class StatisticsFragment extends Fragment {

	private LineChart mChart;

	public static StatisticsFragment newInstance() {
		StatisticsFragment fragment = new StatisticsFragment();
		return fragment;
	}

	public StatisticsFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_statistics, container, false);
		mChart = (LineChart) rootView.findViewById(R.id.chart);

		ArrayList<Entry> steps = new ArrayList<Entry>();
		ArrayList<String> xVals = new ArrayList<String>();

		Entry steps1 = new Entry(850, 0);
		steps.add(steps1);
		Entry steps2 = new Entry(435, 1);
		steps.add(steps2);
		Entry steps3 = new Entry(600, 2);
		steps.add(steps3);
		

		LineDataSet setSteps = new LineDataSet(steps, "Steps");
		setSteps.setAxisDependency(AxisDependency.LEFT);
		setSteps.setColors(new int[] { R.color.primary }, getActivity().getBaseContext());
		ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
		setSteps.setLineWidth(2);

		dataSets.add(setSteps);

		xVals.add("14.12.");
		xVals.add("15.12.");
		xVals.add("16.12.");
		xVals.add("17.12.");

		LineData data = new LineData(xVals, dataSets);
		mChart.setData(data);
		mChart.animateX(200);
		mChart.invalidate();

		return rootView;
	}
}
