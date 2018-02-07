package org.eclipse.papyrus.uml.diagram.sequence.diagram.model.internal.layout;

import java.util.Collections;
import java.util.List;

public final class ListUtils {

	private ListUtils() {
		// utilities
	}

	public static <T> List<T> getItemsBefore(List<T> list, T element) {
		if (!list.contains(element)) {
			return Collections.emptyList();
		}
		return list.subList(0, list.indexOf(element));
	}

	public static <T> List<T> getItemsAfter(List<T> list, T element) {
		if (!list.contains(element)) {
			return Collections.emptyList();
		}
		return list.subList(list.indexOf(element) + 1, list.size());
	}

}
