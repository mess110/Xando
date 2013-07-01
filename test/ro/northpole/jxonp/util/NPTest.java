package ro.northpole.jxonp.util;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ro.northpole.jxonp.Const;
import ro.northpole.jxonp.GameDeserializer;
import ro.northpole.jxonp.GameSerializer;
import ro.northpole.jxonp.NP;
import ro.northpole.jxonp.exceptions.JXoNpException;
import ro.northpole.jxonp.models.Game;

public class NPTest {

	private Gson gson;

	@Before
	public void setUp() throws Exception {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Game.class,	new GameSerializer());
		gsonBuilder.registerTypeAdapter(Game.class, new GameDeserializer());
		gson = gsonBuilder.create();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testNP() throws IOException {
		NP np = new NP(true);
		Game game = np.getGame();
		assertFalse(game.isStarted());
		assertFalse(game.isFinished());
	}
	
	@Test
	public void testNPId() throws JXoNpException, IOException {
		String id = new NP(true).getGame().getId();

		NP np = new NP(id, true);
		Game game = np.getGame();
		
		String json = gson.toJson(game);
		System.out.println(json);
		
		assertFalse(game.isStarted());
		assertFalse(game.isFinished());
	}
	
	@Test
	public void testJoin() throws JXoNpException, IOException {
		String id = new NP(true).getGame().getId();
		
		NP np = new NP(id, true);
		np.join(Const.PLAYER1);
		
		String json = gson.toJson(np.getGame());
		System.out.println(json);
	}
	
	@Test
	public void testMove() throws JXoNpException, IOException, InterruptedException {
		String id = new NP(true).getGame().getId();
		
		NP np = new NP(id, true);
		np.join(Const.PLAYER1);
		np.join(Const.PLAYER2);
		
		np.move(Const.PLAYER1, 0, 0);
		Thread.sleep(1000);
		np.move(Const.PLAYER2, 1, 0);
		
		String json = gson.toJson(np.getGame());
		System.out.println(json);
	}

}
