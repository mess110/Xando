package ro.northpole.jxonp.models;

import java.util.ArrayList;

public class MoveList extends ArrayList<Move> {

	private static final long serialVersionUID = -2273281584602946700L;

	public MoveList() {
		super();
	}

	public Move getLastMove() {
		if (size() > 0) {
			return get(size() - 1);
		} else {
			return new Move(-1, -1, 0, 0);
		}
	}
}
