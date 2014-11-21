package com.example.game;

import android.os.Handler;

public class ControlThread extends Thread {
	private static Handler mhandler;
	
	public ControlThread(Handler mHandler)
	{
		mhandler = mHandler;
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
				GameData.GetData().add_star();
				n = 0;
			}
			else
			{
				GameData.GetData().add_star_for_fps(200);
			}
			mhandler.sendMessage(mhandler.obtainMessage(0,""));
		}
	}
}
