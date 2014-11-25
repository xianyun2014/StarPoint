package com.example.game;

import android.os.Handler;

public class TimeThreadControl extends Thread {
	private static Handler mhandler;
	
	public TimeThreadControl(Handler mHandler)
	{
		mhandler = mHandler;
	}
	@Override
	public void run()
	{
		while (true)
		{
			try {
				Thread.sleep(1000);
				GameData.GetData().add_star();
			} catch (Exception e) { }
			mhandler.sendMessage(mhandler.obtainMessage(0,""));
		}
	}
}
