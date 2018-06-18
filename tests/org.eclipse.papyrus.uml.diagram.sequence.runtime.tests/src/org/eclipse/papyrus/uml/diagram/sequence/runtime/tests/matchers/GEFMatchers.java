/*****************************************************************************
 * Copyright (c) 2018 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.sequence.runtime.tests.matchers;

import static org.eclipse.papyrus.uml.interaction.tests.matchers.NumberMatchers.isNear;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.hamcrest.Description;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.SelfDescribing;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * Matchers for assertions on GEF.
 *
 * @author Christian W. Damus
 */
public class GEFMatchers {

	/**
	 * Not instantiable by clients.
	 */
	private GEFMatchers() {
		super();
	}

	/**
	 * Matcher for a rectangle.
	 *
	 * @param x
	 *            the expected x location
	 * @param y
	 *            the expected y location
	 * @param width
	 *            the expected width
	 * @param height
	 *            the expected height
	 *
	 * @return the rectangle matcher
	 */
	public static Matcher<Rectangle> isRect(int x, int y, int width, int height) {
		return isRect(is(x), is(y), is(width), is(height));
	}

	/**
	 * Matcher for a rectangle.
	 *
	 * @param x
	 *            matcher for the x location, or {@code null} if none
	 * @param y
	 *            matcher for the y location, or {@code null} if none
	 * @param width
	 *            matcher for the width, or {@code null} if none
	 * @param height
	 *            matcher for the height or {@code null} if none
	 *
	 * @return the rectangle matcher
	 */
	public static Matcher<Rectangle> isRect(Matcher<? super Integer> x, Matcher<? super Integer> y,
			Matcher<? super Integer> width, Matcher<? super Integer> height) {

		return new TypeSafeDiagnosingMatcher<Rectangle>() {
			@Override
			public void describeTo(Description description) {
				boolean appended = false;
				description.appendText("rectangle that ");

				SelfDescribing[] parts = { x, y, width, height };
				String[] names = { "x", "y", "width", "height" };
				for (int i = 0; i < parts.length; i++) {
					if (parts[i] == null) {
						continue;
					}

					if (appended) {
						description.appendText(" and ");
					}
					description.appendText(names[i]).appendText(" that ");
					description.appendDescriptionOf(parts[i]);
					appended = true;
				}

				if (!appended) {
					description.appendText("exists");
				}
			}

			@Override
			protected boolean matchesSafely(Rectangle item, Description mismatchDescription) {
				if ((x != null) && !x.matches(item.x())) {
					mismatchDescription.appendText("x ");
					x.describeMismatch(item.x(), mismatchDescription);
					return false;
				}
				if ((y != null) && !y.matches(item.y())) {
					mismatchDescription.appendText("y ");
					y.describeMismatch(item.y(), mismatchDescription);
					return false;
				}
				if ((width != null) && !width.matches(item.width())) {
					mismatchDescription.appendText("width ");
					width.describeMismatch(item.width(), mismatchDescription);
					return false;
				}
				if ((height != null) && !height.matches(item.height())) {
					mismatchDescription.appendText("height ");
					height.describeMismatch(item.height(), mismatchDescription);
					return false;
				}

				return true;
			}
		};
	}

	/**
	 * Matcher for a point.
	 *
	 * @param x
	 *            the expected x location
	 * @param y
	 *            the expected y location
	 *
	 * @return the point matcher
	 */
	public static Matcher<Point> isPoint(int x, int y) {
		return isPoint(is(x), is(y));
	}

