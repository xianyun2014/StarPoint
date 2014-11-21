package com.example.game;

import android.view.View;
import android.widget.TextView;

public class ViewStar_Frame {
	private static ViewStar_Frame v = null;
	private TextView txtStar;
	private ViewStar_Updata updataview;
	
	private ViewStar_Frame(View view)
	{
		txtStar = (TextView) view.findViewById(R.id.txtStar);
        View view_build_updata = (View) view.findViewById(R.id.view_build_updata);
        updataview = ViewStar_Updata.GetView(view_build_updata);
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
    	updataview.UIupdata(updataview.cur_select);
	}
}
