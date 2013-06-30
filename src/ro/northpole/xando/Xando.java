package ro.northpole.xando;

import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

import org.andengine.engine.camera.ZoomCamera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.primitive.Line;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.input.touch.controller.MultiTouch;
import org.andengine.input.touch.detector.PinchZoomDetector;
import org.andengine.input.touch.detector.SurfaceScrollDetector;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;

import ro.northpole.jxonp.NP;
import ro.northpole.jxonp.exceptions.JXoNpException;
import ro.northpole.jxonp.util.Const;
import ro.northpole.xando.models.Box;
import ro.northpole.xando.models.Tile;
import android.util.Log;
import android.widget.Toast;

public class Xando extends ZoomActivity {

	private static final int CAMERA_WIDTH = 720;
	private static final int CAMERA_HEIGHT = 480;

	private BitmapTextureAtlas mCardDeckTexture;
	private Box[][] tiles;
	private Box[][] miniBoard;

	private Scene mScene;

	private HashMap<Tile, ITextureRegion> mCardTotextureRegionMap;
	private NP np;
	private Rectangle allowedMask;

	@Override
	public EngineOptions onCreateEngineOptions() {
		mZoomCamera = new ZoomCamera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		final EngineOptions engineOptions = new EngineOptions(true,
				ScreenOrientation.LANDSCAPE_FIXED, new FillResolutionPolicy(),
				mZoomCamera);

		if (MultiTouch.isSupported(this)) {
			if (MultiTouch.isSupportedDistinct(this)) {
				// MultiTouch detected --> Both controls will work properly!"
			} else {
				// MultiTouch detected, but your device has problems
				// distinguishing between fingers.\n\nControls are placed at
				// different vertical locations.
			}
		} else {
			// Sorry your device does NOT support MultiTouch!\n\n(Falling back
			// to SingleTouch.)\n\nControls are placed at different vertical
			// locations."
		}

		return engineOptions;
	}

	@Override
	public void onCreateResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

