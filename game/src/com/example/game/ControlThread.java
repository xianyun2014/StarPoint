package com.example.game;

import android.os.Handler;

public class ControlThread extends Thread {
	private static Handler mhandler;
	private static GameData sd;
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
				Thread.sleep(100);
			} catch (Exception e) { }
			if (++n >= 10)
			{
				sd.add_onesecond();
				n = 0;
			}
			else
			{
				sd.add_second(100);
			}
			mhandler.sendMessage(mhandler.obtainMessage(0,""));
		}
	}
}
