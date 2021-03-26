package com.rcg.stemmer.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * Class that adds all stemmed words into a Map class and sort it by value. <br>
 * <br>
 *
 * @author Raymond Gines
 */
public class SortableValueMap<K, V extends Comparable<V>> extends LinkedHashMap<K, V> {

	private static final long serialVersionUID = 1L;

	public SortableValueMap() {

	}

	public SortableValueMap(Map<K, V> map) {
		super(map);
	}

	public void sortByValue(final boolean descending, int numRowsFilter) {
		List<Map.Entry<K, V>> valueList = new LinkedList<Map.Entry<K, V>>(entrySet());
		Collections.sort(valueList, new Comparator<Map.Entry<K, V>>() {

			@Override
			public int compare(Map.Entry<K, V> entry1, Map.Entry<K, V> entry2) {

				if (descending) {
					return entry2.getValue().compareTo(entry1.getValue());
				} else {
					return entry1.getValue().compareTo(entry2.getValue());
				}
			}
		});

		clear();

		int rowNum = 0;
		for (Map.Entry<K, V> entry : valueList) {
			put(entry.getKey(), entry.getValue());

			rowNum++;

			if (rowNum == numRowsFilter) {
				break;
			}
		}
	}

}
