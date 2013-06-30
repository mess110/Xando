package ro.northpole.xando.models;

import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import ro.northpole.xando.Xando;

public class Box extends Sprite {
	private int x;
	private int y;
	private int kind;
	private Xando xando;

	public Box(int pX, int pY, int kind, ITextureRegion iTextureRegion,
			VertexBufferObjectManager vertexBufferObjectManager, Xando xando) {
		super(pX * Tile.CARD_WIDTH + 1, pY * Tile.CARD_HEIGHT, Tile.CARD_WIDTH,
				Tile.CARD_HEIGHT, iTextureRegion, vertexBufferObjectManager);
		this.x = pX;
		this.y = pY;
		this.kind = kind;
		this.xando = xando;
	}

	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
			final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		if (xando != null) {
			switch (pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:
				setScale(1.25f);
				xando.refresh(x, y);
				break;
			case TouchEvent.ACTION_UP:
				setScale(1.0f);
				break;
			}
			return true;
		}
		return false;
	}

	public int getKind() {
		return kind;
	}
}
