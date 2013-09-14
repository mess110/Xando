package ro.northpole.jxonp;

import java.io.IOException;

import ro.northpole.jxonp.exceptions.JXoNpException;
import ro.northpole.jxonp.models.Game;
import ro.northpole.jxonp.util.Util;

public class NP {

	public static final String NAMESPACE = "namespace";
	public static final String NAMESPACE_VALUE = "jnpxo";
	public static final String PLAYER1 = "player1";
	public static final String PLAYER2 = "player2";
	public static final String ID = "id";
	public static final String BOARD = "board";
	public static final String X = "x";
	public static final String Y = "y";
	public static final String KIND = "kind";
	public static final String MOVE_LIST = "movelist";
	public static final String MINI_BOARD = "miniboard";
	public static final String TIME = "time";

	private Game game;

	public NP(boolean online) throws IOException {
		game = new Game();
		game.setId("offline");
	}

	public NP(String id, boolean online) throws JXoNpException, IOException {
		game = new Game();
		game.setId(id);
	}

	public void join(String name) throws JXoNpException, IOException {
		game.join(name);
	}

	public void move(String name, int x, int y) throws JXoNpException,
			IOException {
		game.move(name, x, y, Util.getTime());
	}

	public Game getGame() {
		return game;
	}

	public int[][] getBoardTiles() {
		return game.getBoard().getBoardTiles();
	}

	public int whosTurnIsIt() {
		return game.whosTurnIsIt();
	}

	public int[][] getMiniBoardTiles() {
		return game.getBoard().getMiniBoard().getBoardTiles();
	}
}
