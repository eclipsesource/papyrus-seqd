package org.eclipse.papyrus.uml.diagram.sequence.diagram.model.internal.layout;

public class MPositionRange {

	private int begin, end;

	public MPositionRange(int begin, int end) {
		super();
		this.begin = begin;
		this.end = end;
	}

	public int getBegin() {
		return begin;
	}

	public int getEnd() {
		return end;
	}

	@Override
	public String toString() {
		return "MPositionRange [begin=" + begin + ", end=" + end + "]";
	}

}
