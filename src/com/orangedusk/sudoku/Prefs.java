package com.orangedusk.sudoku;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;

public class Prefs extends Activity{
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override 
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		getFragmentManager().beginTransaction().replace(android.R.id.content, new PrefsFragment())
		.commit();
	}

}
