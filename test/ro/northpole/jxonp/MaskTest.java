package ro.northpole.jxonp;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ro.northpole.jxonp.models.Mask;
import ro.northpole.jxonp.models.Move;
import ro.northpole.jxonp.models.MoveList;
import ro.northpole.jxonp.util.Const;

public class MaskTest {

	private Mask mask;

	@Before
	public void setUp() throws Exception {
		mask = new Mask();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSquareIndex() {
		int square = mask.getSquareIndex(new Move(0, 0, Const.NONE, 0));
		assertEquals(square, Const.BOARD_TOP_LEFT);

		square = mask.getSquareIndex(new Move(0, 1, Const.NONE, 0));
		assertEquals(square, Const.BOARD_TOP_MIDDLE);

		square = mask.getSquareIndex(new Move(3, 2, Const.NONE, 0));
		assertEquals(square, Const.BOARD_TOP_RIGHT);

		square = mask.getSquareIndex(new Move(4, 4, Const.NONE, 0));
		assertEquals(square, Const.BOARD_MIDDLE_MIDDLE);

		square = mask.getSquareIndex(new Move(5, 6, Const.NONE, 0));
		assertEquals(square, Const.BOARD_BOTTOM_LEFT);
	}

	@Test
	public void testGettingAllowedMovesAfterAMove() {
		MoveList topLeftMoves = new MoveList();
		topLeftMoves.add(new Move(0, 0, Const.NONE, 0));
		topLeftMoves.add(new Move(0, 1, Const.NONE, 0));
		topLeftMoves.add(new Move(0, 2, Const.NONE, 0));
		topLeftMoves.add(new Move(1, 0, Const.NONE, 0));
		topLeftMoves.add(new Move(1, 1, Const.NONE, 0));
		topLeftMoves.add(new Move(1, 2, Const.NONE, 0));
		topLeftMoves.add(new Move(2, 0, Const.NONE, 0));
		topLeftMoves.add(new Move(2, 1, Const.NONE, 0));
		topLeftMoves.add(new Move(2, 2, Const.NONE, 0));

		Move m = new Move(0, 0, Const.NONE, 0);
		MoveList allowedMoves = mask.getAllowedMovesAfter(m);
		assertEquals(topLeftMoves.size(), allowedMoves.size());
		for (int i = 0; i < topLeftMoves.size(); i++) {
			assertEquals(allowedMoves.get(i), topLeftMoves.get(i));
		}
	}

	@Test
	public void testGettingAllowedMovesAfterAMove2() {
		MoveList bottomMiddleMoves = new MoveList();
		bottomMiddleMoves.add(new Move(6, 3, Const.NONE, 0));
		bottomMiddleMoves.add(new Move(6, 4, Const.NONE, 0));
		bottomMiddleMoves.add(new Move(6, 5, Const.NONE, 0));
		bottomMiddleMoves.add(new Move(7, 3, Const.NONE, 0));
		bottomMiddleMoves.add(new Move(7, 4, Const.NONE, 0));
		bottomMiddleMoves.add(new Move(7, 5, Const.NONE, 0));
		bottomMiddleMoves.add(new Move(8, 3, Const.NONE, 0));
		bottomMiddleMoves.add(new Move(8, 4, Const.NONE, 0));
		bottomMiddleMoves.add(new Move(8, 5, Const.NONE, 0));

		Move m = new Move(8, 7, Const.NONE, 0);
		MoveList allowedMoves = mask.getAllowedMovesAfter(m);
		assertEquals(bottomMiddleMoves.size(), allowedMoves.size());
		for (int i = 0; i < bottomMiddleMoves.size(); i++) {
			assertEquals(bottomMiddleMoves.get(i), allowedMoves.get(i));
		}
	}

	@Test
	public void testGetBoardIndex() {
		assertEquals(Const.BOARD_TOP_LEFT, mask.getBoardIndex(new Move(2, 1, 0, 0)));
	}
}
