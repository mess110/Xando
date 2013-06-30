package ro.northpole.xando.models;


public enum Tile {

	TILE_BG_1(Kind.NONE, Value.SKIN_1),
	TILE_BG_2(Kind.NONE, Value.SKIN_2),
	TILE_BG_3(Kind.NONE, Value.SKIN_3),

	DEFAULT_X_1(Kind.X, Value.SKIN_1),
	DEFAULT_X_2(Kind.X, Value.SKIN_2),
	DEFAULT_X_3(Kind.X, Value.SKIN_3),

	DEFAULT_Y_1(Kind.O, Value.SKIN_1),
	DEFAULT_Y_2(Kind.O, Value.SKIN_2),
	DEFAULT_Y_3(Kind.O, Value.SKIN_3),

	DEFAULT_BUTTON_1(Kind.BUTTON, Value.SKIN_1),
	DEFAULT_BUTTON_2(Kind.BUTTON, Value.SKIN_2),
	DEFAULT_BUTTON_3(Kind.BUTTON, Value.SKIN_3);

	public static final int CARD_WIDTH = 64;
	public static final int CARD_HEIGHT = 64;

	public final Kind mColor;
	public final Value mValue;

	private Tile(final Kind pColor, final Value pValue) {
		this.mColor = pColor;
		this.mValue = pValue;
	}

	public int getTexturePositionX() {
		return this.mValue.ordinal() * CARD_WIDTH;
	}

	public int getTexturePositionY() {
		return this.mColor.ordinal() * CARD_HEIGHT;
	}
}