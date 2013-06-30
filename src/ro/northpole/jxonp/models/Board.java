package ro.northpole.jxonp.models;

import ro.northpole.jxonp.exceptions.CanNotMoveThere;
import ro.northpole.jxonp.exceptions.InvalidBoardCoordinates;
import ro.northpole.jxonp.exceptions.JXoNpException;
import ro.northpole.jxonp.exceptions.NotYourTurn;
import ro.northpole.jxonp.util.BaseBoard;
import ro.northpole.jxonp.util.Const;

public class Board extends BaseBoard {

	private MiniBoard miniBoard;
	private Mask mask;

	public Board() {
		super(9);
		mask = new Mask();
		miniBoard = new MiniBoard();
	}

	public void move(Move m) throws JXoNpException {
		if (!isValidCoordinates(m)) {
			throw new InvalidBoardCoordinates();
		}
		if (!isCorrectPlayerMoving(m)) {
			throw new NotYourTurn();
		}
		if (!isEmptyPosition(m)) {
			throw new CanNotMoveThere();
		}
		if (!isCorrectMovePosition(m)) {
			throw new CanNotMoveThere();
		}

		tiles[m.x][m.y] = m.kind;
		moves.add(m);

		MiniBoard mB = new MiniBoard();
		mB.from(tiles, mask, m);
		//Log.d("kiki", "board index:" + mask.getBoardIndex(m));
		//Log.d("kiki", mB.toString());
		if (mB.isFinished()) {
			switch (mask.getBoardIndex(m)) {
			case Const.BOARD_TOP_LEFT:
				miniBoard.setTile(new Move(0, 0, m.kind, m.time));
				break;
			case Const.BOARD_MIDDLE_LEFT:
				miniBoard.setTile(new Move(0, 1, m.kind, m.time));
				break;
			case Const.BOARD_BOTTOM_LEFT:
				miniBoard.setTile(new Move(0, 2, m.kind, m.time));
				break;
			case Const.BOARD_TOP_MIDDLE:
				miniBoard.setTile(new Move(1, 0, m.kind, m.time));
				break;
			case Const.BOARD_MIDDLE_MIDDLE:
				miniBoard.setTile(new Move(1, 1, m.kind, m.time));
				break;
			case Const.BOARD_BOTTOM_MIDDLE:
				miniBoard.setTile(new Move(1, 2, m.kind, m.time));
				break;
			case Const.BOARD_TOP_RIGHT:
				miniBoard.setTile(new Move(2, 0, m.kind, m.time));
				break;
			case Const.BOARD_MIDDLE_RIGHT:
				miniBoard.setTile(new Move(2, 1, m.kind, m.time));
				break;
			case Const.BOARD_BOTTOM_RIGHT:
				miniBoard.setTile(new Move(2, 2, m.kind, m.time));
				break;
			default:
				break;
			}
			// sa identific care board a fost terminat
			// sa marchez pe miniboard

			// System.out.println(m.toString());
			// int xx = m.x % 3;
			// int yy = m.y % 3;
			// Move tmpMove = new Move(xx, yy, m.kind, m.time);
			// System.out.println(tmpMove.toString());
			// miniBoard.setTile(tmpMove);
		}
	}

	public int getWinner() {
		return miniBoard.getWinner();
	}

	public int getTile(int x, int y) throws JXoNpException {
		if (!isValidCoordinates(x, y)) {
			throw new InvalidBoardCoordinates();
		}
		return tiles[x][y];
	}

	private boolean isCorrectMovePosition(Move m) {
		if (!moves.isEmpty()) {
			MoveList allowedMoves = mask.getAllowedMovesAfter(moves
					.getLastMove());
			Move tmpMove = new Move(m.x, m.y, Const.NONE, 0);

			if (!allowedMoves.contains(tmpMove)) {
				return false;
			}
		}
		return true;
	}

	private boolean isEmptyPosition(Move m) {
		return tiles[m.x][m.y] == Const.NONE;
	}

	private boolean isCorrectPlayerMoving(Move m) {
		return m.kind == whosTurnIsIt();
	}

	private boolean isValidCoordinates(Move m) {
		return isValidCoordinates(m.x, m.y);
	}

	private boolean isValidCoordinates(int x, int y) {
		return (0 <= x && x < 9 && 0 <= y && y < 9);
	}

	public boolean isFinished() {
		return miniBoard.isFinished();
	}

	public MiniBoard getMiniBoard() {
		return miniBoard;
	}

	public Mask getMask() {
		return mask;
	}

	public int getAllowedBoard() {
		return getMask().getSquareIndex(moves.getLastMove());
	}
}
