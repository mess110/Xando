package ro.northpole.xando.models;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import ro.northpole.jxonp.models.Game;
import ro.northpole.jxonp.util.Const;

public class AllowedMask extends Rectangle {

	private float pX;
	private float pY;
	private float pWidth;
	private float pHeight;

	public AllowedMask(float pX, float pY, float pWidth, float pHeight,
			VertexBufferObjectManager vertexBufferObjectManager) {
		super(pX, pY, pWidth, pHeight, vertexBufferObjectManager);
		setAlpha(0.3f);
		setColor(0f, 1f, 0f);
		this.pX = pX;
		this.pY = pY;
		this.pWidth = pWidth;
		this.pHeight = pHeight;
	}

	public void setDefaultPositionAndSize() {
		setPosition(pX, pY);
		setSize(pWidth, pHeight);
	}

	public void setNoSize() {
		setSize(0, 0);
	}

	public void update(Game game) {
		if (game.isFinished()) {
			setNoSize();
		} else if (game.getMoveCount() == 0) {
			setDefaultPositionAndSize();
		} else {
			setSize(Tile.CARD_WIDTH * 3, Tile.CARD_HEIGHT * 3);

			// this takes orientation into account
			int i = game.getBoard().getAllowedBoard();
			// TODO simplify this..
			switch (i) {
			case Const.BOARD_TOP_LEFT:
			case Const.BOARD_TOP_MIDDLE:
			case Const.BOARD_TOP_RIGHT:
				setPosition(Tile.CARD_WIDTH * 0, Tile.CARD_HEIGHT * 3 * (i % 3));
				break;
			case Const.BOARD_MIDDLE_LEFT:
			case Const.BOARD_MIDDLE_MIDDLE:
			case Const.BOARD_MIDDLE_RIGHT:
				setPosition(Tile.CARD_WIDTH * 3, Tile.CARD_HEIGHT * 3 * (i % 3));
				break;
			case Const.BOARD_BOTTOM_LEFT:
			case Const.BOARD_BOTTOM_MIDDLE:
			case Const.BOARD_BOTTOM_RIGHT:
				setPosition(Tile.CARD_WIDTH * 6, Tile.CARD_HEIGHT * 3 * (i % 3));
				break;
			default:
				setNoSize();
				break;
			}
		}
	}

}
