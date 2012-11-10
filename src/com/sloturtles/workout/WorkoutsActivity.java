package com.sloturtles.workout;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class WorkoutsActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workouts);
        
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
        getMenuInflater().inflate(R.menu.activity_workouts, menu);
        return true;
    }
}
//Katelyn//