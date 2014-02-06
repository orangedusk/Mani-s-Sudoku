package com.orangedusk.sudoku;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

public class Keypad extends Dialog {
	
	private static final String TAG="Sudoku";
	
	private final View keys[]= new View[9];
	private View keypad;
	private PuzzleView puzzleView;

	public Keypad(Context context,PuzzleView puzzle) {
		super(context);
		puzzleView=puzzle;
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		setTitle(R.string.keypad_title);
		setContentView(R.layout.keypad);
		findViews();
		setListeners();
	}
	
	private void findViews() {
		keypad=findViewById(R.id.keypad);
		keys[0]=findViewById(R.id.keypad_1);
		keys[1]=findViewById(R.id.keypad_2);
		keys[2]=findViewById(R.id.keypad_3);
		keys[3]=findViewById(R.id.keypad_4);
		keys[4]=findViewById(R.id.keypad_5);
		keys[5]=findViewById(R.id.keypad_6);
		keys[6]=findViewById(R.id.keypad_7);
		keys[7]=findViewById(R.id.keypad_8);
		keys[8]=findViewById(R.id.keypad_9);		
	}
	private void setListeners() {
		for(int i=0;i<keys.length;i++){
			final int t=i+1;
			keys[i].setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					returnResult(t);
					
				}

				
			});
		}
		
		keypad.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				returnResult(0);
				
			}
		});
	}
	
	private void returnResult(int tile) {
		puzzleView.setSelectedTile(tile);
		dismiss();
	}
	
	

	
	

}
