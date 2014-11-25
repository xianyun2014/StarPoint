package com.xianyun.game;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import android.util.Log;

public class GameData implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	private static GameData gd = null; //single 

	public enum building{MZ, XXDF, MFW, XXMGC, ZXGD, XKDP,
		MOON, XKZM, SKSD, WMYT}; //building's name, cann't change this sequence
	private long game_day;
	private long game_hour;
	
	private long star; //当前星点数
	private long star_history_max; //历史最大星点数
	private long star_history_total; //历史总计星点数
	private long yield_sec; //每秒产量
	private long yield_multiple; //产量倍率
	private long click_num; //点击次数
	private long click_yield;  //点击产量
	private long click_total_yield; //点击总量
	private long updata_num; //升级总次数
	
	private String[] build_name; //建筑名字
	private String[] build_info; //建筑描述信息
	private long [] build_cur_level; //建筑当前等级
	private long [] build_sec_yield; //建筑秒产
	private long [] build_total_yield; //建筑总产
	private long [] build_multiple; //建筑倍率
	private long [] build_updata_cost; //下级花费
	private long [] build_next_add; //下级增产
	
	private GameData(){//private ,can't default create 
		game_day = 0L;
		game_hour = 0L;
		
		star = 0L;
		star_history_max = 0L;
		star_history_total = 0L;
		yield_sec = 1L;
		yield_multiple = 1L;
		click_num = 0L;
		click_yield = 1L;
		click_total_yield = 0L;
		updata_num = 0L;

        build_name = new String[]{"星空指", "吸星大法", "星星魔法屋",
                "星星梦工场", "摘星国度", "星空大炮",
                "月亮跃迁器", "星空之门", "时空隧道", "位面源头"};
        build_info = new String[]{"勤劳的人儿总是不缺星点，手指头越多点的越快哦~",
                "稍微动动手掌，大把星点到手", "魔法棒一挥，星星落了一地",
                "工厂里生产的据说是成吨的星点", "据说这个国度就是为了摘星星而生的",
                "大炮好像可以打碎星星的样子", "人们都跑月亮上抢星点去了 ",
                "直接降落到星星上去挖~", "每个星星都有我的踪迹 ",
                "所有位面的星星都在这里了"};
		build_cur_level = new long[10];
		build_sec_yield = new long[10];
		build_total_yield = new long[10];
		build_multiple = new long[] {1,1,1,1,1,1,1,1,1,1};
		build_updata_cost = new long[]{5, 10, 50, 250, 2000, 10000,
				100000, 1000000, 100000000, 1000000000};
		build_next_add = new long[]{1, 5, 10, 50, 100, 300,
				500, 5000, 10000, 100000};
	}
	
	public static GameData GetData()
	{
		if (null == gd)
		{
			gd = new GameData();
		}
		return gd;
	}
	public static boolean saveData(FileOutputStream fileout)
	{
		try {
			ObjectOutputStream out = new ObjectOutputStream(fileout);
			out.writeObject(gd);
			out.close();
			fileout.close();
			Log.e("game", "save success");
			return true;
		} catch (Exception e) {
			Log.e("game", "save fail");
			return false;
		}
	}
	public static boolean readData(FileInputStream filein)
	{
		try{
			ObjectInputStream in = new ObjectInputStream(filein);
			gd = (GameData) in.readObject();
			in.close();
			filein.close();
			Log.e("game", "read success");
			return true;
		} catch (Exception e){
			Log.e("game", "read fail");
			return false;
		}
	}

	public void add_star() //增加一秒产量
	{
		star += yield_sec * yield_multiple;
		star_history_total += yield_sec * yield_multiple;
		if (star > star_history_max)
			star_history_max = star;
		for (int i = 0; i < build_total_yield.length; ++i)
		{
			build_total_yield[i] += build_sec_yield[i] * build_multiple[i];
		}
	}
	public void add_click()
	{
		star += click_yield;
		click_total_yield += click_yield;
		++click_num;
	}
	public String get_game_day()
	{
		return StrPreOper(String.valueOf(game_day));
	}
	public String get_game_hour()
	{
		return StrPreOper(String.valueOf(game_hour));
	}
	public String get_star()
	{
		return StrPreOper(String.valueOf(star));
	}
	public String get_star_sec()
	{
		return StrPreOper(String.valueOf(yield_sec));
	}
	public String get_star_history_total()
	{
		return StrPreOper(String.valueOf(star_history_total));
	}
	public String get_star_history_max()
	{
		return StrPreOper(String.valueOf(star_history_max));
	}
	public String get_updata_num()
	{
		return StrPreOper(String.valueOf(updata_num));
	}
	public String get_click_num()
	{
		return StrPreOper(String.valueOf(click_num));
	}
	public String get_click_yield()
	{
		return StrPreOper(String.valueOf(click_yield));
	}
	public String get_click_total()
	{
		return StrPreOper(String.valueOf(click_total_yield));
	}
	public String get_build_name(building b)
	{
		return build_name[b.ordinal()];
	}
	public String get_build_level(building b)
	{
		return String.valueOf(build_cur_level[b.ordinal()]);
	}
	public String get_build_yield_sec(building b)
	{
		return StrPreOper(String.valueOf(build_sec_yield[b.ordinal()]));
	}
	public String get_build_yield_total(building b)
	{
		return StrPreOper(String.valueOf(build_total_yield[b.ordinal()]));
	}
	public long get_build_multiple(building b)
	{
		return build_multiple[b.ordinal()];
	}
	public String get_build_updata_cost(building b)
	{
		return StrPreOper(String.valueOf(build_updata_cost[b.ordinal()]));
	}
	public String get_build_next_add(building b)
	{
		return StrPreOper(String.valueOf(build_next_add[b.ordinal()]));
	}
	public String get_build_info(building b)
	{
		return build_info[b.ordinal()];
	}
	public boolean build_can_updata(building b)
	{
		return star >= build_updata_cost[b.ordinal()];
	}
	public String StrPreOper(String str)
    {
        int loc = 0, len = str.length();
        char[] re = new char[str.length() + str.length() / 3 + 1];
        char[] source = new char[len + len / 4 + 1];
        str.getChars(0, len, source, 0);

        boolean flags = false;
        if (len > 12)
        {
            len -= 8;
            flags = true;
        }
        for (int i = 0; i < len; )
        {
            re[loc++] = source[i++];
            if (len - i == 8)
            {
                re[loc++] = '亿';
                while (len - i  > 4 && source[i] == '0')
                    ++i;
                if (len - i == 4)
                {
                    while (len - i > 0 && source[i] == '0')
                        ++i;
                    while (i++ < len)
                        re[loc++] = source[i - 1];
                }
            }
            else if (len - i == 4)
            {
                re[loc++] = '万';
                while (len - i > 0 && source[i] == '0')
                    ++i;
                while (i < len)
                    re[loc++] = source[i++];
            }
        }
        if (flags)
            re[loc++] = '亿';
        return new String(re, 0, loc);
    }
	public void build_updata(building b)
	{
		if (build_can_updata(b))
		{
			star -= build_updata_cost[b.ordinal()];
			build_cur_level[b.ordinal()]++;
			
			if (building.MZ == b)
			{
				click_yield += build_next_add[b.ordinal()];
			}
			
			yield_sec += build_next_add[b.ordinal()];
			build_sec_yield[b.ordinal()] += build_next_add[b.ordinal()];
			//next level data 
			build_updata_cost[b.ordinal()] *= 1.1;
			//build_next_add[b.ordinal()] *= 1.2;
			
			++updata_num;
		}
	}
	public static long valueOf(String str)
	{
		if (str.equals("CUR_STAR"))
			return gd.star;
		else if (str.equals("TOTAL_STAR"))
			return gd.star_history_total;
		else if (str.equals("SEC_YIELD"))
			return gd.yield_sec;
		else if (str.equals("UPDATA_TIMES"))
			return gd.updata_num;
		else if (str.equals("CLICK_TIMES"))
			return gd.click_num;
		else if (str.equals("CLICK_YIELD"))
			return gd.click_yield;
		else if (str.equals("CLICK_TOTALYIELD"))
			return gd.click_total_yield;
		else if (str.equals("GAME_DAY"))
			return gd.game_day;
		else if (str.equals("GAME_HOUR"))
			return gd.game_hour;
		else if (-1 != "MZ, XXDF, MFW, XXMGC, ZXGD, XKDP,MOON, XKZM, SKSD, WMYT".indexOf(str))
			return gd.build_cur_level[building.valueOf(str).ordinal()];
		
		return 0;
	}
}
