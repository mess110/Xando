package ro.northpole.jxonp;

import java.lang.reflect.Type;

import ro.northpole.jxonp.models.Game;
import ro.northpole.jxonp.models.Move;
import ro.northpole.jxonp.models.MoveList;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class GameDeserializer implements JsonDeserializer<Game> {
	@Override
	public Game deserialize(JsonElement jsonElement, Type typeOfT,
			JsonDeserializationContext context) {
		Game game = new Game();

		JsonObject json = jsonElement.getAsJsonObject();

		// add meta data
		if (json.get(NP.ID) != null) {
			game.setId(json.get(NP.ID).getAsString());
		}
		if (json.get(NP.PLAYER1) != null) {
			game.setPlayer1(json.get(NP.PLAYER1).getAsString());
		}
		if (json.get(NP.PLAYER2) != null) {
			game.setPlayer2(json.get(NP.PLAYER2).getAsString());
		}

		if (json.get(NP.BOARD) != null) {
			JsonObject jsonBoard = json.get(NP.BOARD).getAsJsonObject();

			// add move list
			MoveList moveList = getMoveList(jsonBoard, NP.MOVE_LIST);
			game.getBoard().setMoveList(moveList);

			// add miniboard list
			MoveList miniBoardList = getMoveList(jsonBoard, NP.MINI_BOARD);
			game.getBoard().getMiniBoard().setMoveList(miniBoardList);
		}

		return game;
	}

	private MoveList getMoveList(JsonObject jsonBoard, String moveListName) {
		JsonArray jsonMoveList = jsonBoard.get(moveListName).getAsJsonArray();
		MoveList moveList = new MoveList();
		for (int i = 0; i < jsonMoveList.size(); i++) {
			JsonObject move = jsonMoveList.get(i).getAsJsonObject();
			Move m = toMove(move);
			moveList.add(m);
		}
		return moveList;
	}

	private Move toMove(JsonObject move) {
		int x = move.get(NP.X).getAsInt();
		int y = move.get(NP.Y).getAsInt();
		int kind = move.get(NP.KIND).getAsInt();
		long time = move.get(NP.TIME).getAsLong();
		Move m = new Move(x, y, kind, time);
		return m;
	}
}