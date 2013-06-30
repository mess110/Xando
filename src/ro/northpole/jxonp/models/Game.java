package ro.northpole.jxonp.models;

import ro.northpole.jxonp.exceptions.GameFinished;
import ro.northpole.jxonp.exceptions.GameNotStarted;
import ro.northpole.jxonp.exceptions.JXoNpException;
import ro.northpole.jxonp.util.Const;

public class Game {

	private String id;
	private String p1, p2;
	private Board board;

	public Game() {
		board = new Board();
	}

	public int getMoveCount() {
		return board.getMoves().size();
	}

	public String getId() {
		return id;
	}

	public int whosTurnIsIt() {
		return board.whosTurnIsIt();
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean join(String playerName) {
		if (p1 == null) {
			p1 = playerName;
			return true;
		} else if (p2 == null) {
			p2 = playerName;
			return true;
		} else {
			return false;
		}
	}

	public void move(String playerName, int x, int y, long time)
			throws JXoNpException {
		if (!isStarted()) {
			throw new GameNotStarted();
		}
		if (isFinished()) {
			throw new GameFinished();
		}
		int kind = playerName.equals(p1) ? Const.X : Const.O;
		board.move(new Move(x, y, kind, time));
	}

	public boolean isFinished() {
		return board.isFinished();
	}

	public int getWinner() {
		return board.getWinner();
	}

	public boolean isStarted() {
		return p1 != null && p2 != null;
	}

	public String getNextPlayerName() {
		return whosTurnIsIt() == Const.X ? p1 : p2;
	}

	public String getPlayer1() {
		return p1;
	}

	public void setPlayer1(String player) {
		this.p1 = player;
	}

	public String getPlayer2() {
		return p2;
	}

	public void setPlayer2(String player) {
		this.p2 = player;
	}

	public Board getBoard() {
		return board;
	}
}
