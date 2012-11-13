package com.sloturtles.workout;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
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
	private Button mButton;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newworkout);
		mLayout = (LinearLayout) findViewById(R.id.LinearLayout2);
		mEditText = (EditText) findViewById(R.id.etWorkoutName);
		mButton = (Button) findViewById(R.id.bNewExercise);
		mButton.setOnClickListener(this);
	}
	
	public void onClick(View v) {
        mLayout.addView(createNewEditText(mEditText.getText().toString()));
    }

	private View createNewEditText(String string) {
		 final LayoutParams lparams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		 final EditText editText = new EditText(this);
		 editText.setLayoutParams(lparams);
		 List<EditText> list = new ArrayList<EditText>();
		 EditText toAdd = new EditText(this);
		 list.add(toAdd);
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
			Toast.makeText(this, saveWorkout() + " saved", Toast.LENGTH_SHORT)
			.show();
			sWorkoutName = "";
			finish();
			break;

		default:
			break;
		}

		return true;
	}

	public String saveWorkout() {
		EditText etWorkoutName;
		etWorkoutName = (EditText) findViewById(R.id.etWorkoutName);
		sWorkoutName = etWorkoutName.getText().toString();
		WorkoutsActivity.workoutList.add(new Workouts(001, sWorkoutName, false));
		return sWorkoutName;
	}
}

