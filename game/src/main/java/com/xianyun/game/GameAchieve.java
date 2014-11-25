package com.xianyun.game;

import android.util.Log;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GameAchieve {
	private static GameAchieve ga = null;
	 /*
     *  "CUR_STAR"
		"TOTAL_STAR"
		"SEC_YIELD"
		"UPDATA_TIMES"
		"CLICK_TIMES"
		"CLICK_YIELD"
		"CLICK_TOTALYIELD"
		"GAME_DAY"
		"GAME_HOUR"
		"MZ, XXDF, MFW, XXMGC, ZXGD, XKDP,MOON, XKZM, SKSD, WMYT"
     */
	private String[] ACHIEVE_LIST = new String[] {
            "第一次尝试", "CLICK_TIMES>=1",
            "点击爱好者", "CLICK_TIMES>=50",
            "勤奋手指", "CLICK_TIMES>=100",
            "手动达人", "CLICK_TIMES>=1000",
            "手动小马达", "CLICK_TIMES>=5000",
            "点击狂魔", "CLICK_TIMES>=10000",
            "乾坤一指", "CLICK_TIMES>=100000",
    };
	private HashMap<String, String> Achieve = new HashMap<String, String>(){{
        for (int i = 0; i < ACHIEVE_LIST.length; i += 2)
            put(ACHIEVE_LIST[i].replaceAll("\\s*", ""),
                    ACHIEVE_LIST[i+1].replaceAll("\\s*", ""));
    }};
    
    private GameAchieve(){ }

    public static GameAchieve GetAchieve()
    {
    	if (null == ga){
    		ga = new GameAchieve();
    	}
    	return ga;
    }
    private boolean isStatementTrue(String name, String op, long value)
    {
    	long cur_value = GameData.valueOf(name);
    	if (op.equals(">="))
    		return cur_value >= value;
    	else if (op.equals("<="))
    		return cur_value <= value;
    	else if (op.equals("="))
    		return cur_value == value;
    	else if (op.equals(">"))
    		return cur_value > value;
    	else if (op.equals("<"))
        	return cur_value < value;
    	else if (op.equals("!="))
    		return cur_value != value;
    	return false;
    }
    private boolean isAchieve(String str)
    {
        String[] oneStr = str.split("\\s*,\\s*");
        for (String s : oneStr)
        {
            s = s.replaceAll("\\s*", "");
            int i, j;
            for (i = 0; i < s.length() && -1 == "=!<>".indexOf(s.charAt(i)); ++i)
                continue;
            String name = s.substring(0, i);
            for (j = i; j < s.length() && -1 != "=!<>".indexOf(s.charAt(j)); ++j)
                continue;
            String op = s.substring(i, j);
            long value = Long.valueOf(s.substring(j));
            if (!isStatementTrue(name, op, value))
            	return false;
        }
        return true;
    }
    public void checkAchieve()
    {
    	Iterator it = Achieve.entrySet().iterator();
    	while (it.hasNext())
    	{
    		Map.Entry<String, String> e = (Map.Entry<String, String>)it.next();
    		if (isAchieve(e.getValue())){
    			ViewControl.GetViewControl(null).StartViewAchieve(e.getKey(), e.getValue());
                Achieve.remove(e.getKey());
                break;
    		}
    	}
    }
}
