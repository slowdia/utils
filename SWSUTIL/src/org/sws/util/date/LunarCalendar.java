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
	 * ���� ���� - �Էµ� ���� + 2637 �� ���� �Է�
	 * @param year
	 */
	public void setYear(int year) {
		cal.set(LunarCalendar.EXTENDED_YEAR, year + 2637);
	}
	
	/**
	 * ����
	 * @return
	 */
	public int getYear() {
		return cal.get(LunarCalendar.EXTENDED_YEAR) - 2637;
	}
	
	/**
	 * �Էµ� ���� -1 �ؼ� ����
	 * @return
	 */
	public void setMonth(int month) {
		cal.set(LunarCalendar.MONTH, month - 1);
	}
	
	/**
	 * ���� ��  1~12 ��  (+1 �� ��� ��)
	 * @return
	 */
	public int getMonth() {
		return cal.get(LunarCalendar.MONTH) + 1;
	}
	
	/**
	 * ���� �ش� ���� ����
	 * @return
	 */
	public void setDay(int day) {
		cal.set(LunarCalendar.DAY_OF_MONTH, day);
	}
	
	/**
	 * �ش���� ����
	 * @return
	 */
	public int getDay() {
		return cal.get(LunarCalendar.DAY_OF_MONTH);
	}
	
}
