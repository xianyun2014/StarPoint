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
	private ViewStar_Frame view_star;
	private ViewStar_Count view_count;
	
	private ControlThread ctrl_thread = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_activity);
        
        View vg = (View) findViewById(R.id.layout_game_star);
        view_star = ViewStar_Frame.GetView(vg);
        View vc = (View) findViewById(R.id.layout_game_star_count);
        view_count = ViewStar_Count.Get_View(vc);
        
        try{
        	GameData.readData(openFileInput("sava.sv"));
        }catch (Exception e){ }
        ctrl_thread = new ControlThread(mHandler);
        ctrl_thread.start();
    }

	@Override
	protected void onPause() {
		try{
			GameData.saveData(openFileOutput("sava.sv", MODE_PRIVATE));
		}catch (Exception e){ }
		Log.e("game", "onPause");
		super.onPause();
	}
	@Override
	protected void onStop() {
		Log.e("game", "onStop");
		super.onStop();
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
    	view_count.UIupdata();
	}
    
}
