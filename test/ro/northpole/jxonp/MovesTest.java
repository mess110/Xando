package ro.northpole.jxonp;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ro.northpole.jxonp.models.Move;
import ro.northpole.jxonp.models.MoveList;
import ro.northpole.jxonp.util.Const;
import ro.northpole.jxonp.util.Util;

public class MovesTest {

	private MoveList moves;
	private Move first;
	private Move second;

	@Before
	public void setUp() throws Exception {
		moves = new MoveList();
		first = new Move(0, 0, Const.X, Util.getTime());
		second = new Move(0, 1, Const.O, Util.getTime());
		moves.add(first);
		moves.add(second);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddingMovesAtTheEndOfTheList() {
		assertEquals(moves.get(0), first);
		assertEquals(moves.get(1), second);
	}

	@Test
	public void testGetLastMove() {
		assertEquals(moves.getLastMove(), second);
	}

}
