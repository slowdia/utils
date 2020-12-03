package org.sws.util.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * ��¥ ���� ��ƿ
 * <br>-----------------------------------------
 * <br>���� ����  ->>  ���
 * <br>-----------------------------------------
 * <br>"yyyy.MM.dd G 'at' hh:mm:ss z"	->>  1996.07. 10 AD at 15:08:56 PDT
 * <br>"EEE, MMM d, ''yy"				->>  Wed, July 10, '96
 * <br>"h:mm a"						  ->>  12:08 PM
 * <br>"hh 'o''clock' a, zzzz"		   ->>  12 o'clock PM, Pacific Daylight Time
 * <br>"K:mm a, z"					   ->>  0:00 PM, PST
 * <br>"yyyyy.MMMMM.dd GGG hh:mm aaa"	->>  1996. July. 10 AD 12:08 PM
 * @author ago
 *
 */
public class DateUtil {
	
	/**
	 * ���� ������ ������ �����Ѵ�.
	 * @return
	 */
	public static String getYear()
	{
		return getYear(Locale.KOREA);
	}
	
	/**
	 * ���� ������ ������ �����Ѵ�.
	 * @param locale
	 * @return
	 */
	public static String getYear(Locale locale)
	{
		SimpleDateFormat sfmt = new SimpleDateFormat("yyyy", locale);
		return sfmt.format(new Date());
	}
	
	/**
	 * ���� ������ ���� �����Ѵ�.
	 * @return
	 */
	public static String getMonth()
	{
		return getMonth(Locale.KOREA);
	}
	
	/**
	 * ���� ������ ���� �����Ѵ�.
	 * @param locale
	 * @return
	 */
	public static String getMonth(Locale locale)
	{
		SimpleDateFormat sfmt = new SimpleDateFormat("MM", locale);
		return sfmt.format(new Date());
	}
	
	/**
	 * ���� ������ �ϸ� �����Ѵ�.
	 * @return
	 */
	public static String getDay()
	{
		return getDay(Locale.KOREA);
	}
	
	/**
	 * ���� ������ �ϸ� �����Ѵ�.
	 * @param locale
	 * @return
	 */
	public static String getDay(Locale locale)
	{
		SimpleDateFormat sfmt = new SimpleDateFormat("dd", locale);
		return sfmt.format(new Date());
	}
	
	/**
	 * ���� �ð��� �ø� �����Ѵ�.
	 * @return
	 */
	public static String getHour()
	{
		return getHour(Locale.KOREA);
	}
	
	/**
	 * ���� �ð��� �ø� �����Ѵ�.
	 * @param locale
	 * @return
	 */
	public static String getHour(Locale locale)
	{
		SimpleDateFormat sfmt = new SimpleDateFormat("HH", locale);
		return sfmt.format(new Date());
	}
	
	/**
	 * ���� �ð��� �ø� �����Ѵ�.
	 * @return
	 */
	public static String getMinute()
	{
		return getMinute(Locale.KOREA);
	}
	
	/**
	 * ���� �ð��� ���� �����Ѵ�.
	 * @param locale
	 * @return
	 */
	public static String getMinute(Locale locale)
	{
		SimpleDateFormat sfmt = new SimpleDateFormat("mm", locale);
		return sfmt.format(new Date());
	}
	
	/**
	 * ���� �ð��� ���� �����Ѵ�.
	 * @return
	 */
	public static String getSecond()
	{
		return getSecond(Locale.KOREA);
	}
	
	/**
	 * ���� �ð��� ���� �����Ѵ�.
	 * @param locale
	 * @return
	 */
	public static String getSecond(Locale locale)
	{
		SimpleDateFormat sfmt = new SimpleDateFormat("ss", locale);
		return sfmt.format(new Date());
	}
	
	/**
	 * ���� �ð��� �и����� �����Ѵ�.
	 * @return
	 */
	public static String getMilliSecond()
	{
		return getMilliSecond(Locale.KOREA);
	}
	
	/**
	 * ���� �ð��� �и����� �����Ѵ�.
	 * @param locale
	 * @return
	 */
	public static String getMilliSecond(Locale locale)
	{
		SimpleDateFormat sfmt = new SimpleDateFormat("SSS", locale);
		return sfmt.format(new Date());
	}
	
