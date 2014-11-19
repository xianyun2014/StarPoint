package com.example.game;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity{
	private TextView txtStar;
	private GameData sd;
	private BuildUpdataView updataview;
	
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_frame);
        
        ///Ѱ����һ��xml�е�view
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
        View rootview = (View) root.getChildAt(0);

        View bv = inflater.inflate(R.layout.activity_main, root);
        LinearLayout.LayoutParams lp = (LayoutParams) bv.getLayoutParams();
        
        lp.height = (int) (getWindowManager().getDefaultDisplay().getHeight() * 0.9);
        lp.bottomMargin = (int) (getWindowManager().getDefaultDisplay().getHeight() * 0.1);
        bv.setLayoutParams(lp);
        FrameLayout mf = (FrameLayout) findViewById(R.id.context_frame);
        txtStar = (TextView) bv.findViewById(R.id.txtStar);

        View view = inflater.inflate(R.layout.build_info_view, root);
        updataview = BuildUpdataView.CreateView(view);
        sd = GameData.CreateData();
        new ControlThread(mHandler, sd).start();
        
    }
    
    @SuppressLint("HandlerLeak") 
    protected Handler mHandler = new Handler(){  /*Update UI message*/
    	@Override
    	public void handleMessage(Message msg){
    		UIupdata();
    	}
    };
    
    @SuppressLint("NewApi")
    public void UIupdata()
	{
    	txtStar.setText(sd.StrPreOper(sd.get_Star_Str()));
    	updataview.UIupdata(updataview.cur_select);
	}
    
}
