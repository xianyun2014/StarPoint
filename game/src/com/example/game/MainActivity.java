package com.example.game;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity{
	private GameData sd;
	private ViewStar_Frame view_star;
	private ControlThread ctrl_thread = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_activity);
        
        View v = (View) findViewById(R.id.layout_game_star);
        view_star = ViewStar_Frame.GetView(v);
        
        sd = GameData.CreateData();
        ctrl_thread = new ControlThread(mHandler, sd);
        ctrl_thread.start();
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
    	view_star.UIupdata();
	}
    
}
