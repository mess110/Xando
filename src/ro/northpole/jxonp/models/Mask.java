package ro.northpole.jxonp.models;

import ro.northpole.jxonp.util.Const;

public class Mask {

	private int[][] tiles;
	private int[][] mini;

	public Mask() {
		int tl = Const.BOARD_TOP_LEFT;
		int tm = Const.BOARD_TOP_MIDDLE;
		int tr = Const.BOARD_TOP_RIGHT;
		int ml = Const.BOARD_MIDDLE_LEFT;
		int mm = Const.BOARD_MIDDLE_MIDDLE;
		int mr = Const.BOARD_MIDDLE_RIGHT;
		int bl = Const.BOARD_BOTTOM_LEFT;
		int bm = Const.BOARD_BOTTOM_MIDDLE;
		int br = Const.BOARD_BOTTOM_RIGHT;

		tiles = new int[][] { { tl, tl, tl, tm, tm, tm, tr, tr, tr },
				{ tl, tl, tl, tm, tm, tm, tr, tr, tr },
				{ tl, tl, tl, tm, tm, tm, tr, tr, tr },
				{ ml, ml, ml, mm, mm, mm, mr, mr, mr },
				{ ml, ml, ml, mm, mm, mm, mr, mr, mr },
				{ ml, ml, ml, mm, mm, mm, mr, mr, mr },
				{ bl, bl, bl, bm, bm, bm, br, br, br },
				{ bl, bl, bl, bm, bm, bm, br, br, br },
				{ bl, bl, bl, bm, bm, bm, br, br, br } };

		mini = new int[][] { { tl, tm, tr }, { ml, mm, mr }, { bl, bm, br } };
	}
	
	public int getBoardIndex(Move m) {
		return tiles[m.x][m.y];
	}

	public int getSquareIndex(Move m) {
		return mini[m.x % 3][m.y % 3];
	}

	public MoveList getAllowedMovesAfter(Move m) {
		MoveList result = new MoveList();

		int square = getSquareIndex(m);
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				if (tiles[x][y] == square) {
					result.add(new Move(x, y, Const.NONE, 0));
				}
			}
		}

		return result;
	}

	public Move getSquareCorner(Move m) {
		Move tmpMove = new Move(m.x, m.y, Const.NONE, m.time);
		while (tmpMove.x % 3 != 0) {
			tmpMove.x = tmpMove.x - 1;
		}
		while (tmpMove.y % 3 != 0) {
			tmpMove.y = tmpMove.y - 1;
		}
		return tmpMove;
	}
}
