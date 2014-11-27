package com.xianyun.game;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

public class ViewAchieve_Show {
	private static ViewAchieve_Show vs = null;
	private View view;
	private TextView txt_achieve_name;
	private TextView txt_achieve_info;
	private ViewAchieve_Show(View v)
	{
        view = v;
        view.setVisibility(View.GONE);
		txt_achieve_name = (TextView) v.findViewById(R.id.txt_achieve_name);
		txt_achieve_info = (TextView) v.findViewById(R.id.txt_achieve_info);
	}
	public static ViewAchieve_Show Get_ViewAchieve_Show(View v)
	{
		if (null == vs)
		{
			vs = new ViewAchieve_Show(v);
		}
		return vs;
	}

    public void set_achieve(String name, String info)
    {
        txt_achieve_name.setText(name);
        txt_achieve_info.setText(info);
        view.setVisibility(View.VISIBLE);

        AnimationSet animation = new AnimationSet(true);
        AlphaAnimation alpha = new AlphaAnimation(1, 0);
        TranslateAnimation translate = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, -100,
                Animation.RELATIVE_TO_SELF, 0.0f
        );
        translate.setDuration(1000);
        animation.addAnimation(translate);
        alpha.setFillAfter(true);
        alpha.setStartOffset(4000);
        alpha.setDuration(500);
        alpha.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.INVISIBLE);
                view.clearAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animation.addAnimation(alpha);
        view.startAnimation(animation);
    }
}
