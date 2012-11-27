package com.sloturtles.workout;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class StartWorkoutActivity extends Activity implements OnClickListener {
	//Global Variable Declaration/Initialization
	List<String> exerciseList = new ArrayList<String>();
	String globalItemName;
	int workoutPosition;
	Button bDone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		//Usual OnCreate Stuff
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_startworkout);
		
		//Assign Java variables to UI elements
		TextView tvItemName = (TextView) findViewById(R.id.tvItem);
		ListView lvExercises = (ListView)findViewById(R.id.ListView1);
		bDone = (Button)findViewById(R.id.bDone);

		//Take the data passed into StartWorkoutActivity, convert it to String and assign it
		//to the value of TextView
		Bundle data = this.getIntent().getExtras();
		String itemName = data.getString("itemName");
		tvItemName.setText(itemName);
		itemName = globalItemName;
		
		//Setup ListView of CheckBox
		lvExercises.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, exerciseList));
		lvExercises.setItemsCanFocus(false);
		lvExercises.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		
		//Set button
		bDone.setOnClickListener(this);
		
		//Execute function to populate the ListView
		populateList();

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_startworkout, menu);
		return true;
	}

	
	public void onClick(View arg0) {
		finish();
	}
	
	//Create dynamic EditTexts
	public void populateList() {
		for(int i = 0;i<WorkoutsActivity.workoutList.size();i++) {
			if(WorkoutsActivity.workoutList.get(i).equals(globalItemName)) {
				i = workoutPosition;	
			}
		}
		for(int x = 0;x<WorkoutsActivity.workoutList.get(workoutPosition).exerciseList.size();x++) {
			exerciseList.add(WorkoutsActivity.workoutList.get(workoutPosition).exerciseList.get(x).exerciseLabel);
		}
	}
}