	/**
	 * Matcher for a point.
	 *
	 * @param x
	 *            matcher for the x location, or {@code null} if none
	 * @param y
	 *            matcher for the y location, or {@code null} if none
	 *
	 * @return the point matcher
	 */
	public static Matcher<Point> isPoint(Matcher<? super Integer> x, Matcher<? super Integer> y) {

		return new TypeSafeDiagnosingMatcher<Point>() {
			@Override
			public void describeTo(Description description) {
				boolean appended = false;
				description.appendText("point that ");

				SelfDescribing[] parts = { x, y };
				String[] names = { "x", "y" };
				for (int i = 0; i < parts.length; i++) {
					if (parts[i] == null) {
						continue;
					}

					if (appended) {
						description.appendText(" and ");
					}
					description.appendText(names[i]).appendText(" that ");
					description.appendDescriptionOf(parts[i]);
					appended = true;
				}

				if (!appended) {
					description.appendText("exists");
				}
			}

			@Override
			protected boolean matchesSafely(Point item, Description mismatchDescription) {
				if ((x != null) && !x.matches(item.x())) {
					mismatchDescription.appendText("x ");
					x.describeMismatch(item.x(), mismatchDescription);
					return false;
				}
				if ((y != null) && !y.matches(item.y())) {
					mismatchDescription.appendText("y ");
					y.describeMismatch(item.y(), mismatchDescription);
					return false;
				}

				return true;
			}
		};
	}

	/**
	 * Matcher for a dimension.
	 *
	 * @param width
	 *            the expected width
	 * @param height
	 *            the expected height
	 *
	 * @return the dimension matcher
	 */
	public static Matcher<Dimension> isSize(int width, int height) {
		return isSize(is(width), is(height));
	}

	/**
	 * Matcher for a dimension.
	 *
	 * @param width
	 *            matcher for the width, or {@code null} if none
	 * @param height
	 *            matcher for the height or {@code null} if none
	 *
	 * @return the dimension matcher
	 */
	public static Matcher<Dimension> isSize(Matcher<? super Integer> width,
			Matcher<? super Integer> height) {

		return new TypeSafeDiagnosingMatcher<Dimension>() {
			@Override
			public void describeTo(Description description) {
				boolean appended = false;
				description.appendText("dimension that ");

				SelfDescribing[] parts = { width, height };
				String[] names = { "width", "height" };
				for (int i = 0; i < parts.length; i++) {
					if (parts[i] == null) {
						continue;
					}

					if (appended) {
						description.appendText(" and ");
					}
					description.appendText(names[i]).appendText(" that ");
					description.appendDescriptionOf(parts[i]);
					appended = true;
				}

				if (!appended) {
					description.appendText("exists");
				}
			}

			@Override
			protected boolean matchesSafely(Dimension item, Description mismatchDescription) {
				if ((width != null) && !width.matches(item.width())) {
					mismatchDescription.appendText("width ");
					width.describeMismatch(item.width(), mismatchDescription);
					return false;
				}
				if ((height != null) && !height.matches(item.height())) {
					mismatchDescription.appendText("height ");
					height.describeMismatch(item.height(), mismatchDescription);
					return false;
				}

				return true;
			}
		};
	}

	/**
	 * Matcher for a point-list. Every matcher must match some point in the list.
	 *
	 * @param where
	 *            the point matchers
	 *
	 * @return the point-list matcher
	 */
	@SafeVarargs
	public static Matcher<PointList> hasPoints(Matcher<? super Point>... where) {
		return new TypeSafeDiagnosingMatcher<PointList>() {

			@Override
			public void describeTo(Description description) {
				description.appendText("point list that all of");
				description.appendList(" ", ", ", "", Arrays.asList(where));
			}

			@Override
			protected boolean matchesSafely(PointList item, Description mismatchDescription) {
				List<Point> points = new ArrayList<>(item.size());
				for (int i = 0; i < item.size(); i++) {
					points.add(item.getPoint(i));
				}

				boolean result = false;
				for (Matcher<? super Point> next : where) {
					Matcher<Iterable<? super Point>> nextMatcher = hasItem(next);

					result = nextMatcher.matches(points);
					if (!result) {
						nextMatcher.describeMismatch(item, mismatchDescription);
						break; // Nothing more to check
					}
				}
				return false;
			}
		};
	}

