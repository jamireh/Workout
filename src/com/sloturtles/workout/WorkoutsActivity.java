package com.sloturtles.workout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

public class WorkoutsActivity extends Activity {
	EditText exerciseLabel, workoutName;
	Button newExerciseButton; 
	public static ArrayList <Workout> workoutList = new ArrayList<Workout>();
	public static List<String> lvWorkoutList = new ArrayList<String>();
	public static int superScreen; 
	public ListView lvWorkouts;
	public String STORE_PREFERENCES = "StorePrefs";




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
		
		fromNewWorkoutActivity();

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

	private void setupAdapters() {
		lvWorkouts = (ListView) findViewById(R.id.Workouts);
		loadWorkout();
		ArrayAdapter<String> lvAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lvWorkoutList);
		lvWorkouts.setAdapter(lvAdapter);

	}

	private void loadWorkout() {
		SharedPreferences sp = getSharedPreferences(STORE_PREFERENCES, MODE_WORLD_READABLE); 
		String longWorkoutTag = sp.getString("workoutTag", "");
		lvWorkoutList = new ArrayList<String>(Arrays.asList(longWorkoutTag.split("[+]")));
	}

	//onResume runs whenever the screen is returned to from another screen. The same function can't run if returning 
	//from NewWorkout or from StartWorkout so the 'if' statements help determine which method to run based on where the app just 
	//came from. 1 = NewWorkoutActivity 2 = StartWorkoutActivity 3 = EditWorkoutActivity
	public void onResume() {
		super.onResume();
		if(superScreen == 1) {
			fromNewWorkoutActivity();
		} 
		if(superScreen == 2) {
			fromStartWorkoutActivity();
		} 
		if(superScreen == 3) {
			fromEditWorkoutActivity();
		}
	}


	public void fromNewWorkoutActivity() {
		loadWorkout();
		setupAdapters();

	}

	private void fromStartWorkoutActivity() {
		// TODO Auto-generated method stub

	}

	private void fromEditWorkoutActivity() {
		// TODO Auto-generated method stub

	}

	public void debugToast(String message){
		Toast.makeText(this, "+" + message + "+", Toast.LENGTH_SHORT).show();
	}

}



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

