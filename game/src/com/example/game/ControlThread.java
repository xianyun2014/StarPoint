package com.example.game;

import android.os.Handler;
import android.text.format.Time;
import android.util.Log;

public class ControlThread extends Thread {
	private static Handler mhandler;
	private static GameData sd;
	
	private Time tm = null;
	
	
	public ControlThread(Handler mHandler, GameData scenedata)
	{
		mhandler = mHandler;
		sd = scenedata;
	}
	@Override
	public void run()
	{
		int n = 0;
		while (true)
		{
			try {
				Thread.sleep(200);
			} catch (Exception e) { }
			if (++n >= 5)
			{
				sd.add_onesecond();
				n = 0;
			}
			else
			{
				sd.add_second(200);
			}
			mhandler.sendMessage(mhandler.obtainMessage(0,""));
		}
	}
}
