package com.xianyun.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity{
	private ViewControl ctrl_view = null;
	private TimeThreadControl ctrl_thread = null;
	
	private boolean in_front; //is show
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //解决不同方式打开软件显示异常问题
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_activity);
        
        ctrl_view = ViewControl.GetViewControl(findViewById(R.id.main_game_layout), mHandler);
        try{
        	GameData.readData(openFileInput("sava.sv"));
        }catch (Exception e){
        	GameData.GetData();
        }
        ctrl_thread = new TimeThreadControl(mHandler);
        ctrl_thread.start();
        ctrl_view.UIALLupdata();
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
