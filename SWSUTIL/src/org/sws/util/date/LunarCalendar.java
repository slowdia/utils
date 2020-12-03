package org.sws.util.date;

import com.ibm.icu.util.ChineseCalendar;

public class LunarCalendar extends ChineseCalendar {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static LunarCalendar cal = null;
	
	public static LunarCalendar getInstance() {
		if(cal == null) {
			cal = new LunarCalendar();
		}
		return cal;
	}
	
	/**
	 * 연도 셋팅 - 입력된 연도 + 2637 한 값을 입력
	 * @param year
	 */
	public void setYear(int year) {
		cal.set(LunarCalendar.EXTENDED_YEAR, year + 2637);
	}
	
	/**
	 * 연도
	 * @return
	 */
	public int getYear() {
		return cal.get(LunarCalendar.EXTENDED_YEAR) - 2637;
	}
	
	/**
	 * 입력된 월에 -1 해서 셋팅
	 * @return
	 */
	public void setMonth(int month) {
		cal.set(LunarCalendar.MONTH, month - 1);
	}
	
	/**
	 * 연의 월  1~12 월  (+1 된 결과 값)
	 * @return
	 */
	public int getMonth() {
		return cal.get(LunarCalendar.MONTH) + 1;
	}
	
	/**
	 * 월의 해당 일자 셋팅
	 * @return
	 */
	public void setDay(int day) {
		cal.set(LunarCalendar.DAY_OF_MONTH, day);
	}
	
	/**
	 * 해당월의 일자
	 * @return
	 */
	public int getDay() {
		return cal.get(LunarCalendar.DAY_OF_MONTH);
	}
	
}
