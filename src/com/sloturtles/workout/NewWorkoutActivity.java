package com.sloturtles.workout;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


public class NewWorkoutActivity extends Activity implements OnClickListener {

	String sWorkoutName;
	private LinearLayout mLayout;
	private EditText mEditText;
	private EditText mEditText2;
	private Button mButton;
	List<EditText> excerciseList = new ArrayList<EditText>();
	public String STORE_PREFERENCES = "StorePrefs";


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newworkout);
		mLayout = (LinearLayout) findViewById(R.id.LinearLayout2);
		mEditText = (EditText) findViewById(R.id.etWorkoutName);
		mEditText2 = (EditText) findViewById(R.id.etExerciseLabel);
		mButton = (Button) findViewById(R.id.bNewExercise);
		mButton.setOnClickListener(this);
	}

	public void onClick(View v) {
		mLayout.addView(createNewEditText(mEditText.getText().toString()));
	}

	private View createNewEditText(String string) {
		@SuppressWarnings("deprecation")
		final LayoutParams lparams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		final EditText editText = new EditText(this);
		editText.setLayoutParams(lparams);
		EditText toAdd = new EditText(this);
		excerciseList.add(toAdd);
		return editText;
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
		WorkoutsActivity.workoutList.add(new Workout(sWorkoutName, false));
		for(int x = 0; x < excerciseList.size();x++){
			Exercise blah = new Exercise(excerciseList.get(x).getText().toString());
			WorkoutsActivity.workoutList.get(WorkoutsActivity.workoutList.size()-1).exerciseList.add(blah);
			Toast.makeText(this, mEditText2.getText().toString(), Toast.LENGTH_SHORT)
			.show();
		}
		SharedPreferences sp = getSharedPreferences(STORE_PREFERENCES, MODE_WORLD_READABLE); 
		Set<Workout> dbset = new HashSet<Workout>(WorkoutsActivity.workoutList);
		SharedPreferences.Editor spEditor = sp.edit();
		//workouts
		String workoutNames = "";
		for(int x = 0; x < WorkoutsActivity.workoutList.size();x++)
			workoutNames += "+" + WorkoutsActivity.workoutList.get(x).workoutTitle;
		
		//exercises
		String exerciseNames = "";
		for(int x = 0; x < WorkoutsActivity.workoutList.size();x++){
			for(int y = 0; y < WorkoutsActivity.workoutList.get(x).exerciseList.size(); y++){
				exerciseNames += "+" + WorkoutsActivity.workoutList.get(x).exerciseList.get(y).exerciseLabel;
			}
		}
		
		spEditor.putString("workoutTag", workoutNames);
		spEditor.putString("exerciseTag", exerciseNames);
		spEditor.commit();
	}
	
}

