package com.risetek.bindery;

import java.util.List;

import com.risetek.utils.Icons.Icon;

public interface PlainMenuLoader {

	public class PlainMenuContent implements Comparable<PlainMenuContent> {
		public String title;
		public String token;
		public String iconName;
		public Icon icon;

		int order;
		
		@Override
		public int compareTo(PlainMenuContent o) {
			return (order - o.order);
		}
	}

	public List<PlainMenuContent> getMenus();
}