	/**
	 * Matcher for the start and end of point-list.
	 *
	 * @param from
	 *            the start matcher
	 * @param to
	 *            the end matcher
	 *
	 * @return the point-list matcher
	 */
	public static Matcher<PointList> runs(Matcher<? super Point> from, Matcher<? super Point> to) {
		return new TypeSafeDiagnosingMatcher<PointList>() {

			@Override
			public void describeTo(Description description) {
				description.appendText("point list runs from ");
				description.appendDescriptionOf(from);
				description.appendText(" to ");
				description.appendDescriptionOf(to);
			}

			@Override
			protected boolean matchesSafely(PointList item, Description mismatchDescription) {
				boolean result = from.matches(item.getFirstPoint());
				if (!result) {
					from.describeMismatch(item.getFirstPoint(), mismatchDescription);
				} else {
					result = to.matches(item.getLastPoint());
					if (!result) {
						to.describeMismatch(item.getLastPoint(), mismatchDescription);
					}
				}
				return result;
			}
		};
	}

	/**
	 * Matcher for the start and end of point-list.
	 *
	 * @param fromX,&nbsp;fromY
	 *            the start point
	 * @param toX,&nbsp;toY
	 *            the end point
	 *
	 * @return the point-list matcher
	 */
	public static Matcher<PointList> runs(int fromX, int fromY, int toX, int toY) {
		return runs(isPoint(fromX, fromY), isPoint(toX, toY));
	}

	/**
	 * Fuzzy matcher for a point.
	 *
	 * @param x
	 *            the expected x location
	 * @param y
	 *            the expected y location
	 * @param tolerance
	 *            a tolerance for errors in the point coördinate values
	 *
	 * @return the point matcher
	 */
	public static Matcher<Point> isPoint(int x, int y, int tolerance) {
		return isPoint(isNear(x, tolerance), isNear(y, tolerance));
	}

	/**
	 * Fuzzy matcher for a dimension.
	 *
	 * @param width
	 *            the expected width
	 * @param height
	 *            the expected height
	 * @param tolerance
	 *            a tolerance for errors in the dimension measures
	 *
	 * @return the point matcher
	 */
	public static Matcher<Dimension> isSize(int width, int height, int tolerance) {
		return isSize(isNear(width, tolerance), isNear(height, tolerance));
	}

	/**
	 * Fuzzy matcher for a rectangle.
	 *
	 * @param x
	 *            the expected x location
	 * @param y
	 *            the expected y location
	 * @param width
	 *            the expected width
	 * @param height
	 *            the expected height
	 * @param tolerance
	 *            a tolerance for errors in the rectangle coördinate values
	 *
	 * @return the rectangle matcher
	 */
	public static Matcher<Rectangle> isRect(int x, int y, int width, int height, int tolerance) {
		return isRect(isNear(x, tolerance), isNear(y, tolerance), isNear(width, tolerance),
				isNear(height, tolerance));
	}

	/**
	 * Fuzzy matcher for the start and end of point-list.
	 *
	 * @param fromX,&nbsp;fromY
	 *            the start point
	 * @param toX,&nbsp;toY
	 *            the end point
	 * @param tolerance
	 *            a tolerance for errors in the point coördinate values
	 *
	 * @return the point-list matcher
	 */
	public static Matcher<PointList> runs(int fromX, int fromY, int toX, int toY, int tolerance) {
		return runs(isPoint(fromX, fromY, tolerance), isPoint(toX, toY, tolerance));
	}

	static <T> Matcher<T> isAt(Matcher<? super Point> where, Function<? super T, ? extends Point> point) {
		return new FeatureMatcher<T, Point>(where, "location", "location") {
			@Override
			protected Point featureValueOf(T actual) {
				return point.apply(actual);
			}
		};
	}

	static <T> Matcher<T> isSized(Matcher<? super Dimension> what,
			Function<? super T, ? extends Dimension> dimension) {
		return new FeatureMatcher<T, Dimension>(what, "size", "size") {
			@Override
			protected Dimension featureValueOf(T actual) {
				return dimension.apply(actual);
			}
		};
	}

	static <T> Matcher<T> isBounded(Matcher<? super Rectangle> how,
			Function<? super T, ? extends Rectangle> rectangle) {
		return new FeatureMatcher<T, Rectangle>(how, "bounds", "bounds") {
			@Override
			protected Rectangle featureValueOf(T actual) {
				return rectangle.apply(actual);
			}
		};
	}

