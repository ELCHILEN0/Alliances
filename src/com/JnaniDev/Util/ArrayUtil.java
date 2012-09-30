package com.JnaniDev.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayUtil {
	public static List<String> asList(String string) {
		List<String> list;
		if(string != null && string != "")
			list = new ArrayList<String>(Arrays.asList(string.split(", ")));
		else
			list = new ArrayList<String>();
		return list;
	}
	
	public static String toString(List<String> list) {
		StringBuilder sb = new StringBuilder();
		if(list.size() != 0 && !list.get(0).equals("")) {
			sb.append(list.get(0));
			for(int i=1; i<list.size(); i++) {
			    sb.append(", ");
			    sb.append(list.get(i));
			}
		}
		return sb.toString();
	}
	
}