	/**
	 * ���� ���� ������ ��¥�� ���Ѵ�.
	 * @return
	 */
	public static int getLastDayOfMonth()
	{
		GregorianCalendar date = new GregorianCalendar();
		return date.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * ������ ��/�� �� ������ ��¥�� ���Ѵ�.
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getLastDayOfMonth(int year, int month)
	{
		GregorianCalendar date = new GregorianCalendar(year, month - 1, 1);
		return date.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * ���� ��¥�� ���˿� ���߾� ���ڿ��� �����Ѵ�.
	 * @param format
	 * @return
	 */
	public static String getDate(String format)
	{
		return getDate(format, Locale.KOREA);
	}
	
	/**
	 * ���� ��¥�� ���˿� ���߾� ���ڿ��� �����Ѵ�.
	 * @param format
	 * @param locale
	 * @return
	 */
	public static String getDate(String format, Locale locale)
	{
		SimpleDateFormat sfmt = new SimpleDateFormat(format, locale);
		return sfmt.format(new Date());
	}
	
	/**
	 * java.util.Date ������ java.sql.Date �������� ��ȯ�Ͽ� ����
	 * @return
	 */
	public static java.sql.Date getSqlDate()
	{
		return new java.sql.Date(new Date().getTime());
	}
	
	/**
	 * java.util.Date ������ Calendar �������� ��ȯ�Ͽ� ����
	 * @param date java.sql.Date �� ��ȯ �� ��¥
	 * @return
	 */
	public static Calendar getCalendar(Date date) throws ParseException
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}
	
	/**
	 * java.util.Date ������ java.sql.Date �������� ��ȯ�Ͽ� ����
	 * @param date �� ��ȯ �� ��¥
	 * @return
	 */
	public static java.sql.Date getSqlDate(Date date)
	{
		return new java.sql.Date(date.getTime());
	}
	
	/**
	 * �Ķ���� ��¥�� ���˿� ���߾� ���ڿ��� �����Ѵ�.
	 * @param date
	 * @return
	 */
	public static String getDate(Date date) {
		SimpleDateFormat sfmt = new SimpleDateFormat("yyyy-MM-dd");
		return sfmt.format(date);
	}
	
	/**
	 * java.sql.Date ������ java.util.Date �������� ��ȯ�Ͽ� ����
	 * @param date java.sql.Date �� ��ȯ �� ��¥
	 * @return
	 */
	public static Date getDate(java.sql.Date date)
	{
		return new Date(date.getTime());
	}
	
	/**
	 * ���� ��¥�� ���˿� ���߾� ���ڿ��� �����Ѵ�.
	 * @param format
	 * @return
	 */
	public static String getDate(Date date, String format)
	{
		SimpleDateFormat sfmt = new SimpleDateFormat(format);
		return sfmt.format(date);
	}
	
	/**
	 * String ������ Calendar �������� ��ȯ�Ͽ� ����
	 * @param date �� ��ȯ �� ��¥
	 * @param format
	 * @return
	 * @throws ParseException 
	 */
	public static Calendar getCalendar(String date, String format) throws ParseException
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(getDate(date, format));
		return cal;
	}
	
	/**
	 * String ������ java.util.Date �������� ��ȯ�Ͽ� ����
	 * @param date �� ��ȯ �� ��¥
	 * @param format
	 * @return
	 * @throws ParseException 
	 */
	public static Date getDate(String date, String format) throws ParseException
	{
		SimpleDateFormat sfmt = new SimpleDateFormat(format);
		return sfmt.parse(date);
	}

