package ro.northpole.jxonp;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ro.northpole.jnorthpole.util.Util;
import ro.northpole.jxonp.exceptions.JXoNpException;
import ro.northpole.jxonp.models.Game;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SerializationTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Game.class,	new GameSerializer());
		gsonBuilder.registerTypeAdapter(Game.class, new GameDeserializer());
		Gson gson = gsonBuilder.create();
		
		Game game = new Game();
		game.join(NP.PLAYER1);
		game.join(NP.PLAYER2);
		try {
			game.move(NP.PLAYER1, 0, 0, Util.getTime());
			game.move(NP.PLAYER2, 1, 0, Util.getTime());
		} catch (JXoNpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String json = gson.toJson(game);
		System.out.println(json);
		
		game = gson.fromJson(json, Game.class);
		assertEquals(NP.PLAYER2, game.getPlayer2());
		assertEquals(2, game.getMoveCount());
	}
}
