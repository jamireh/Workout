package com.sloturtles.workout;



import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;


public class EditWorkoutActivity extends Activity {
	
	String globalItemName;
	int workoutPosition;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		//Usual OnCreate Stuff
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editworkout);

		EditText etEditWorkoutName = (EditText) findViewById(R.id.etEditWorkoutName);
		
		//Take the data passed into StartWorkoutActivity, convert it to String and assign it
		//to the value of TextView
		Bundle data = this.getIntent().getExtras();
		String itemName = data.getString("itemName");
		etEditWorkoutName.setText(itemName);
		itemName = globalItemName;

		
		populateList();

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
			finish();
			break;
		default:
			break;
		}
		return true;
	}
	

	private void populateList() {
		// TODO Auto-generated method stub
		
	}
}
