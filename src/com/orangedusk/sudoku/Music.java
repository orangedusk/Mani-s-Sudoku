package com.orangedusk.sudoku;

import android.content.Context;
import android.media.MediaPlayer;
//play music
public class Music {
	private static MediaPlayer mp=null;
	public static void play(Context context,int resource){
		stop(context);
		//start music only if the music preference is set
		if(PrefsFragment.getMusic(context)){
		mp=MediaPlayer.create(context,resource);
		mp.setLooping(true);
		mp.start();
		}
	}
//stop music	
	public static void stop(Context context){
		if(mp!=null){
			mp.stop();
			mp.release();
			mp=null;
		}
		
	}
}