		mCardDeckTexture = new BitmapTextureAtlas(getTextureManager(), 256,
				256, TextureOptions.BILINEAR);
		BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				mCardDeckTexture, this, "carddeck_tiled.png", 0, 0);
		mCardDeckTexture.load();

		mCardTotextureRegionMap = new HashMap<Tile, ITextureRegion>();

		for (final Tile card : Tile.values()) {
			final ITextureRegion cardTextureRegion = TextureRegionFactory
					.extractFromTexture(mCardDeckTexture,
							card.getTexturePositionX(),
							card.getTexturePositionY(), Tile.CARD_WIDTH,
							Tile.CARD_HEIGHT);
			mCardTotextureRegionMap.put(card, cardTextureRegion);
		}

		try {
			np = new NP();
			np.join("kiki");
			np.join("felix");
			Log.d("kiki", np.getGame().getId());
		} catch (JXoNpException e) {
			log(e);
		} catch (IOException e) {
			log(e);
		}
	}

	private void log(Exception e) {
		Toast.makeText(Xando.this, e.getLocalizedMessage(), Toast.LENGTH_LONG)
				.show();
	}

	@Override
	public Scene onCreateScene() {
		mScene = new Scene();
		mScene.setOnAreaTouchTraversalFrontToBack();

		initBoardBackground();

		tiles = new Box[9][9];
		miniBoard = new Box[3][3];
		redraw();

		mScene.setBackground(new Background(1.0f, 1.0f, 1.0f));

		mScrollDetector = new SurfaceScrollDetector(this);
		mPinchZoomDetector = new PinchZoomDetector(this);

		mZoomCamera.setZoomFactor(0.80650854f);
		mZoomCamera.setCenter(429.8135f, 286.5421f);

		mScene.setOnSceneTouchListener(this);
		mScene.setTouchAreaBindingOnActionDownEnabled(true);

		allowedMask = new Rectangle(0, 0, Tile.CARD_WIDTH * 9,
				Tile.CARD_HEIGHT * 9, this.getVertexBufferObjectManager());
		allowedMask.setAlpha(0.3f);
		allowedMask.setColor(0f, 1f, 0f);

		addMarker(0, 0, 1f, 0f, 0f); //red
		addMarker(64 * 8, 0, 0f, 1f, 0f); // green
		addMarker(0, 64 * 8, 0f, 0f, 1f); // blue

		mScene.attachChild(allowedMask);

		return mScene;
	}

	private void addMarker(int i, int j, float f, float g, float h) {
		Rectangle marker = new Rectangle(i, j, 5, 5,
				getVertexBufferObjectManager());
		marker.setColor(f, g, h);
		mScene.attachChild(marker);
	}

	private void initBoardBackground() {
		addLine(0, Tile.CARD_HEIGHT * 3, Tile.CARD_WIDTH * 9,
				Tile.CARD_HEIGHT * 3);
		addLine(0, Tile.CARD_HEIGHT * 6, Tile.CARD_WIDTH * 9,
				Tile.CARD_HEIGHT * 6);
		addLine(Tile.CARD_WIDTH * 3, 0, Tile.CARD_WIDTH * 3,
				Tile.CARD_HEIGHT * 9);
		addLine(Tile.CARD_WIDTH * 6, 0, Tile.CARD_WIDTH * 6,
				Tile.CARD_HEIGHT * 9);
	}

	private void addLine(int pX1, int pY1, int pX2, int pY2) {
		final float lineWidth = 5;
		final Line line = new Line(pX1, pY1, pX2, pY2, lineWidth,
				getVertexBufferObjectManager());
		line.setColor(0.0f, 0.0f, 0.0f);
		mScene.attachChild(line);
	}

	private void addTile(int x, int y, int kind) {
		ITextureRegion tR;

		switch (kind) {
		case Const.X:
			float r = new Random().nextFloat();
			if (r < 0.33f) {
				tR = mCardTotextureRegionMap.get(Tile.DEFAULT_X_1);
			} else if (0.33f < r && r < 0.66f) {
				tR = mCardTotextureRegionMap.get(Tile.DEFAULT_X_2);
			} else {
				tR = mCardTotextureRegionMap.get(Tile.DEFAULT_X_3);
			}
			break;
		case Const.O:
			float r2 = new Random().nextFloat();
			if (r2 < 0.33f) {
				tR = mCardTotextureRegionMap.get(Tile.DEFAULT_Y_1);
			} else if (0.33f < r2 && r2 < 0.66f) {
				tR = mCardTotextureRegionMap.get(Tile.DEFAULT_Y_2);
			} else {
				tR = mCardTotextureRegionMap.get(Tile.DEFAULT_Y_3);
			}
			break;
		default:
			tR = mCardTotextureRegionMap.get(Tile.TILE_BG_1);
			break;
		}

		final Box tile = new Box(x, y, kind, tR,
				getVertexBufferObjectManager(), this);
		tiles[x][y] = tile;

		mScene.attachChild(tile);
		mScene.registerTouchArea(tile);
	}

	public void refresh(int x, int y) {
		try {
			String name = np.getGame().getMoveCount() % 2 == 0 ? "kiki"
					: "felix";
			np.move(name, x, y);
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					redraw();
				}
			});
		} catch (JXoNpException e) {
			// log(e);
		} catch (IOException e) {
			// log(e);
		}
	}

	private void redraw() {
		int[][] boardTiles = np.getBoardTiles();
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				if (tiles[x][y] == null
						|| tiles[x][y].getKind() != boardTiles[x][y]) {
					addTile(x, y, boardTiles[x][y]);
					if (allowedMask != null) {
						updateMask();
					}
				}
			}
		}

		// TODO WTF IS THIS SHIT
		int[][] miniBoardTiles = np.getMiniBoardTiles();
		Log.d("kiki", np.getGame().getBoard().getMoves().getLastMove()
				.toString());
		Log.d("kiki", np.getGame().getBoard().getMiniBoard().toString());
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				if (miniBoardTiles[x][y] != Const.NONE) {
					if (miniBoard[x][y] == null) {
						ITextureRegion tR;
						if (miniBoardTiles[x][y] == Const.X) {
							tR = mCardTotextureRegionMap.get(Tile.DEFAULT_X_1);
						} else {
							tR = mCardTotextureRegionMap.get(Tile.DEFAULT_Y_1);
						}
						miniBoard[x][y] = new Box(0, 0, miniBoardTiles[x][y],
								tR, getVertexBufferObjectManager(), null);
						miniBoard[x][y].setScale(3f);
						// mScene.attachChild(miniBoard[x][y]);
					}
				}
			}
		}
	}

	private void updateMask() {
		if (np.getGame().isFinished()) {
			allowedMask.setSize(0, 0);
		} else {
			allowedMask.setSize(Tile.CARD_WIDTH * 3, Tile.CARD_HEIGHT * 3);

			// this takes orientation into account
			int i = np.getGame().getBoard().getAllowedBoard();
			// TODO simplify this..
			switch (i) {
			case Const.BOARD_TOP_LEFT:
			case Const.BOARD_TOP_MIDDLE:
			case Const.BOARD_TOP_RIGHT:
				allowedMask.setPosition(Tile.CARD_WIDTH * 0, Tile.CARD_HEIGHT
						* 3 * (i % 3));
				break;
			case Const.BOARD_MIDDLE_LEFT:
			case Const.BOARD_MIDDLE_MIDDLE:
			case Const.BOARD_MIDDLE_RIGHT:
				allowedMask.setPosition(Tile.CARD_WIDTH * 3, Tile.CARD_HEIGHT
						* 3 * (i % 3));
				break;
			case Const.BOARD_BOTTOM_LEFT:
			case Const.BOARD_BOTTOM_MIDDLE:
			case Const.BOARD_BOTTOM_RIGHT:
				allowedMask.setPosition(Tile.CARD_WIDTH * 6, Tile.CARD_HEIGHT
						* 3 * (i % 3));
				break;
			default:
				allowedMask.setSize(0, 0);
				break;
			}
		}
	}
}