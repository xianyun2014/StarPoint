package com.example.game;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity{
	private ViewControl ctrl_view = null;
	private ControlThread ctrl_thread = null;
	
	private boolean in_front; //is show
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_activity);
        
        ctrl_view = new ViewControl(findViewById(R.id.main_game_layout));
        
        try{
        	GameData.readData(openFileInput("sava.sv"));
        }catch (Exception e){ }
        ctrl_thread = new ControlThread(mHandler);
        ctrl_thread.start();
    }

	@Override
	protected void onStart() {
		in_front = true;
		super.onRestart();
	}

	@Override
	protected void onPause() {
		try{
			GameData.saveData(openFileOutput("sava.sv", MODE_PRIVATE));
		}catch (Exception e){ }
		in_front = false;
		super.onPause();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			this.moveTaskToBack(false);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	protected Handler mHandler = new Handler(){  /*Update UI message*/
    	@Override
    	public void handleMessage(Message msg){
    		UIupdata();
    	}
    };
    
    public void UIupdata()
	{
    	if (in_front){
    		ctrl_view.UIupdata();
    	}
	}
    
}
