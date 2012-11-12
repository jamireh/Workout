package com.sloturtles.workout;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;


public class NewWorkoutActivity extends Activity {
	
	String sWorkoutName;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newworkout);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_newworkout, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menuitem2:
			saveWorkout();
			Toast.makeText(this, sWorkoutName + " saved", Toast.LENGTH_SHORT)
	          .show();
			sWorkoutName = "";
			finish();
			break;
			
		default:
			break;
		}

		return true;
	}
	
	public void saveWorkout() {
		EditText etWorkoutName;
		etWorkoutName = (EditText) findViewById(R.id.etWorkoutName);
		sWorkoutName = etWorkoutName.getText().toString();
		WorkoutsActivity.workoutList.add(new Workouts(001, sWorkoutName, false));
	}
}

