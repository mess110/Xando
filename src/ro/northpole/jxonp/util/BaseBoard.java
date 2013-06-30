package ro.northpole.jxonp.util;

import java.util.Collections;
import java.util.Comparator;

import ro.northpole.jxonp.models.Move;
import ro.northpole.jxonp.models.MoveList;

public class BaseBoard {
	protected int[][] tiles;
	protected MoveList moves;
	protected int size;

	public BaseBoard(int n) {
		tiles = new int[n][n];
		moves = new MoveList();
		this.size = n;
	}

	public int whosTurnIsIt() {
		return moves.size() % 2 == 0 ? Const.X : Const.O;
	}

	public MoveList getMoves() {
		return moves;
	}

	public int[][] getBoardTiles() {
		return tiles;
	}

	public void setMoveList(MoveList mL) {
		Collections.sort(mL, new Comparator<Move>() {

			@Override
			public int compare(Move o1, Move o2) {
				return (int) (o1.time - o2.time);
			}
		});
		for (int i = 0; i < mL.size(); i++) {
			Move m = mL.get(i);
			tiles[m.x][m.y] = m.kind;
		}
		moves = mL;
	}

	public String toString() {
		String s = "";
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				s += tiles[i][j];
			}
			s += "\n";
		}
		return s;
	}
}
