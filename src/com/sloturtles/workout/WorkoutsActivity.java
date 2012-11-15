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
	public static ArrayList <Workout> workoutList = new ArrayList<Workout>();

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

//clay is cool

class Workout {

	int workoutId;
	boolean isFavorite;
	String workoutTitle;
	ArrayList<Exercise> exerciseList = new ArrayList<Exercise>();

	Workout(String workoutTitle, boolean isFavorite) {
		this.workoutTitle = workoutTitle;
		this.isFavorite = isFavorite;
	}
}
class Exercise {

	String exerciseLabel;

	Exercise(String exerciseLabel) {
		this.exerciseLabel = exerciseLabel;
	}
}

