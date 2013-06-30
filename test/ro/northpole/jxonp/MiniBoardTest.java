package ro.northpole.jxonp;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ro.northpole.jxonp.models.Mask;
import ro.northpole.jxonp.models.MiniBoard;
import ro.northpole.jxonp.models.Move;
import ro.northpole.jxonp.util.Const;

public class MiniBoardTest {

	private MiniBoard miniBoard;

	@Before
	public void setUp() throws Exception {
		miniBoard = new MiniBoard();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testIsFinished() {
		assertEquals(false, miniBoard.isFinished());
	}

	@Test
	public void testIsFinishedXWon() {
		miniBoard.setTile(new Move(0, 0, Const.X, 0));
		miniBoard.setTile(new Move(0, 1, Const.X, 0));
		miniBoard.setTile(new Move(0, 2, Const.X, 0));
		assertEquals(true, miniBoard.isFinished());
	}

	@Test
	public void testIsFinishedXWon2() {
		miniBoard.setTile(new Move(0, 1, Const.X, 0));
		miniBoard.setTile(new Move(1, 1, Const.X, 0));
		miniBoard.setTile(new Move(2, 1, Const.X, 0));
		assertEquals(true, miniBoard.isFinished());
	}

	@Test
	public void testIsFinishedXWonDiagonally() {
		miniBoard.setTile(new Move(0, 0, Const.X, 0));
		miniBoard.setTile(new Move(1, 1, Const.X, 0));
		miniBoard.setTile(new Move(2, 2, Const.X, 0));
		assertEquals(true, miniBoard.isFinished());
	}

	@Test
	public void testFrom() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				assertEquals(0, miniBoard.getTile(i, j));
			}
		}

		int[][] mainTiles = new int[][] {
				{ 0, 1, 2, 0, 0, 0, 1, 0, 2 },
				{ 0, 1, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 2, 1, 0, 0, 0, 1, 0, 1 },
				{ 0, 0, 0, 1, 0, 1, 0, 0, 0 },
				{ 0, 0, 0, 0, 2, 1, 0, 0, 0 },
				{ 0, 0, 0, 0, 2, 2, 2, 2, 1 },
				{ 1, 2, 1, 0, 1, 0, 2, 2, 1 },
				{ 2, 1, 2, 0, 2, 0, 2, 2, 1 },
				{ 2, 1, 2, 0, 0, 0, 2, 2, 1 },
				};
		miniBoard.from(mainTiles, new Mask(), new Move(1, 1, 0, 0));
		String expected = "012\n010\n021\n";
		assertEquals(expected, miniBoard.toString());
		
		miniBoard.from(mainTiles, new Mask(), new Move(3, 3, 0, 0));
		expected = "101\n021\n022\n";
		assertEquals(expected, miniBoard.toString());
		
		miniBoard.from(mainTiles, new Mask(), new Move(6, 6, 0, 0));
		expected = "221\n221\n221\n";
		assertEquals(expected, miniBoard.toString());
		
		miniBoard.from(mainTiles, new Mask(), new Move(2, 8, 0, 0));
		expected = "102\n000\n101\n";
		assertEquals(expected, miniBoard.toString());
		miniBoard.from(mainTiles, new Mask(), new Move(1, 7, 0, 0));
		assertEquals(expected, miniBoard.toString());
	}
}
