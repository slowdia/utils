package org.sws.util.date;

import java.util.Date;

import org.sws.util.entity.Entity;

import com.ibm.icu.util.Calendar;

public class Lunar {
	
	/**
	 * 양력을 음력으로 바꾼다
	 * @param date
	 * @return day : 연-월-일, leap : 윤달여부
	 */
	public static Entity getDate(Date date) {
		LunarCalendar cal = LunarCalendar.getInstance();
		cal.setTimeInMillis(date.getTime());
		Entity entity = new Entity();
		entity.put("day", (cal.getYear()
					 + (cal.getMonth() < 10 ? "-0": "-") + cal.getMonth()
					 + (cal.getDay()   < 10 ? "-0": "-") + cal.getDay())
				);
		entity.put("leap", cal.get(LunarCalendar.IS_LEAP_MONTH) == 1 ? true : false);
		
		return entity;
	}
	
	/**
	 * 양력을 음력으로 바꾼다
	 * @param date
	 * @return day : 연-월-일, leap : 윤달여부
	 */
	public static Entity getDate(Calendar calendar) {
		LunarCalendar cal = LunarCalendar.getInstance();
		cal.setTimeInMillis(calendar.getTimeInMillis());
		Entity entity = new Entity();
		entity.put("day", (cal.getYear()
					 + (cal.getMonth() < 10 ? "-0": "-") + cal.getMonth()
					 + (cal.getDay()   < 10 ? "-0": "-") + cal.getDay())
				);
		entity.put("leap", cal.get(LunarCalendar.IS_LEAP_MONTH) == 1 ? true : false);
		
		return entity;
	}
	
	public static void main(String args[]) {
		Calendar cal = Calendar.getInstance();
		cal.set(2019, 1, 5); // 2019-02-05 >> 2019-01-01
		Entity entity = Lunar.getDate(cal);
		System.out.println(entity.get("day") + (entity.getInt("leap")==1?" 윤달":""));
		entity = Lunar.getDate(new Date());
		System.out.println(entity.get("day") + (entity.getInt("leap")==1?" 윤달":""));
		entity = Lunar.getDate(Calendar.getInstance());
		System.out.println(entity.get("day") + (entity.getInt("leap")==1?" 윤달":""));
	}
}
