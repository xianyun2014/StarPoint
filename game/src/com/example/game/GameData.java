package com.example.game;

public class GameData {
	private static GameData sd = null; //single 

	public enum building{MZ, XXDF, MFW, XXMGC, ZXGD, XKDP, MOON, XKZM, SKSD, WMYT}; //building's name, cann't change this sequence
	
	private long star; //star point number
	private long yield_sec;
	private long time_offset;  //毫秒修正辅助
	private long yield_click;
	private String[] build_name;
	private String[] build_info;
	private long [] build_cur_level;
	private long [] build_sec_yield; //different building's yield every second
	private long [] build_total_yield; //total yield this building
	private long [] build_multiple;
	private long [] build_updata_cost;
	private long [] build_next_add;
	
	private GameData(){ } //can't default create
	
	public static GameData CreateData()
	{
		if (null == sd)
		{
			sd = new GameData();
			sd.star = 0L;
			sd.yield_sec = 1L;
			sd.time_offset = 0;
			sd.yield_click = 1L;
			sd.build_name = new String[]{"星空指", "吸星大法", "星星魔法屋", 
					"星星梦工场", "摘星国度", "星空大炮", 
					"月亮跃迁器", "星空之门", "时空隧道", "位面源头"};
			sd.build_info = new String[]{"勤劳的人儿总是不缺星点，手指头越多点的越快哦~", 
					"稍微动动手掌，大把星点到手", "魔法棒一挥，星星落了一地", 
					"工厂里生产的据说是成吨的星点", "据说这个国度就是为了摘星星而生的", 
					"大炮好像可以打碎星星的样子", "人们都跑月亮上抢星点去了 ", 
					"直接降落到星星上去挖~", "每个星星都有我的踪迹 ", 
					"所有位面的星星都在这里了"};
			sd.build_cur_level = new long[10];
			sd.build_sec_yield = new long[10];
			sd.build_total_yield = new long[10];
			sd.build_multiple = new long[10];
			sd.build_updata_cost = new long[]{5, 10, 50, 250, 2000, 10000,
					100000, 1000000, 100000000, 1000000000};
			sd.build_next_add = new long[]{1, 5, 10, 50, 100, 300,
					500, 5000, 10000, 100000};
		}
		return sd;
	}
	public String get_Star_Str()
	{
		return String.valueOf(star);
	}
	public void add_second(int m) //每m毫秒增加的产量
	{
		star += yield_sec / (1000 / m);
		time_offset += yield_sec / (1000 / m);
	}
	public void add_onesecond()
	{
		star += yield_sec - time_offset;
		for (int i = 0; i < build_total_yield.length; ++i)
		{
			build_total_yield[i] += build_sec_yield[i];
		}
		time_offset = 0;
	}
	public void add_click()
	{
		star += yield_click;
	}
	public String get_build_name(building b)
	{
		return build_name[b.ordinal()];
	}
	public String get_build_level(building b)
	{
		return String.valueOf(build_cur_level[b.ordinal()]);
	}
	public String get_yield_sec(building b)
	{
		return String.valueOf(build_sec_yield[b.ordinal()]);
	}
	public String get_yield_total(building b)
	{
		return String.valueOf(build_total_yield[b.ordinal()]);
	}
	public long get_build_multiple(building b)
	{
		return build_multiple[b.ordinal()];
	}
	public String get_build_updata_cost(building b)
	{
		return String.valueOf(build_updata_cost[b.ordinal()]);
	}
	public String get_build_next_add(building b)
	{
		return String.valueOf(build_next_add[b.ordinal()]);
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
		if (star >= build_updata_cost[b.ordinal()])
		{
			star -= build_updata_cost[b.ordinal()];
			build_cur_level[b.ordinal()]++;
			
			if (building.MZ == b)
			{
				yield_click += build_next_add[b.ordinal()];
			}
			
			yield_sec += build_next_add[b.ordinal()];
			build_sec_yield[b.ordinal()] += build_next_add[b.ordinal()];
			//next level data 
			build_updata_cost[b.ordinal()] *= 1.1;
			//build_next_add[b.ordinal()] *= 1.2;
			
		}
	}
}
