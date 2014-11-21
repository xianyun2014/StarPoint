package com.example.game;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageButton;

public class XYButtonView extends ImageButton{
	private ViewStar_Updata v;
    public XYButtonView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }
    
	@SuppressLint({ "NewApi", "ClickableViewAccessibility" }) @Override
	public boolean onTouchEvent(MotionEvent e)
	{
		switch (e.getAction()) //flash on click
		{
		case MotionEvent.ACTION_DOWN:
			setX(getX() + 5);
			setY(getY() + 5);
			break;
		case MotionEvent.ACTION_UP:
			setX(getX() - 5);
			setY(getY() - 5);
			break;
		}

		if (e.getAction() == MotionEvent.ACTION_UP) //dispatch different operate
		{
			if (e.getX() < 0 || e.getY() < 0 || e.getX() > getWidth() || e.getY() > getHeight())
				return true;
			if (null == v)
				v = ViewStar_Updata.GetView(null);
			
			switch (this.getId())
			{
			case R.id.imgStar:
				GameData.GetData().add_click();
				break;
			case R.id.jz_mz:
				v.setBuild(GameData.building.MZ);
				break;
			case R.id.jz_xxdf:
				v.setBuild(GameData.building.XXDF);
				break;
			case R.id.jz_mfw:
				v.setBuild(GameData.building.MFW);
				break;
			case R.id.jz_xxmgc:
				v.setBuild(GameData.building.XXMGC);
				break;
			case R.id.jz_xkdp:
				v.setBuild(GameData.building.XKDP);
				break;
			case R.id.jz_zxgd:
				v.setBuild(GameData.building.ZXGD);
				break;
			case R.id.jz_ylyqq:
				v.setBuild(GameData.building.MOON);
				break;
			case R.id.jz_xkzm:
				v.setBuild(GameData.building.XKZM);
				break;
			case R.id.zj_sksd:
				v.setBuild(GameData.building.SKSD);
				break;
			case R.id.jz_wmyt:
				v.setBuild(GameData.building.WMYT);
				break;
			case R.id.up_close:
				v.hide();
				break;
			case R.id.up_updata:
				GameData.GetData().build_updata(v.cur_select);
				break;
			}
		}
		return true;
	}
	
}