	static <T> Matcher<T> runs(Matcher<? super PointList> where,
			Function<? super T, ? extends PointList> points) {
		return new FeatureMatcher<T, PointList>(where, "points", "points") {
			@Override
			protected PointList featureValueOf(T actual) {
				return points.apply(actual);
			}
		};
	}

	static Function<Rectangle, Point> location() {
		return Rectangle::getLocation;
	}

	static Function<Rectangle, Dimension> size() {
		return Rectangle::getSize;
	}

	static Function<IFigure, Rectangle> bounds() {
		return figure -> {
			Rectangle result = figure.getBounds().getCopy();
			figure.getParent().translateToAbsolute(result); // Assertions are in absolute terms
			return result;
		};
	}

	static Function<IFigure, Point> figureLocation() {
		return location().compose(bounds());
	}

	static Function<IFigure, Dimension> figureSize() {
		return size().compose(bounds());
	}

	static Function<Object, GraphicalEditPart> graphical() {
		return cast(GraphicalEditPart.class);
	}

	private static <T> Function<Object, T> cast(Class<? extends T> type) {
		return obj -> {
			assertThat(obj, instanceOf(type));
			return type.cast(obj);
		};
	}

	static Function<GraphicalEditPart, IFigure> figure() {
		return GraphicalEditPart::getFigure;
	}

	static Function<EditPart, IFigure> editPartFigure() {
		return figure().compose(graphical());
	}

	static Function<EditPart, Rectangle> editPartBounds() {
		return bounds().compose(editPartFigure());
	}

	static Function<EditPart, Point> editPartLocation() {
		return figureLocation().compose(editPartFigure());
	}

	static Function<EditPart, Dimension> editPartSize() {
		return figureSize().compose(editPartFigure());
	}

	static Function<Connection, PointList> points() {
		return Connection::getPoints;
	}

	static Function<Object, Connection> connection() {
		return cast(Connection.class);
	}

	static Function<IFigure, PointList> figurePoints() {
		return points().compose(connection());
	}

	static Function<EditPart, Connection> editPartConnection() {
		return connection().compose(editPartFigure());
	}

	static Function<EditPart, PointList> editPartPoints() {
		return points().compose(editPartConnection());
	}

	/**
	 * Assertions on the geometry of GEF figures. All coördinates are in absolute
	 * space, as is the convention of the <em>Logical Model</em>.
	 */
	public static class Figures {

		private Figures() {
			super();
		}

		public static Matcher<IFigure> isAt(Matcher<? super Point> where) {
			return GEFMatchers.isAt(where, figureLocation());
		}

		public static Matcher<IFigure> isAt(int x, int y) {
			return isAt(isPoint(x, y));
		}

		public static Matcher<IFigure> isAt(int x, int y, int tolerance) {
			return isAt(isPoint(x, y, tolerance));
		}

		public static Matcher<IFigure> isAt(Matcher<? super Integer> x, Matcher<? super Integer> y) {
			return isAt(isPoint(x, y));
		}

		public static Matcher<IFigure> isSized(Matcher<? super Dimension> what) {
			return isSized(what);
		}

		public static Matcher<IFigure> isSized(int width, int height) {
			return isSized(isSize(width, height));
		}

		public static Matcher<IFigure> isSized(int width, int height, int tolerance) {
			return isSized(isSize(width, height, tolerance));
		}

		public static Matcher<IFigure> isSized(Matcher<? super Integer> width,
				Matcher<? super Integer> height) {
			return isSized(isSize(width, height));
		}

		public static Matcher<IFigure> isBounded(Matcher<? super Rectangle> how) {
			return GEFMatchers.isBounded(how, bounds());
		}

		public static Matcher<IFigure> isBounded(int x, int y, int width, int height) {
			return isBounded(isRect(x, y, width, height));
		}

		public static Matcher<IFigure> isBounded(int x, int y, int width, int height, int tolerance) {
			return isBounded(isRect(x, y, width, height, tolerance));
		}

