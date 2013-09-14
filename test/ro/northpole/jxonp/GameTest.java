package ro.northpole.jxonp;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ro.northpole.jxonp.exceptions.JXoNpException;
import ro.northpole.jxonp.models.Game;
import ro.northpole.jxonp.util.Util;

public class GameTest {

	private Game game;

	@Before
	public void setUp() throws Exception {
		game = new Game();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testJoin() {
		game.join(Const.PLAYER1);
		assertEquals(false, game.isStarted());
		game.join(Const.PLAYER2);
		assertEquals(true, game.isStarted());
	}

	@Test
	public void testMove() throws JXoNpException {
		game.join(Const.PLAYER1);
		game.join(Const.PLAYER2);

		game.move(Const.PLAYER1, 0, 0, Util.getTime());
		game.move(Const.PLAYER2, 1, 1, Util.getTime());
		game.move(Const.PLAYER1, 4, 5, Util.getTime());
		game.move(Const.PLAYER2, 5, 8, Util.getTime());
	}

	@Test
	public void testMiniBoard() throws JXoNpException {
		game.join(Const.PLAYER1);
		game.join(Const.PLAYER2);

		move(0, 0);
		move(1, 1);
		move(3, 3);
		move(0, 1);
		move(1, 5);
		move(4, 6);
		move(3, 0);
		move(2, 1);
		move(6, 4);
		move(0, 4);
		move(1, 4);
		// move(3, 1);
		// System.out.println(game.getBoard().toString());
		// System.out.println(game.getBoard().getMiniBoard().toString());
	}

	@Test
	public void testMiniBoard2() throws JXoNpException {
		game.join(Const.PLAYER1);
		game.join(Const.PLAYER2);

		move(8, 8);
		move(7, 7);
		move(5, 5);
		move(8, 6);
		move(8, 2);
		move(6, 8);
		move(1, 8);
		move(4, 7);
		move(4, 5);
		move(3, 6);
		move(1, 2);
		move(5, 8);
		move(6, 6);
		move(1, 1);
		move(3, 5); // x on middle
		move(2, 6);
		move(8, 0);
		move(7, 1);
		move(5, 4);
		move(8, 3);
		move(8, 1); // x on top right
		move(7, 3);
		move(3,2);
		move(1,7);
		move(4,3);
		move(3,1);
		move(0,5);
		move(0,8);
		assertTrue(game.isFinished());
		assertTrue(game.isStarted());
		assertEquals(ro.northpole.jxonp.util.Const.O, game.getWinner());
		//System.out.println(game.getBoard().toString());
		//System.out.println(game.getBoard().getMiniBoard().toString());
	}

	private void move(int x, int y) throws JXoNpException {
		game.move(game.getNextPlayerName(), x, y, Util.getTime());

	}
}
