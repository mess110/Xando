package ro.northpole.jxonp.models;

import ro.northpole.jxonp.util.BaseBoard;
import ro.northpole.jxonp.util.Const;

public class MiniBoard extends BaseBoard {

	public MiniBoard() {
		super(3);
	}

	public void setTile(Move m) {
		if (tiles[m.x][m.y] == Const.NONE) {
			moves.add(m);
			tiles[m.x][m.y] = m.kind;
		}
	}

	public int getTile(int x, int y) {
		return tiles[x][y];
	}

	public int getWinner() {
		if (symbolWon(Const.X)) {
			return Const.X;
		} else if (symbolWon(Const.O)) {
			return Const.O;
		} else {
			return Const.NONE;
		}
	}

	public boolean isFinished() {
		return symbolWon(Const.X) || symbolWon(Const.O);
	}

	private boolean symbolWon(int symbol) {
		for (int i = 0; i < 3; i++) {
			if (tiles[i][0] == symbol && tiles[i][1] == symbol
					&& tiles[i][2] == symbol) {
				return true;
			}
			if (tiles[0][i] == symbol && tiles[1][i] == symbol
					&& tiles[2][i] == symbol) {
				return true;
			}
		}
		if (tiles[0][0] == symbol && tiles[1][1] == symbol
				&& tiles[2][2] == symbol) {
			return true;
		}
		if (tiles[0][2] == symbol && tiles[1][1] == symbol
				&& tiles[2][0] == symbol) {
			return true;
		}
		return false;
	}

	public void from(int[][] mainTiles, Mask mask, Move m) {
		Move squareCorner = mask.getSquareCorner(m);
		for (int x = squareCorner.x; x < squareCorner.x + 3; x++) {
			for (int y = squareCorner.y; y < squareCorner.y + 3; y++) {
				tiles[x % 3][y % 3] = mainTiles[x][y];
			}
		}
	}
}
