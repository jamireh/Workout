package com.sloturtles.workout;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class StartWorkoutActivity extends Activity {
	//Global Variable Declaration/Initialization
	List<String> exerciseList = new ArrayList<String>();
	String globalItemName;
	int workoutPosition;
	public String STORE_PREFERENCES = "StorePrefs";
	public boolean newDay = false;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//Usual OnCreate Stuff
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_startworkout);

		//Assign Java variables to UI elements
		TextView tvItemName = (TextView) findViewById(R.id.tvItem);
		ListView lvExercises = (ListView)findViewById(R.id.ListView1);

		//Take the data passed into StartWorkoutActivity, convert it to String and assign it
		//to the value of TextView
		Bundle data = this.getIntent().getExtras();
		String itemName = data.getString("itemName");
		tvItemName.setText(itemName);
		globalItemName = itemName;


		//Setup ListView of CheckBox
		lvExercises.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, exerciseList));
		lvExercises.setItemsCanFocus(false);
		lvExercises.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

		//Execute function to populate the ListView
		populateList();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_startworkout, menu);
		return true;
	}

	//Done Button
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menuitem2:
			saveProgress();
			Toast.makeText(this, "Workout Completed and Logged", Toast.LENGTH_SHORT).show();
			finish();
			break;
		default:
			break;
		}
		return true;
	}

	//Create dynamic EditTexts
	public void populateList() {
		for(int i = 0;i<WorkoutsActivity.workoutList.size();i++) {
			if(WorkoutsActivity.workoutList.get(i).workoutTitle.equals(globalItemName)) {
				workoutPosition = i;
			}
		}
		for(int x = 0;x<WorkoutsActivity.workoutList.get(workoutPosition).exerciseList.size();x++) {
			exerciseList.add(WorkoutsActivity.workoutList.get(workoutPosition).exerciseList.get(x).exerciseLabel);
		}
	}
	public void saveProgress() {
		Calendar c = Calendar.getInstance(); 
		String date = Integer.toString(c.get(Calendar.MONTH) + 1) + "/" + Integer.toString(c.get(Calendar.DATE)) + "/" + Integer.toString(c.get(Calendar.YEAR));

		SharedPreferences sp = getSharedPreferences(STORE_PREFERENCES, MODE_PRIVATE); 
		SharedPreferences.Editor spEditor = sp.edit();

		String templongTitlesTag = sp.getString("progressTitleTag", "");
		if(WorkoutsActivity.lvProgressList.size() == 0) {
			templongTitlesTag += date + "+";
			System.out.println("first time run ever");
		}
		for(int i = 0;i<WorkoutsActivity.lvProgressList.size();i++) {
			if(WorkoutsActivity.lvProgressList.get(i).equals(date)) {

			} else {
				templongTitlesTag += date + "+";
				newDay = true;
				System.out.println("new day");
			}
		}
		System.out.println(templongTitlesTag);
		spEditor.putString("progressTitleTag", templongTitlesTag);


		String templongWorkoutsTag = sp.getString("progressWorkoutTag", "");
		templongWorkoutsTag += globalItemName + "+";
		if(newDay) {
			templongWorkoutsTag += "-";
		}
		System.out.println(templongWorkoutsTag);
		spEditor.putString("progressWorkoutTag", templongWorkoutsTag);

		spEditor.commit();
	}
}
