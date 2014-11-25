package com.example.game;

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
	String[] ACHIEVE_LIST = new String[] {
            "第一次点击", "CLICK_TIMES>=1",
            "点击爱好者", "CLICK_TIMES>=50",
            "点击狂人", "CLICK_TIMES>=100",
            "点击狂魔", "CLICK_TIMES>=1000",
            "手指控", "CLICK_TIMES>=5000",
            "玄冥一指", "CLICK_TIMES>=10000",
            "点击爱好者", "CLICK_TIMES>=100000",
            "", "",
            "", "",
            "", ""
    };
	private HashMap<String, String> Achieve = new HashMap<String, String>(){{
        for (int i = 0; i < ACHIEVE_LIST.length; i += 2)
            put(ACHIEVE_LIST[i].replaceAll("\\s*", ""),
                    ACHIEVE_LIST[i+1].replaceAll("\\s*", ""));
    }};
    
    private GameAchieve(){ }
    public GameAchieve GetAchieve()
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
    		return value >= cur_value;
    	else if (op.equals("<="))
    		return value <= cur_value;
    	else if (op.equals("="))
    		return value == cur_value;
    	else if (op.equals(">"))
    		return value > cur_value;
    	else if (op.equals("<"))
        	return value < cur_value;
    	else if (op.equals("!="))
    		return value != cur_value;
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
    private void checkAchieve()
    {
    	Iterator it = Achieve.entrySet().iterator();
    	while (it.hasNext())
    	{
    		Map.Entry<String, String> e = (Map.Entry<String, String>)it.next();
    		if (isAchieve(e.getValue())){
    			ViewControl.GetViewControl(null).StartViewAchieve(e.getKey(), e.getValue());
    		}
    			
    	}
    }
}
