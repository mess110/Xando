package ro.northpole.jxonp.util;

import java.io.IOException;

import ro.northpole.jnorthpole.Storage;
import ro.northpole.jnorthpole.util.Util;
import ro.northpole.jxonp.GameDeserializer;
import ro.northpole.jxonp.GameSerializer;
import ro.northpole.jxonp.NP;
import ro.northpole.jxonp.exceptions.JXoNpException;
import ro.northpole.jxonp.exceptions.NotXoGame;
import ro.northpole.jxonp.models.Game;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class NPClient {

	protected Gson gson;
	protected JsonParser parser;

	public NPClient() {
		init();
	}

	protected JsonObject newGame() throws IOException {
		Game tmpGame = new Game();
		String jsonString = gson.toJson(tmpGame);
		JsonObject json = parser.parse(jsonString).getAsJsonObject();

		addCredentials(json);
		json.addProperty(NP.NAMESPACE, NP.NAMESPACE_VALUE);

		Storage storage = new Storage(json);
		return storage.create().getAsJsonObject();
	}

	protected JsonObject getGame(String id) throws JXoNpException, IOException {
		JsonObject json = new JsonObject();

		addCredentials(json);
		json.addProperty(NP.ID, id);

		Storage storage = new Storage(json);
		json = storage.find().getAsJsonArray().get(0).getAsJsonObject();

		ensureGameObject(json);

		return json;
	}

	protected JsonObject updateGame(Game game) throws JXoNpException,
			IOException {
		String jsonString = gson.toJson(game);
		JsonObject json = parser.parse(jsonString).getAsJsonObject();

		addCredentials(json);
		json.addProperty(NP.NAMESPACE, NP.NAMESPACE_VALUE);

		Storage storage = new Storage(json);
		json = storage.update().getAsJsonObject();

		ensureGameObject(json);

		return json;
	}

	private void ensureGameObject(JsonObject json) throws NotXoGame {
		if (json.get(NP.NAMESPACE) == null) {
			throw new NotXoGame();
		}
		if (!json.get(NP.NAMESPACE).getAsString().equals(NP.NAMESPACE_VALUE)) {
			throw new NotXoGame();
		}
	}

	private void init() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Game.class, new GameSerializer());
		gsonBuilder.registerTypeAdapter(Game.class, new GameDeserializer());
		gson = gsonBuilder.create();
		parser = new JsonParser();
	}

	protected void addCredentials(JsonObject json) {
		json.addProperty(Util.API_KEY, Util.GUEST_API_KEY);
		json.addProperty(Util.SECRET, Util.GUEST_SECRET);
	}
}
