package ro.northpole.jxonp;

import java.lang.reflect.Type;

import ro.northpole.jxonp.models.Board;
import ro.northpole.jxonp.models.Game;
import ro.northpole.jxonp.models.Move;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class GameSerializer implements JsonSerializer<Game> {
	@Override
	public JsonElement serialize(Game src, Type typeOfSrc,
			JsonSerializationContext context) {

		JsonObject obj = new JsonObject();
		
		// add meta data
		obj.addProperty(NP.ID, src.getId());
		obj.addProperty(NP.PLAYER1, src.getPlayer1());
		obj.addProperty(NP.PLAYER2, src.getPlayer2());
		
		// add the board
		Board board = src.getBoard();
		JsonObject jsonBoard = new JsonObject();
		
		// add the move list
		JsonArray moveList = new JsonArray();
		for (Move m : board.getMoves()) {
			JsonObject jsonMove = new JsonObject();
			jsonMove.addProperty(NP.X, m.x);
			jsonMove.addProperty(NP.Y, m.y);
			jsonMove.addProperty(NP.KIND, m.kind);
			jsonMove.addProperty(NP.TIME, m.time);
			moveList.add(jsonMove);
		}
		jsonBoard.add(NP.MOVE_LIST, moveList);
		
		JsonArray miniBoard = new JsonArray();
		for (Move m : board.getMiniBoard().getMoves()) {
			JsonObject jsonMove2 = new JsonObject();
			jsonMove2.addProperty(NP.X, m.x);
			jsonMove2.addProperty(NP.Y, m.y);
			jsonMove2.addProperty(NP.KIND, m.kind);
			jsonMove2.addProperty(NP.TIME, m.time);
			miniBoard.add(jsonMove2);
		}
		jsonBoard.add(NP.MINI_BOARD, miniBoard);
		
		obj.add(NP.BOARD, jsonBoard);

		return obj;
	}
}