package com.sloturtles.workout;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class NewWorkoutActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newworkout);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_workouts, menu);
		return true;
	}
}
