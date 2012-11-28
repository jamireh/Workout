package com.sloturtles.workout;



import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


public class EditWorkoutActivity extends Activity {
	private LinearLayout mLayout;
	EditText exerciseET;
	String globalItemName;
	int workoutPosition;
	int index;
	public String STORE_PREFERENCES = "StorePrefs";
	String sWorkoutName;
	List<EditText> excerciseList = new ArrayList<EditText>();
	EditText etEditWorkoutName;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		//Usual OnCreate Stuff
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editworkout);
		mLayout = (LinearLayout) findViewById(R.id.LinearLayoutEdit);
		exerciseET = (EditText) findViewById(R.id.etExerciseLabelEdit);
		etEditWorkoutName = (EditText) findViewById(R.id.etEditWorkoutName);
		//Take the data passed into StartWorkoutActivity, convert it to String and assign it
		//to the value of TextView
		Bundle data = this.getIntent().getExtras();
		String itemName = data.getString("itemName");
		index = data.getInt("itemPosition");
		etEditWorkoutName.setText(itemName);
		globalItemName = itemName;
		excerciseList.add(exerciseET);
		populateList();
	}

	private View createNewEditText(String string) {
		@SuppressWarnings("deprecation")
		final LayoutParams lparams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		final EditText editText = new EditText(this);
		editText.setLayoutParams(lparams);
		excerciseList.add(editText);
		editText.setText(string);
		return editText;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_editworkout, menu);
		return true;
	}

	//Done Button
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menuitem2:
			WorkoutsActivity.superScreen = 3;
			Toast.makeText(this, "Workout Saved", Toast.LENGTH_SHORT).show();
			saveWorkout();
			finish();
			break;
		default:
			break;
		}
		return true;
	}


	private void populateList() {
		int index = 0;
		for(int i = 0;i<WorkoutsActivity.workoutList.size();i++) {
			if(WorkoutsActivity.workoutList.get(i).workoutTitle.equals(globalItemName)) {
				index = i;
			}
		}
		exerciseET.setText(WorkoutsActivity.workoutList.get(index).exerciseList.get(0).exerciseLabel);
		for(int x = 1; x < WorkoutsActivity.workoutList.get(index).exerciseList.size();x++){
			mLayout.addView(createNewEditText(WorkoutsActivity.workoutList.get(index).exerciseList.get(x).exerciseLabel));
		}
	}

	//Method for saving Workouts
	public void saveWorkout() {
		//remove the workout you are working on and add a new one
		sWorkoutName = etEditWorkoutName.getText().toString();
		WorkoutsActivity.workoutList.add(new Workout(sWorkoutName, false));
		WorkoutsActivity.workoutList.remove(index);
		WorkoutsActivity.workoutList.get(index).exerciseList.clear();
		
		//old saveWorkout() code
		for(int x = 0; x < excerciseList.size();x++) {
			Exercise blah = new Exercise(excerciseList.get(x).getText().toString());
			WorkoutsActivity.workoutList.get(index).exerciseList.add(blah);
		}
		SharedPreferences sp = getSharedPreferences(STORE_PREFERENCES, MODE_WORLD_READABLE); 
		SharedPreferences.Editor spEditor = sp.edit();

		String workoutNames = "";
		for(int x = 0; x < WorkoutsActivity.workoutList.size();x++)
			workoutNames += WorkoutsActivity.workoutList.get(x).workoutTitle + "+";

		String exerciseNames = "";
		for(int x = 0; x < WorkoutsActivity.workoutList.size();x++){
			for(int y = 0; y < WorkoutsActivity.workoutList.get(x).exerciseList.size(); y++){
				exerciseNames += WorkoutsActivity.workoutList.get(x).exerciseList.get(y).exerciseLabel + "+";
			}
			exerciseNames += "-";
		}

		spEditor.putString("workoutTag", workoutNames);
		spEditor.putString("exerciseTag", exerciseNames);

		//debug
		toast(workoutNames);
		toast(exerciseNames);

		spEditor.commit();
	}

	public void toast(String message){
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}
}
