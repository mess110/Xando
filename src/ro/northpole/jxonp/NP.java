package ro.northpole.jxonp;

import java.io.IOException;

import ro.northpole.jnorthpole.util.Util;
import ro.northpole.jxonp.exceptions.JXoNpException;
import ro.northpole.jxonp.models.Game;
import ro.northpole.jxonp.util.NPClient;

import com.google.gson.JsonObject;

public class NP extends NPClient {

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

	public NP() throws IOException {
		super();
		JsonObject jsonGame = newGame();
		game = gson.fromJson(jsonGame, Game.class);
	}

	public NP(String id) throws JXoNpException, IOException {
		super();
		JsonObject jsonGame = getGame(id);
		game = gson.fromJson(jsonGame, Game.class);
	}

	public void join(String name) throws JXoNpException, IOException {
		boolean joined = game.join(name);
		if (joined) {
			JsonObject jsonGame = updateGame(game);
			game = gson.fromJson(jsonGame, Game.class);
		}
	}
	
	public void move(String name, int x, int y) throws JXoNpException, IOException {
		game.move(name, x, y, Util.getTime());
		JsonObject jsonGame = updateGame(game);
		game = gson.fromJson(jsonGame, Game.class);
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
