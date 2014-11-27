package com.xianyun.game;

import android.util.Log;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GameAchieve  implements java.io.Serializable {
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
		"MZ, XXDF, MFW, XXMGC, ZXGD, XKDP, MOON, XKZM, SKSD, WMYT"
     */
	private String[] ACHIEVE_LIST = new String[] {
        "第一次点击",    "CLICK_TIMES>=1",
        "十次点击",      "CLICK_TIMES>=10",
        "点击爱好者",    "CLICK_TIMES>=50",
        "勤奋手指",      "CLICK_TIMES>=100",
        "手动达人",      "CLICK_TIMES>=1000",
        "手动小马达",    "CLICK_TIMES>=5000",
        "点击狂魔",      "CLICK_TIMES>=10000",
        "按键精灵",      "CLICK_TIMES>=100000",
        "小小收集者",    "SEC_YIELD>=10",
        "稳定收入者",    "SEC_YIELD>=100",
        "超强实力派",    "SEC_YIELD>=1000",
        "著名收藏家",    "SEC_YIELD>=10000",
        "顶级收集者",    "SEC_YIELD>=100000",
        "行星掠夺者",    "SEC_YIELD>=1000000",
        "星系掠夺者",    "SEC_YIELD>=10000000",
        "宇宙吞噬者",    "SEC_YIELD>=100000000",
        "化身黑洞",      "SEC_YIELD>=1000000000",
        "单击", "MZ>=1",
        "连击", "MZ>=10",
        "黄金手指", "MZ>=50",
        "远古一指", "MZ>=100",
        "乾坤一指", "MZ>=200",
        "初识功法", "XXDF>=1",
        "功法小成", "XXDF>=10",
        "功法大成", "XXDF>=50",
        "功法圆满", "XXDF>=100",
        "功参造化", "XXDF>=200",
        "元素之力", "MFW>=1",
        "元素采集器", "MFW>=10",
        "魔法储存器", "MFW>=50",
        "魔法喷泉", "MFW>=100",
        "魔力之源", "MFW>=200",
        "工厂化生产", "XXMGC>=1",
        "产业链", "XXMGC>=10",
        "工业革命", "XXMGC>=50",
        "工业帝国", "XXMGC>=100",
        "宇宙工厂", "XXMGC>=200",
        "国家初建", "ZXGD>=1",
        "国度联盟", "ZXGD>=10",
        "星球联邦", "ZXGD>=50",
        "星系大联合", "ZXGD>=100",
        "神之国度", "ZXGD>=200",
        "冲向宇宙", "XKDP>=1",
        "初现峥嵘", "XKDP>=10",
        "星系争霸", "XKDP>=50",
        "恐惧大炮", "XKDP>=100",
        "星际巡洋炮", "XKDP>=200",
        "月球殖民地", "MOON>=1",
        "殖民地联合", "MOON>=10",
        "殖民总动员", "MOON>=50",
        "殖民基地", "MOON>=100",
        "无限移民", "MOON>=200",
        "星际传送", "XKZM>=1",
        "咫尺光年", "XKZM>=10",
        "星系小挪移", "XKZM>=50",
        "宇宙大挪移", "XKZM>=100",
        "无视距离", "XKZM>=200",
        "新的宇宙", "SKSD>=1",
        "宇宙小联合", "SKSD>=10",
        "更多的宇宙", "SKSD>=50",
        "宇宙大一统", "SKSD>=100",
        "超然宇宙外", "SKSD>=200",
        "一切的起源", "WMYT>=1",
        "深入源头", "WMYT>=10",
        "位面大爆发", "WMYT>=50",
        "征服所有位面", "WMYT>=100",
        "神一般的存在", "WMYT>=200"
    };
	private HashMap<String, String> Achieve = new HashMap<String, String>(){{
        for (int i = 0; i < ACHIEVE_LIST.length; i += 2)
            put(ACHIEVE_LIST[i].replaceAll("\\s*", ""),
                    ACHIEVE_LIST[i+1].replaceAll("\\s*", ""));
    }};
    
    private GameAchieve(){ }

    public static void ResetGameAchieve(GameAchieve g)
    {
        ga = g;
    }
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
        try {
            Iterator it = Achieve.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> e = (Map.Entry<String, String>) it.next();
                if (isAchieve(e.getValue())) {
                    ViewControl.GetViewControl(null, null).StartViewAchieve(e.getKey(), e.getValue());
                    Achieve.remove(e.getKey());
                    GameData.GetData().add_one_achieve();
                    break;
                }
            }
        }catch (Exception e){ }
    }
}
