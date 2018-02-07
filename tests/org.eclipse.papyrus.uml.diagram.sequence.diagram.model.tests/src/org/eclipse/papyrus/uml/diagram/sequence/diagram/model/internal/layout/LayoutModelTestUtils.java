package org.eclipse.papyrus.uml.diagram.sequence.diagram.model.internal.layout;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

public class LayoutModelTestUtils {

	private LayoutModelTestUtils() {
		// no instantiation
	}

	public static void assertSameContent(List<?> expected, List<?> actual) {
		assertTrue(actual.containsAll(expected));
		assertEquals(expected.size(), actual.size());
	}

	public static ILayoutPreferences getAllTenPrefs() {
		return new ILayoutPreferences() {
			@Override
			public int getDefaultHeightOfLifelineHead() {
				return 10;
			}

			@Override
			public int getPaddingTop(Class<? extends MElement> elementType) {
				return 10;
			}

			@Override
			public int getPaddingBottom(Class<? extends MElement> elementType) {
				return 10;
			}

			@Override
			public int getMessageHeight() {
				return 10;
			}

			@Override
			public int getDefaultWidth(Class<? extends MElement> elementType) {
				return 10;
			}

			@Override
			public int getFragmentPaddingLeft() {
				return 10;
			}

			@Override
			public int getFragmentPaddingRight() {
				return 10;
			}
		};
	}

}
