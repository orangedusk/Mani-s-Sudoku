package com.orangedusk.sudoku;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class PrefsFragment extends PreferenceFragment{
	//option names and default values
	private static final String OPT_MUSIC = "music";
	private static final boolean OPT_MUSIC_DEF=true;
	private static final String OPT_HINTS="hints";
	private static final boolean OPT_HINTS_DEF=true;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
		
	}
	
	/** Get the current value of the music preference**/
	public static  boolean getMusic(Context context){
		return PreferenceManager.getDefaultSharedPreferences(context)
				.getBoolean(OPT_MUSIC,OPT_MUSIC_DEF);
	}
	/** Get the current value of the hints preference**/
	public static  boolean getHints(Context context){
		return PreferenceManager.getDefaultSharedPreferences(context)
				.getBoolean(OPT_HINTS,OPT_HINTS_DEF);
	}
	
	
}
