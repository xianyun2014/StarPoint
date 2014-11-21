package com.example.game;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import android.util.Log;

public class GameData implements java.io.Serializable{
	private static GameData gd = null; //single 

	public enum building{MZ, XXDF, MFW, XXMGC, ZXGD, XKDP, MOON, XKZM, SKSD, WMYT}; //building's name, cann't change this sequence
	private long game_day;
	private long game_hour;
	
	private long star; //��ǰ�ǵ�����
	private long star_history_max; //��ʷ����ǵ���
	private long star_history_total; //��ʷ�ܲ�
	private long yield_sec; //��ǰ���
	private long yield_multiple; //�ܲ�������
	private long time_offset;  //������������
	private long click_num; //�ܵ������
	private long click_yield;  //���ε������
	private long click_total_yield; //����ܲ���
	private long updata_num; //����������
	
	private String[] build_name; //��������
	private String[] build_info; //����������Ϣ
	private long [] build_cur_level; //������ǰ�ȼ�
	private long [] build_sec_yield; //�������
	private long [] build_total_yield; //������ʷ�ܲ�
	private long [] build_multiple; //������������
	private long [] build_updata_cost; //������ǰ��������
	private long [] build_next_add; //�����¼����Ӳ���
	
	private GameData(){//private ,can't default create 
		game_day = 0L;
		game_hour = 0L;
		
		star = 0L;
		star_history_max = 0L;
		star_history_total = 0L;
		time_offset = 0;
		yield_sec = 1L;
		yield_multiple = 1L;
		click_num = 0L;
		click_yield = 1L;
		click_total_yield = 0L;
		updata_num = 0L;
		
		build_name = new String[]{"�ǿ�ָ", "���Ǵ�", "����ħ����", 
				"�����ι���", "ժ�ǹ���", "�ǿմ���", 
				"����ԾǨ��", "�ǿ�֮��", "ʱ�����", "λ��Դͷ"};
		build_info = new String[]{"���͵��˶����ǲ�ȱ�ǵ㣬��ָͷԽ����Խ��Ŷ~", 
				"��΢�������ƣ�����ǵ㵽��", "ħ����һ�ӣ���������һ��", 
				"�����������ľ�˵�ǳɶֵ��ǵ�", "��˵������Ⱦ���Ϊ��ժ���Ƕ�����", 
				"���ں�����Դ������ǵ�����", "���Ƕ������������ǵ�ȥ�� ", 
				"ֱ�ӽ��䵽������ȥ��~", "ÿ�����Ƕ����ҵ��ټ� ", 
				"����λ������Ƕ���������"};
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
	public void add_star_for_fps(int m) //ÿm�������ӵĲ������˷������������ǵ㣬ֻΪ��ӦˢƵ���ʣ�
	{
		time_offset += yield_sec / (1000 / m) * yield_multiple;
	}
	public void add_star() //ʵ�ʵ�����һ������ķ���
	{
		star += yield_sec * yield_multiple;
		star_history_total += yield_sec * yield_multiple;
		if (star > star_history_max)
			star_history_max = star;
		for (int i = 0; i < build_total_yield.length; ++i)
		{
			build_total_yield[i] += build_sec_yield[i] * build_multiple[i];
		}
		 
		time_offset = 0;
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
		return StrPreOper(String.valueOf(star + time_offset));
	}
	public String get_star_sec()
	{
		return StrPreOper(String.valueOf(yield_sec));
	}
	public String get_star_history_total()
	{
		return StrPreOper(String.valueOf(star_history_total + time_offset));
	}
	public String get_star_history_max()
	{
		return StrPreOper(String.valueOf(star_history_max + time_offset));
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
                re[loc++] = '��';
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
                re[loc++] = '��';
                while (len - i > 0 && source[i] == '0')
                    ++i;
                while (i < len)
                    re[loc++] = source[i++];
            }
        }
        if (flags)
            re[loc++] = '��';
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
}
