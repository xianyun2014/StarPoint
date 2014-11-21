package com.example.game;

import android.view.View;
import android.widget.TextView;

public class ViewStar_Frame {
	private static ViewStar_Frame v = null;
	private TextView txtStar;
	
	private ViewStar_Frame(View view)
	{
		txtStar = (TextView) view.findViewById(R.id.txtStar);
	}
	public static ViewStar_Frame GetView(View view)
	{
		if (null == v)
		{
			v = new ViewStar_Frame(view);
		}
		return v;
	}
	public void UIupdata()
	{
		txtStar.setText(GameData.GetData().get_star());
	}
}
