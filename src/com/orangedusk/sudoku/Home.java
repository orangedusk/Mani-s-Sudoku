package com.orangedusk.sudoku;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Home extends Activity implements OnClickListener{
	private Button aboutButton,continueButton,exitButton,newButton;
	private static final String TAG="Sudoku";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		aboutButton=(Button)findViewById(R.id.aboutButton);
		aboutButton.setOnClickListener(this);
		continueButton=(Button)findViewById(R.id.continueButton);
		continueButton.setOnClickListener(this);
		exitButton=(Button)findViewById(R.id.exitButton);
		exitButton.setOnClickListener(this);
		newButton=(Button)findViewById(R.id.newButton);
		newButton.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
		case R.id.action_settings:
			startActivity(new Intent(this,Prefs.class));
			return true;
		}
		return false;
	}
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.aboutButton:
			Intent intent=new Intent(this,About.class);
			startActivity(intent);
			break;
		case R.id.continueButton:
			startGame(Game.DIFFICULTY_CONTINUE);
			break;
		case R.id.newButton:
			openNewGameDialog();
			break;
		case R.id.exitButton:
			finish();
			break;
		}
		
	}

	@Override 
	protected void onResume(){
		super.onResume();
		Music.play(this,R.raw.main);
	}
	
	@Override
	protected void onPause(){
		super.onPause();
		Music.stop(this);
	}
	private void openNewGameDialog() {
		new AlertDialog.Builder(this).setTitle(R.string.new_game_title)
		.setItems(R.array.difficulty,new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int i) {
				startGame(i);
				
			}

			
		})
		.show();
		
	}
	
	private void startGame(int i) {
		Log.d(TAG,"clicked on "+i);
		startActivity(new Intent(this,Game.class).putExtra(Game.KEY_DIFFICULTY,i));
	}

}
