package com.sloturtles.workout;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class StartWorkoutActivity extends Activity {
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		//Usual OnCreate Stuff
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_startworkout);
		
		//Assign Java variables to UI elements
		TextView tvItemName = (TextView) findViewById(R.id.tvItem);

		//Take the data passed into StartWorkoutActivity, convert it to String and assign it
		//to the value of TextView
		Bundle data = this.getIntent().getExtras();
		String itemName = data.getString("itemName");
		tvItemName.setText(itemName);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_startworkout, menu);
		return true;
	}

}