	/**
	 * yyyy�� MM�� dd�� �������� �����Ͽ� �����Ѵ�.
	 * @param date
	 * @param format
	 * @return
	 */
	public static String getKoDate(String date, String format) throws ParseException
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(getDate(date, format));
		return cal.get(Calendar.YEAR) + "�� " + (cal.get(Calendar.MONTH) +1) + "�� " + cal.get(Calendar.DAY_OF_MONTH) + "��";
	}
	
	/**
	 * ���ó�¥�� ���� �Ⱓ ���� �Ǵ� ���� ��¥�� ���˿� �°� �����Ͽ� �����Ѵ�.
	 * @param format ��¥ ����
	 * @param field Calendar field
	 * @param period ���ų�¥�� ����, �̷� ��¥�� ���
	 * @return
	 */
	public static String getDate(String format, int field, int period)
	{
		return getDate(format, field, period, Locale.KOREA);
	}
	
	/**
	 * ���ó�¥�� ���� �Ⱓ ���� �Ǵ� ���� ��¥�� ���˿� �°� �����Ͽ� �����Ѵ�.
	 * @param format ��¥ ����
	 * @param field Calendar field
	 * @param period ���ų�¥�� ����, �̷� ��¥�� ���
	 * @return
	 */
	public static String getDate(String format, int field, int period, Locale locale)
	{
		SimpleDateFormat sfmt = new SimpleDateFormat(format, locale);
		Calendar today = Calendar.getInstance();
		today.add(field, period);
		return sfmt.format(today.getTime());
	}
	
	/**
	 * ���� ��¥���� ���̸� ���Ѵ�.<br>
	 * ��� �� ���� ������ �����Ѵ�.
	 * @param date ���� ��¥
	 * @param format ���� ��¥�� ����
	 * @param field ���̸� ���� ���� - Calendar field
	 * @return ���� ���ڴ� -����, �̷����ڴ� +����
	 * @throws ParseException 
	 */
	public static long getPeriod(String date, String format, int field) throws ParseException
	{
		return getPeriod(getDate(date, format), field);
	}
	
	/**
	 * ���� ��¥���� ���̸� ���Ѵ�.<br>
	 * ��� �� ���� ������ �����Ѵ�.
	 * @param date ���� ��¥
	 * @param field ���̸� ���� ���� - Calendar field
	 * @return ���� ���ڴ� -����, �̷����ڴ� +����
	 * @throws ParseException 
	 */
	public static long getPeriod(Date date, int field) throws ParseException
	{
		String date1 = getDate(date, "yyyyMMddHHmmss");
		String date2 = DateUtil.getDate("yyyyMMddHHmmss");
		
		int nYear1 = Integer.parseInt(date1.substring(0,4));
		int nMonth1 = Integer.parseInt(date1.substring(4,6));
		int nDate1 = Integer.parseInt(date1.substring(6,8));
		int nHour1 = Integer.parseInt(date1.substring(8,10));
		int nMin1 = Integer.parseInt(date1.substring(10,12));
		int nSec1 = Integer.parseInt(date1.substring(12,14));
		
		int nYear2 = Integer.parseInt(date2.substring(0,4));
		int nMonth2 = Integer.parseInt(date2.substring(4,6));
		int nDate2 = Integer.parseInt(date2.substring(6,8));
		int nHour2 = Integer.parseInt(date1.substring(8,10));
		int nMin2 = Integer.parseInt(date1.substring(10,12));
		int nSec2 = Integer.parseInt(date1.substring(12,14));
		
		if(field == Calendar.YEAR) {
			return nYear1 - nYear2;
		}else if(field == Calendar.MONTH) {
			return ((nYear1 - nYear2) * 12) + nMonth1 - nMonth2;
		}else {
			int period = 0;
			if((nYear1*10000) + (nMonth1*100) + nDate1 < (nYear2*10000) + (nMonth2*100) + nDate2) {
				period = getLastDayOfMonth(nYear1, nMonth1) - nDate1 + nDate2;
				int year = nYear1, month = nMonth1;
				do{
					month ++;
					if(month > 12) { year ++; month = 1; }
					period += getLastDayOfMonth(year, month);
				}while(year*100 + month < (nYear2*100) + nMonth2);
				period = -period;
			}else {
				period = getLastDayOfMonth(nYear2, nMonth2) - nDate2 + nDate1;
				int year = nYear2*100, month = nMonth2;
				do{
					month ++;
					if(month > 12) { year ++; month = 1; }
					period += getLastDayOfMonth(year, month);
				}while(year + month < (nYear1*100) + nMonth1);
			}
			switch (field) {
				case Calendar.DAY_OF_MONTH : 
				case Calendar.DAY_OF_YEAR : 
				case Calendar.DAY_OF_WEEK : 
				case Calendar.DAY_OF_WEEK_IN_MONTH : 
					return period;
				case Calendar.HOUR : 
				case Calendar.HOUR_OF_DAY : 
					return (period * 24) + nHour1 - nHour2;
				case Calendar.MINUTE : 
					return (((period * 24) + nHour1 - nHour2) * 60) + nMin1 - nMin2;
				case Calendar.SECOND : 
					return (((((period * 24) + nHour1 - nHour2) * 60) + nMin1 - nMin2) * 60) + nSec1 - nSec2;
				default : 
					return 0;
			}
		}
	}
	
}
