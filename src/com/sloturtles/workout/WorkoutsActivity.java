package com.sloturtles.workout;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class WorkoutsActivity extends Activity {

	EditText exerciseLabel, workoutName;
	Button newExerciseButton; 
	public static ArrayList <Workouts> workoutList = new ArrayList<Workouts>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_workouts);

		workoutName = (EditText) findViewById(R.id.etWorkoutName);
		exerciseLabel = (EditText) findViewById(R.id.etExerciseLabel); 
		newExerciseButton =  (Button) findViewById(R.id.bNewExercise);

		TabHost th = (TabHost)findViewById(R.id.tabhost);
		th.setup();
		TabSpec specs;
		specs = th.newTabSpec("tag1");
		specs.setContent(R.id.Favorites);
		specs.setIndicator("Favorites");
		th.addTab(specs);
		specs = th.newTabSpec("tag2");
		specs.setContent(R.id.Workouts);
		specs.setIndicator("Workouts");
		th.addTab(specs);
		specs = th.newTabSpec("tag3");
		specs.setContent(R.id.Progress);
		specs.setIndicator("Progress");
		th.addTab(specs);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_workouts, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menuitem1:
			Intent i = new Intent(this, NewWorkoutActivity.class);
			startActivity(i);
			break;

		default:
			break;
		}

		return true;
	}
}
class Workouts {

	int workoutId;
	boolean isFavorite;
	String workoutTitle;
	ArrayList<Exercises> exerciseList = new ArrayList<Exercises>();

	Workouts(int workoutId, String workoutTitle, boolean isFavorite) {
		this.workoutId = workoutId;
		this.workoutTitle = workoutTitle;
		this.isFavorite = isFavorite;
	}
}
class Exercises {

	String exerciseLabel;

	Exercises(String exerciseLabel) {
		this.exerciseLabel = exerciseLabel;
	}
}

