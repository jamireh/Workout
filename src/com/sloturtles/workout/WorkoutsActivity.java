package com.sloturtles.workout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

public class WorkoutsActivity extends Activity implements OnItemClickListener {
	//Global Variable Declaration/Initialization
	EditText exerciseLabel, workoutName;
	Button newExerciseButton; 
	public static ArrayList <Workout> workoutList = new ArrayList<Workout>();
	public static List<String> lvWorkoutList = new ArrayList<String>();
	public static List<String> lvExerciseList = new ArrayList<String>();
	public static List<String> lvFavoritesList = new ArrayList<String>();	
	public static List<String> lvProgressList = new ArrayList<String>();
	public static List<String> progressWorkoutList = new ArrayList<String>();
	public int globalProgressIndex = 0; 
	public String itemSelected;
	public String progressItemSelected;
	public ListView lvWorkouts;
	public ListView lvFavorites;
	public ListView lvProgress;
	public String STORE_PREFERENCES = "StorePrefs";
	final Context context = this;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		//Usual OnCreate Stuff
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_workouts);

		//Assign Java variables to UI elements
		workoutName = (EditText) findViewById(R.id.etWorkoutName);
		exerciseLabel = (EditText) findViewById(R.id.etExerciseLabel); 
		newExerciseButton =  (Button) findViewById(R.id.bNewExercise);

		//Setup Tabs - StackOverFlow
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

		//Load data from SharedPreferences into ListView
		setupAdapters();

		//Enable OnItemClickListener on items in ListView
		lvWorkouts.setOnItemClickListener(this);
		lvFavorites.setOnItemClickListener(this);
		lvProgress.setOnItemClickListener(this);

		//Enable ContextMenu on items in ListView
		registerForContextMenu(lvWorkouts);
		registerForContextMenu(lvFavorites);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_workouts, menu);
		return true;
	}

	//"New" Button
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

	//Loads lvWorkoutList into ListView - StackOverFlow
	private void setupAdapters() {

		loadWorkout();

		//Workouts
		lvWorkouts = (ListView) findViewById(R.id.Workouts);
		ArrayAdapter<String> lvWorkoutAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lvWorkoutList);
		lvWorkouts.setAdapter(lvWorkoutAdapter);

		//Favorites
		lvFavorites = (ListView) findViewById(R.id.Favorites);
		ArrayAdapter<String> lvFavoritesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lvFavoritesList);
		lvFavorites.setAdapter(lvFavoritesAdapter);

		//Progress
		lvProgress = (ListView) findViewById(R.id.Progress);
		ArrayAdapter<String> lvProgressAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lvProgressList);
		lvProgress.setAdapter(lvProgressAdapter);
	}

	//Takes string from SharedPreferences, splits it into individual entries, and then loads it into an ArrayList (lvWorkoutList) 
	//to load into ListView
	private void loadWorkout() {
		SharedPreferences sp = getSharedPreferences(STORE_PREFERENCES, MODE_PRIVATE); 
		String longWorkoutTag = sp.getString("workoutTag", "");
		String longExerciseTag = sp.getString("exerciseTag", "");
		String longFavoriteTag = sp.getString("favoriteTag", "");
		String longProgressTitleTag = sp.getString("progressTitleTag", "");
		String longProgressWorkoutTag = sp.getString("progressWorkoutTag", "");
		lvWorkoutList = new ArrayList<String>(Arrays.asList(longWorkoutTag.split("[+]")));
		lvExerciseList = new ArrayList<String>(Arrays.asList(longExerciseTag.split("[-]")));
		lvFavoritesList = new ArrayList<String>(Arrays.asList(longFavoriteTag.split("[+]")));

		if(longProgressTitleTag.length() > 2) {
			System.out.println("if title triggered");
			lvProgressList = new ArrayList<String>(Arrays.asList(longProgressTitleTag.split("[+]")));
			progressWorkoutList = new ArrayList<String>(Arrays.asList(longProgressWorkoutTag.split("[-]")));
			System.out.println(longProgressWorkoutTag);
		} else {
			System.out.println("if title not triggered");
		}
		//array list of arrays, the upper is based on workouts, inner is exercises tied to that workout
		//loading needs to create new exercise and workout variables to be put in the master array so it saves/loads the workouts correctly
		workoutList.clear();
		for(int x = 0; x < lvWorkoutList.size();x++)
			if(lvWorkoutList.get(x).length() > 2)
				workoutList.add(new Workout(lvWorkoutList.get(x), false));

		try{
			for(int x = 0; x < workoutList.size();x++){
				if(lvExerciseList.get(x).length() > 2){
					List<String> temp = Arrays.asList(lvExerciseList.get(x).split("[+]"));
					for(int y = 0; y < temp.size();y++)
						workoutList.get(x).exerciseList.add(new Exercise(temp.get(y)));
					//toast("ex list " + Integer.toString(workoutList.get(x).exerciseList.size()));
				}
			}
		}catch(Exception e){}

		//loading favorites from SharedPreferences
		for(int z = 0;z < workoutList.size();z++) {
			for(int i = 0;i < lvFavoritesList.size();i++){ 
				if(lvFavoritesList.get(i).equals(workoutList.get(z).workoutTitle)) {
					workoutList.get(z).isFavorite = true;
				}
			}
		}
	}


	public void toast(String message){
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}

	//onResume runs whenever the screen is returned to from another screen. The same function can't run if returning 
	//from NewWorkout or from StartWorkout so the 'if' statements help determine which method to run based on where the app just 
	//came from. 1 = NewWorkoutActivity 2 = StartWorkoutActivity 3 = EditWorkoutActivity
	public void onResume() {
		super.onResume();
		setupAdapters();
	}

	//Creation of ContextMenu for "Edit", "Delete", and "Favorite" options - StackOverFlow
	public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		if(v == lvWorkouts) {
			menu.setHeaderTitle("Choose to...");
			menu.add(0, v.getId(), 0, "Edit Workout"); 
			menu.add(0, v.getId(), 0, "Delete Workout");
			menu.add(0, v.getId(), 0, "Add to Favorites");
		}
		if(v == lvFavorites) {
			menu.setHeaderTitle("Choose to...");
			menu.add(0, v.getId(), 0, "Edit Workout"); 
			menu.add(0, v.getId(), 0, "Delete Workout");
			menu.add(0, v.getId(), 0, "Remove from Favorites");
		}
	}

	//Method to run when an item in ListView is clicked
	public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
		int progressIndex = 0;
		System.out.println(arg0);
		if(arg0 == lvWorkouts || arg0 == lvFavorites) {
			itemSelected = ((TextView) view).getText().toString();

			Intent i = new Intent(this, StartWorkoutActivity.class);
			i.putExtra("itemName", itemSelected);
			startActivity(i);
		} else if(arg0 == lvProgress) {
			//Pop-dialog - StackOverFlow
			progressItemSelected = ((TextView) view).getText().toString();
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

			// set title
			alertDialogBuilder.setTitle("You completed these Workouts:");

			//find workout associated with entry clicked 
			for(int i = 0; i<lvProgressList.size();i++) {
				if(progressItemSelected.equals(lvProgressList.get(i))) {
					progressIndex = i;
				}
			}
			// set dialog message
			alertDialogBuilder
			.setMessage(progressWorkoutList.get(progressIndex).replace("+", " "))
			.setCancelable(true)
			.setNegativeButton("OK",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					// if this button is clicked, close
					dialog.cancel();
				}
			});
			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();

			// show it
			alertDialog.show();
		}
	}
	//Enables function on the ContextMenu
	public boolean onContextItemSelected(MenuItem item) {

		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		int index = info.position;


		if(item.getTitle() == "Edit Workout") {
			editWorkout(index);
		} else if(item.getTitle() == "Delete Workout") {
			deleteWorkout(index);
		} else if(item.getTitle() == "Add to Favorites") {
			addToFavorites(index);
			Toast.makeText(this, "Workout Added to Favorites", Toast.LENGTH_SHORT).show();
		} else if(item.getTitle() == "Remove from Favorites") {
			removeFromFavorites(index);
			Toast.makeText(this, "Workout Removed from Favorites", Toast.LENGTH_SHORT).show();
		} else {
			return false;
		}
		return true;
	}

	private void editWorkout(int index) {
		Intent i = new Intent(this, EditWorkoutActivity.class);
		i.putExtra("itemName", lvWorkoutList.get(index));
		i.putExtra("itemPosition", index);
		startActivity(i);
	}

	//Individual functions for each ContextMenu Entry
	public void addToFavorites(int index) {
		SharedPreferences sp = getSharedPreferences(STORE_PREFERENCES, MODE_PRIVATE);
		String favoriteId = lvWorkoutList.get(index);
		for(int i = 0;i<workoutList.size();i++) {
			if(favoriteId.equals(workoutList.get(i).workoutTitle)) {
				workoutList.get(i).isFavorite = true;
			}
		}
		String favoritesTag = "";
		for(int i = 0;i<workoutList.size();i++) {
			if(workoutList.get(i).isFavorite) {
				favoritesTag += workoutList.get(i).workoutTitle + "+";
			}
		}

		SharedPreferences.Editor spEditor = sp.edit();
		spEditor.putString("favoriteTag", favoritesTag);
		spEditor.commit();

		setupAdapters();
	}

	public void removeFromFavorites(int index) {
		SharedPreferences sp = getSharedPreferences(STORE_PREFERENCES, MODE_PRIVATE);
		String favoriteId = lvWorkoutList.get(index);
		for(int i = 0;i<workoutList.size();i++) {
			if(favoriteId.equals(workoutList.get(i).workoutTitle)) {
				workoutList.get(i).isFavorite = false;
			}
		}
		String favoritesTag = "";
		for(int i = 0;i<workoutList.size();i++) {
			if(workoutList.get(i).isFavorite) {
				favoritesTag += workoutList.get(i).workoutTitle + "+";
			}
		}

		SharedPreferences.Editor spEditor = sp.edit();
		spEditor.putString("favoriteTag", favoritesTag);
		spEditor.commit();

		setupAdapters();
	}


	private void deleteWorkout(int index) {
		String tempExerciseNames = "";

		for(int y = 0; y < workoutList.get(index).exerciseList.size(); y++){
			tempExerciseNames += workoutList.get(index).exerciseList.get(y).exerciseLabel + "+";
		}
		tempExerciseNames += "-";


		SharedPreferences sp = getSharedPreferences(STORE_PREFERENCES, MODE_PRIVATE); 

		String tempLongWorkoutTag = sp.getString("workoutTag", "");
		String tempLongExerciseTag = sp.getString("exerciseTag", "");
		String tempLongFavoritetag = sp.getString("favoriteTag", "");
		tempLongWorkoutTag = tempLongWorkoutTag.replace(lvWorkoutList.get(index) + "+", "");
		tempLongExerciseTag = tempLongExerciseTag.replace(tempExerciseNames, "");
		tempLongFavoritetag = tempLongFavoritetag.replace(lvWorkoutList.get(index) + "+", "");


		SharedPreferences.Editor spEditor = sp.edit();
		spEditor.putString("workoutTag", tempLongWorkoutTag);
		spEditor.putString("exerciseTag", tempLongExerciseTag);
		spEditor.putString("favoriteTag", tempLongFavoritetag);

		spEditor.commit();

		setupAdapters();

	}
}


//Class declarations for Workouts & Exercises
class Workout {
	int workoutId;
	boolean isFavorite;
	String workoutTitle = "";
	ArrayList<Exercise> exerciseList = new ArrayList<Exercise>();

	Workout(String workoutTitle, boolean isFavorite) {
		this.workoutTitle = workoutTitle;
		this.isFavorite = isFavorite;
	}
}
class Progress {
	String progressTitle = "";
	ArrayList<String> workoutList = new ArrayList<String>();

	Progress(String progressTitle) {
		this.progressTitle = progressTitle;
	}
}
class Exercise {
	String exerciseLabel = "";

	Exercise(String exerciseLabel) {
		this.exerciseLabel = exerciseLabel;
	}
}

