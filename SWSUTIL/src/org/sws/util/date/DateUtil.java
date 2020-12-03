package org.sws.util.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * 날짜 포맷 유틸
 * <br>-----------------------------------------
 * <br>포맷 패턴  ->>  결과
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
	 * 오늘 날싸의 연도를 리턴한다.
	 * @return
	 */
	public static String getYear()
	{
		return getYear(Locale.KOREA);
	}
	
	/**
	 * 오늘 날싸의 연도를 리턴한다.
	 * @param locale
	 * @return
	 */
	public static String getYear(Locale locale)
	{
		SimpleDateFormat sfmt = new SimpleDateFormat("yyyy", locale);
		return sfmt.format(new Date());
	}
	
	/**
	 * 오늘 날싸의 월를 리턴한다.
	 * @return
	 */
	public static String getMonth()
	{
		return getMonth(Locale.KOREA);
	}
	
	/**
	 * 오늘 날싸의 월를 리턴한다.
	 * @param locale
	 * @return
	 */
	public static String getMonth(Locale locale)
	{
		SimpleDateFormat sfmt = new SimpleDateFormat("MM", locale);
		return sfmt.format(new Date());
	}
	
	/**
	 * 오늘 날싸의 일를 리턴한다.
	 * @return
	 */
	public static String getDay()
	{
		return getDay(Locale.KOREA);
	}
	
	/**
	 * 오늘 날싸의 일를 리턴한다.
	 * @param locale
	 * @return
	 */
	public static String getDay(Locale locale)
	{
		SimpleDateFormat sfmt = new SimpleDateFormat("dd", locale);
		return sfmt.format(new Date());
	}
	
	/**
	 * 현재 시간의 시를 리턴한다.
	 * @return
	 */
	public static String getHour()
	{
		return getHour(Locale.KOREA);
	}
	
	/**
	 * 현재 시간의 시를 리턴한다.
	 * @param locale
	 * @return
	 */
	public static String getHour(Locale locale)
	{
		SimpleDateFormat sfmt = new SimpleDateFormat("HH", locale);
		return sfmt.format(new Date());
	}
	
	/**
	 * 현재 시간의 시를 리턴한다.
	 * @return
	 */
	public static String getMinute()
	{
		return getMinute(Locale.KOREA);
	}
	
	/**
	 * 현재 시간의 분을 리턴한다.
	 * @param locale
	 * @return
	 */
	public static String getMinute(Locale locale)
	{
		SimpleDateFormat sfmt = new SimpleDateFormat("mm", locale);
		return sfmt.format(new Date());
	}
	
	/**
	 * 현재 시간의 초을 리턴한다.
	 * @return
	 */
	public static String getSecond()
	{
		return getSecond(Locale.KOREA);
	}
	
	/**
	 * 현재 시간의 초을 리턴한다.
	 * @param locale
	 * @return
	 */
	public static String getSecond(Locale locale)
	{
		SimpleDateFormat sfmt = new SimpleDateFormat("ss", locale);
		return sfmt.format(new Date());
	}
	
	/**
	 * 현재 시간의 밀리초을 리턴한다.
	 * @return
	 */
	public static String getMilliSecond()
	{
		return getMilliSecond(Locale.KOREA);
	}
	
	/**
	 * 현재 시간의 밀리초을 리턴한다.
	 * @param locale
	 * @return
	 */
	public static String getMilliSecond(Locale locale)
	{
		SimpleDateFormat sfmt = new SimpleDateFormat("SSS", locale);
		return sfmt.format(new Date());
	}
	
	/**
	 * 현재 월의 마지막 날짜를 구한다.
	 * @return
	 */
	public static int getLastDayOfMonth()
	{
		GregorianCalendar date = new GregorianCalendar();
		return date.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 지정된 연/월 의 마지막 날짜를 구한다.
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
	 * 오늘 날짜를 포맷에 맞추어 문자열로 리턴한다.
	 * @param format
	 * @return
	 */
	public static String getDate(String format)
	{
		return getDate(format, Locale.KOREA);
	}
	
	/**
	 * 오늘 날짜를 포맷에 맞추어 문자열로 리턴한다.
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
	 * java.util.Date 형식을 java.sql.Date 형식으로 변환하여 리턴
	 * @return
	 */
	public static java.sql.Date getSqlDate()
	{
		return new java.sql.Date(new Date().getTime());
	}
	
	/**
	 * java.util.Date 형식을 Calendar 형식으로 변환하여 리턴
	 * @param date java.sql.Date 형 변환 할 날짜
	 * @return
	 */
	public static Calendar getCalendar(Date date) throws ParseException
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}
	
	/**
	 * java.util.Date 형식을 java.sql.Date 형식으로 변환하여 리턴
	 * @param date 형 변환 할 날짜
	 * @return
	 */
	public static java.sql.Date getSqlDate(Date date)
	{
		return new java.sql.Date(date.getTime());
	}
	
	/**
	 * 파라메터 날짜를 포맷에 맞추어 문자열로 리턴한다.
	 * @param date
	 * @return
	 */
	public static String getDate(Date date) {
		SimpleDateFormat sfmt = new SimpleDateFormat("yyyy-MM-dd");
		return sfmt.format(date);
	}
	
	/**
	 * java.sql.Date 형식을 java.util.Date 형식으로 변환하여 리턴
	 * @param date java.sql.Date 형 변환 할 날짜
	 * @return
	 */
	public static Date getDate(java.sql.Date date)
	{
		return new Date(date.getTime());
	}
	
	/**
	 * 오늘 날짜를 포맷에 맞추어 문자열로 리턴한다.
	 * @param format
	 * @return
	 */
	public static String getDate(Date date, String format)
	{
		SimpleDateFormat sfmt = new SimpleDateFormat(format);
		return sfmt.format(date);
	}
	
	/**
	 * String 형식을 Calendar 형식으로 변환하여 리턴
	 * @param date 형 변환 할 날짜
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
	 * String 형식을 java.util.Date 형식으로 변환하여 리턴
	 * @param date 형 변환 할 날짜
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
	 * yyyy년 MM월 dd일 형식으로 변경하여 리턴한다.
	 * @param date
	 * @param format
	 * @return
	 */
	public static String getKoDate(String date, String format) throws ParseException
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(getDate(date, format));
		return cal.get(Calendar.YEAR) + "년 " + (cal.get(Calendar.MONTH) +1) + "월 " + cal.get(Calendar.DAY_OF_MONTH) + "일";
	}
	
	/**
	 * 오늘날짜로 부터 기간 이전 또는 이후 날짜를 포맷에 맞게 변경하여 리턴한다.
	 * @param format 날짜 포맷
	 * @param field Calendar field
	 * @param period 과거날짜면 음수, 미래 날짜면 양수
	 * @return
	 */
	public static String getDate(String format, int field, int period)
	{
		return getDate(format, field, period, Locale.KOREA);
	}
	
	/**
	 * 오늘날짜로 부터 기간 이전 또는 이후 날짜를 포맷에 맞게 변경하여 리턴한다.
	 * @param format 날짜 포맷
	 * @param field Calendar field
	 * @param period 과거날짜면 음수, 미래 날짜면 양수
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
	 * 현재 날짜와의 차이를 구한다.<br>
	 * 계산 시 하위 단위는 무시한다.
	 * @param date 비교할 날짜
	 * @param format 비교할 날짜의 포맷
	 * @param field 차이를 구할 단위 - Calendar field
	 * @return 과거 일자는 -정수, 미래일자는 +정수
	 * @throws ParseException 
	 */
	public static long getPeriod(String date, String format, int field) throws ParseException
	{
		return getPeriod(getDate(date, format), field);
	}
	
	/**
	 * 현재 날짜와의 차이를 구한다.<br>
	 * 계산 시 하위 단위는 무시한다.
	 * @param date 비교할 날짜
	 * @param field 차이를 구할 단위 - Calendar field
	 * @return 과거 일자는 -정수, 미래일자는 +정수
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
