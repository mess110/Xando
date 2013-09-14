package ro.northpole.jxonp;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ro.northpole.jxonp.exceptions.CanNotMoveThere;
import ro.northpole.jxonp.exceptions.InvalidBoardCoordinates;
import ro.northpole.jxonp.exceptions.JXoNpException;
import ro.northpole.jxonp.exceptions.NotYourTurn;
import ro.northpole.jxonp.models.Board;
import ro.northpole.jxonp.models.Move;
import ro.northpole.jxonp.util.Const;
import ro.northpole.jxonp.util.Util;

public class BoardTest {

	private Board board;

	@Before
	public void setUp() throws Exception {
		board = new Board();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testBoardIsEmptyByDefault() throws JXoNpException {
		assertEquals(Const.NONE, board.getTile(0, 5));
		assertEquals(Const.NONE, board.getTile(8, 8));
	}

	@Test(expected = InvalidBoardCoordinates.class)
	public void testWithinBounds() throws JXoNpException {
		board.getTile(-1, -1);
	}
	
	@Test(expected = InvalidBoardCoordinates.class)
	public void testWithinBounds2() throws JXoNpException {
		board.getTile(9, 2);
	}
	
	@Test
	public void testWhosTurnIsIt() throws JXoNpException {
		assertEquals(board.whosTurnIsIt(), Const.X);
		board.move(new Move(0, 0, Const.X, Util.getTime()));
		assertEquals(board.whosTurnIsIt(), Const.O);
		board.move(new Move(0, 1, Const.O, Util.getTime()));
		assertEquals(board.whosTurnIsIt(), Const.X);
	}
	
	@Test(expected = NotYourTurn.class)
	public void testYourTurn() throws JXoNpException {
		assertEquals(board.whosTurnIsIt(), Const.X);
		board.move(new Move(0, 0, Const.O, Util.getTime()));
	}
	
	@Test(expected = CanNotMoveThere.class)
	public void testMovingInATakenTile() throws JXoNpException {
		assertEquals(board.whosTurnIsIt(), Const.X);
		board.move(new Move(0, 0, Const.X, Util.getTime()));
		board.move(new Move(0, 0, Const.O, Util.getTime()));
	}
	
	@Test(expected = CanNotMoveThere.class)
	public void testMovingInTheIncorrectSquare() throws JXoNpException {
		board.move(new Move(0, 0, Const.X, Util.getTime()));
		board.move(new Move(4, 4, Const.O, Util.getTime()));
	}
	
	public void testMovingInTheCorrectSquare() throws JXoNpException {
		board.move(new Move(0, 0, Const.X, Util.getTime()));
		board.move(new Move(1, 1, Const.O, Util.getTime()));
	}
}
