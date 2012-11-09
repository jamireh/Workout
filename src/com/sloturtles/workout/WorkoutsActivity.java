package com.sloturtles.workout;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class WorkoutsActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workouts);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_workouts, menu);
        return true;
    }
}
