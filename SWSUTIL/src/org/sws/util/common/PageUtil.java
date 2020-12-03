package org.sws.util.common;

import org.sws.util.entity.Entity;

public class PageUtil {

	/** �Խù��� �� ���� */
	private static int totalCount = 0;
	/** ���� ������ */
	private static int curPage = 1;
	/** �������� ������ �Խù� �� */
	private static int npp = 10;
	
	/** ������ ���� ��ȣ */
	private static int startNum = 0;
	/** ������ �� ��ȣ */
	private static int endNum = 0;
	/** ������ ���� */
	private static int listCount = 0;
	/** ���� ��ų ���� */
	private static int offset = 0;
	
	/**
	 * ������ ����Ʈ�� ���� ��ȣ�� ��ȯ, ORACLE���� ���
	 * @return
	 */
	public static int getStartNum() {
		return startNum;
	}

	/**
	 * ������ ����Ʈ�� �� ��ȣ�� ��ȯ, ORACLE, MS-SQL���� ���
	 * @return
	 */
	public static int getEndNum() {
		return endNum;
	}

	/**
	 * ������ ����Ʈ ����, MS-SQL, MYSQL���� ���
	 * @return
	 */
	public static int getListCount() {
		return listCount;
	}

	/**
	 * ������ ����Ʈ ����, MYSQL���� ���
	 * @return
	 */
	public static int getOffset() {
		return offset;
	}
	
	/**
	 * ��ȸ ī��Ʈ ���� ������ ��ƼƼ�� ��Ƽ� �����Ѵ�.
	 * @return
	 */
	public static Entity getEntity()
	{
		Entity entity = new Entity();
		entity.setValue("startNum", startNum);
		entity.setValue("endNum", endNum);
		entity.setValue("listCount", listCount);
		entity.setValue("offset", offset);
		return entity;
	}

	public static void setPage(int totalCount)
	{
		PageUtil.totalCount = totalCount;
		
		if(totalCount < 0) totalCount = 0;
		
		calculate();
	}

	public static void setPage(int totalCount, int curPage)
	{
		PageUtil.totalCount = totalCount;
		PageUtil.curPage = curPage;
		
		if(totalCount < 0) totalCount = 0;
		if(curPage < 1) curPage = 1;
		
		calculate();
	}
	
	public static void setPage(int totalCount, int curPage, int npp)
	{
		PageUtil.totalCount = totalCount;
		PageUtil.curPage = curPage;
		PageUtil.npp = npp;
		
		if(totalCount < 0) totalCount = 0;
		if(curPage < 1) curPage = 1;
		if(npp < 1) npp = 10;
		
		calculate();
	}
	
	private static void calculate()
	{
	    int lastPage = 0 ; // ��ü ��������
	    lastPage = totalCount / npp;
	    if((totalCount % npp) > 0) {
	        lastPage++;
	    }
	    if(curPage > lastPage) {
	    	curPage = lastPage;
	    }
	    
	    startNum = ((curPage * npp) - npp) + 1;
	    endNum   = curPage * npp;
	    if(endNum > totalCount) {
	    	endNum = totalCount;
	    }
	    
	    listCount = npp;
	    if(curPage == lastPage && totalCount % npp > 0){
	    	listCount = totalCount % npp;
	    }
	    
	    offset = startNum - 1;
	}
	
}