		public static Matcher<IFigure> isBounded(Matcher<? super Integer> x, Matcher<? super Integer> y,
				Matcher<? super Integer> width, Matcher<? super Integer> height) {
			return isBounded(isRect(x, y, width, height));
		}

		@SafeVarargs
		public static Matcher<IFigure> hasPoints(Matcher<? super Point>... where) {
			return GEFMatchers.runs(GEFMatchers.hasPoints(where), figurePoints());
		}

		public static Matcher<IFigure> runs(Matcher<? super Point> from, Matcher<? super Point> to) {
			return GEFMatchers.runs(GEFMatchers.runs(from, to), figurePoints());
		}

		public static Matcher<IFigure> runs(int fromX, int fromY, int toX, int toY) {
			return GEFMatchers.runs(GEFMatchers.runs(fromX, fromY, toX, toY), figurePoints());
		}

		public static Matcher<IFigure> runs(int fromX, int fromY, int toX, int toY, int tolerance) {
			return GEFMatchers.runs(GEFMatchers.runs(fromX, fromY, toX, toY, tolerance), figurePoints());
		}

	}

	/**
	 * Assertions on the geometry of GEF edit-parts. All coördinates are in absolute
	 * space, as is the convention of the <em>Logical Model</em>.
	 */
	public static class EditParts {

		private EditParts() {
			super();
		}

		public static Matcher<EditPart> isAt(Matcher<? super Point> where) {
			return GEFMatchers.isAt(where, editPartLocation());
		}

		public static Matcher<EditPart> isAt(int x, int y) {
			return isAt(isPoint(x, y));
		}

		public static Matcher<EditPart> isAt(int x, int y, int tolerance) {
			return isAt(isPoint(x, y, tolerance));
		}

		public static Matcher<EditPart> isAt(Matcher<? super Integer> x, Matcher<? super Integer> y) {
			return isAt(isPoint(x, y));
		}

		public static Matcher<EditPart> isSized(Matcher<? super Dimension> what) {
			return GEFMatchers.isSized(what, editPartSize());
		}

		public static Matcher<EditPart> isSized(int width, int height) {
			return isSized(isSize(width, height));
		}

		public static Matcher<EditPart> isSized(int width, int height, int tolerance) {
			return isSized(isSize(width, height, tolerance));
		}

		public static Matcher<EditPart> isSized(Matcher<? super Integer> width,
				Matcher<? super Integer> height) {
			return isSized(isSize(width, height));
		}

		public static Matcher<EditPart> isBounded(Matcher<? super Rectangle> how) {
			return GEFMatchers.isBounded(how, editPartBounds());
		}

		public static Matcher<EditPart> isBounded(int x, int y, int width, int height) {
			return isBounded(isRect(x, y, width, height));
		}

		public static Matcher<EditPart> isBounded(int x, int y, int width, int height, int tolerance) {
			return isBounded(isRect(x, y, width, height, tolerance));
		}

		public static Matcher<EditPart> isBounded(Matcher<? super Integer> x, Matcher<? super Integer> y,
				Matcher<? super Integer> width, Matcher<? super Integer> height) {
			return isBounded(isRect(x, y, width, height));
		}

		@SafeVarargs
		public static Matcher<EditPart> hasPoints(Matcher<? super Point>... where) {
			return GEFMatchers.runs(GEFMatchers.hasPoints(where), editPartPoints());
		}

		public static Matcher<EditPart> runs(Matcher<? super Point> from, Matcher<? super Point> to) {
			return GEFMatchers.runs(GEFMatchers.runs(from, to), editPartPoints());
		}

		public static Matcher<EditPart> runs(int fromX, int fromY, int toX, int toY) {
			return GEFMatchers.runs(GEFMatchers.runs(fromX, fromY, toX, toY), editPartPoints());
		}

		public static Matcher<EditPart> runs(int fromX, int fromY, int toX, int toY, int tolerance) {
			return GEFMatchers.runs(GEFMatchers.runs(fromX, fromY, toX, toY, tolerance), editPartPoints());
		}

	}

}
