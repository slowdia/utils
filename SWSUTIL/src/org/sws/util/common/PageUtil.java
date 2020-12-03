package org.sws.util.common;

import org.sws.util.entity.Entity;

public class PageUtil {

	/** 게시물의 총 개수 */
	private static int totalCount = 0;
	/** 현재 페이지 */
	private static int curPage = 1;
	/** 페이지당 보여줄 게시물 수 */
	private static int npp = 10;
	
	/** 가져올 시작 번호 */
	private static int startNum = 0;
	/** 가져올 끝 번호 */
	private static int endNum = 0;
	/** 가져올 개수 */
	private static int listCount = 0;
	/** 제외 시킬 개수 */
	private static int offset = 0;
	
	/**
	 * 가져올 리스트의 시작 번호를 반환, ORACLE에서 사용
	 * @return
	 */
	public static int getStartNum() {
		return startNum;
	}

	/**
	 * 가져올 리스트의 끝 번호를 반환, ORACLE, MS-SQL에서 사용
	 * @return
	 */
	public static int getEndNum() {
		return endNum;
	}

	/**
	 * 가져올 리스트 개수, MS-SQL, MYSQL에서 사용
	 * @return
	 */
	public static int getListCount() {
		return listCount;
	}

	/**
	 * 제외할 리스트 개수, MYSQL에서 사용
	 * @return
	 */
	public static int getOffset() {
		return offset;
	}
	
	/**
	 * 조회 카운트 관련 정보를 엔티티에 담아서 리턴한다.
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
	    int lastPage = 0 ; // 전체 페이지수
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
