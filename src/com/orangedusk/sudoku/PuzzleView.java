package com.orangedusk.sudoku;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;

public class PuzzleView extends View{
	//constants
	private static final String TAG="Sudoku";
	private static final String SELX="selX";
	private static final String SELY="selY";
	private static final String VIEW_STATE="viewState";
	private static final int ID=42;
	
	//other fields
	private final Game game;
	private float width; //width of one tile
	private float height; //height of one tile
	private int selX;
	private int selY;
	private final Rect selRect= new Rect();
	private Rect r= new Rect();
	//declaring all paint objects to be used
	private Paint background= new Paint();
	private Paint dark= new Paint();
	private Paint hilite= new Paint();
	private Paint light= new Paint();
	private Paint foreground = new Paint(Paint.ANTI_ALIAS_FLAG);
	private Paint hint= new Paint();
	private Paint selected= new Paint();
	
	public PuzzleView(Context context) {
		super(context);
		this.game= (Game) context;
		setFocusable(true);
		setFocusableInTouchMode(true);
		setId(ID);
	}
	
	@Override
	protected Parcelable onSaveInstanceState(){
		Parcelable p=super.onSaveInstanceState();
		Log.d(TAG,"onSaveInstanceState");
		Bundle bundle =new Bundle();
		bundle.putInt(SELX, selX);
		bundle.putInt(SELY, selY);
		bundle.putParcelable(VIEW_STATE, p);
		return bundle;		
	}
	
	@Override
	protected void onRestoreInstanceState(Parcelable state){
		Log.d(TAG,"onRestoreInstanceState");
		Bundle bundle=(Bundle) state;
		select(bundle.getInt(SELX),bundle.getInt(SELY));
		super.onRestoreInstanceState(bundle.getParcelable(VIEW_STATE));
		return; 
	}
	
	@Override
	protected void onSizeChanged(int w,int h, int oldw,int oldh){
		width=w/9f;
		height=h/9f;
		getRect(selX,selY,selRect);
		Log.d(TAG,"onSizeChanged width="+width+",height="+height);
		super.onSizeChanged(w, h, oldw, oldh);
		
	}
	
	private void getRect(int x, int y, Rect rect) {
		rect.set((int)(x*width),(int)(y*height),(int)(x*width+width),(int)(y*height+height));
		
	}
	
	@Override
	protected void onDraw(Canvas canvas){
		//Draw the Background
		
		background.setColor(getResources().getColor(R.color.puzzle_background));
		canvas.drawRect(0,0,getWidth(),getHeight(),background);
		
		//Draw the board
		//Define colors for the gridlines
		
		dark.setColor(getResources().getColor(R.color.puzzle_dark));
		
		
		hilite.setColor(getResources().getColor(R.color.puzzle_hilite));
		
		
		light.setColor(getResources().getColor(R.color.puzzle_light));
		
		//Draw the minor gridlines
		
		for (int i=0;i<9;i++){
			canvas.drawLine(0,i*height,getWidth(),i*height,light);
			canvas.drawLine(0,i*height+1,getWidth(),i*height+1,hilite);
			
			canvas.drawLine(i*width,0,i*width,getHeight(),light);
			canvas.drawLine(i*width+1,0,i*width+1,getHeight(),hilite);
		}
		
		//Draw the minor gridlines 
		for (int i=0;i<9;i++){
			if(i%3!=0)
			continue;
			canvas.drawLine(0,i*height,getWidth(),i*height,dark);
			canvas.drawLine(0,i*height+1,getWidth(),i*height+1,hilite);
			
			canvas.drawLine(i*width,0,i*width,getHeight(),dark);
			canvas.drawLine(i*width+1,0,i*width+1,getHeight(),hilite);
		}
		//Draw the numbers
		//Define the color and style for numbers
		
		foreground.setColor(getResources().getColor(R.color.puzzle_foreground));
		
		foreground.setStyle(Style.FILL);
		foreground.setTextSize(height*0.75f);
		foreground.setTextScaleX(width/height);
		foreground.setTextAlign(Paint.Align.CENTER);
		
		//Draw the number in the centre of the tile
		FontMetrics fm= foreground.getFontMetrics();
		//Centering in X: use alignment(and X at midpoint)
		float x= width/2;
		//Centering in Y: measure ascent/descent first
		float y= height/2-(fm.ascent+fm.descent)/2;
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				canvas.drawText(this.game.getTileString(i,j),i*width+x,j*height+y, foreground);
			}
		}
		
		//Draw hints only of the preference is set
		if(PrefsFragment.getHints(this.game)){
		//Draw Hints
		//Pick a hint color based on #moves left
		
		int c[]= {getResources().getColor(R.color.puzzle_hint_0),
				getResources().getColor(R.color.puzzle_hint_1),
				getResources().getColor(R.color.puzzle_hint_2),};
		
		for(int i=0;i<9;i++){
			for (int j=0;j<9;j++){
				int movesLeft= 9-game.getUsedTiles(i,j).length;
				if(movesLeft<c.length){
					getRect(i,j,r);
					hint.setColor(c[movesLeft]);
					canvas.drawRect(r,hint);
					
				}
			}
		}
		}
		//Draw Selection...
		Log.d(TAG,"selRect="+selRect);
		selected.setColor(getResources().getColor(R.color.puzzle_selected));
		canvas.drawRect(selRect,selected);
	}

	public void setSelectedTile(int tile) {
		if(game.setTileIfValid(selX,selY,tile)){
			invalidate();
		}else{
			Log.d(TAG,"setSelectedTile :invalid:"+tile);
			startAnimation(AnimationUtils.loadAnimation(game,R.anim.shake));
			Vibrator v=(Vibrator)game.getSystemService(Context.VIBRATOR_SERVICE);
			v.vibrate(500);
		}
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event){
		if(event.getAction()!=MotionEvent.ACTION_DOWN)
			return super.onTouchEvent(event);
		select((int)(event.getX()/width),(int)(event.getY()/height));
		game.showKeypadOrError(selX, selY);
		Log.d(TAG,"onTouchEvent:x"+selX+",y"+selY);
		return true;
		
	}

	private void select(int x, int y) {
		invalidate(selRect);
		selX=Math.min(Math.max(x, 0),8);
		selY=Math.min(Math.max(y, 0),8);
		invalidate(selRect);
	}
}

