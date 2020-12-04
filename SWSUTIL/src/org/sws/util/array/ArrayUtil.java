package org.sws.util.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ArrayUtil {

	/**
	 * 순열
	 * @param prefix 초기값 : 빈 배열 new String[0]
	 * @param array 조합 할 원본 배열 String[]
	 * @param list 조합된 결과 리스트
	 */
	public static void Premutation(String[] prefix, String[] array, List<String[]> list){
		if(prefix != null && prefix.length > 0){
			list.add(prefix);
		}
		for(int i=0; i<array.length; i++){
			String[] newprefix = new String[prefix.length+1];
			System.arraycopy(prefix, 0, newprefix, 0, prefix.length);
			newprefix[newprefix.length-1] = array[i];
			String[] newArray = new String[array.length-1];
			System.arraycopy(array, 0, newArray, 0, i);
			System.arraycopy(array, i+1, newArray, i, newArray.length - i);
			Premutation(newprefix, newArray, list);
		}
	}
	
	public static void main(String[] args){
		// 순열 테스트 ===========================================
		List<String[]> list = new ArrayList<>();
		String[] array = {"a", "b", "c", "d"};
		ArrayUtil.Premutation(new String[0], array, list);
		for(String[] arr : list){
			System.out.println(Arrays.stream(arr).collect(Collectors.joining(", ")));
		}
		// 순열 테스트 끝 ===========================================
	}
}
