package ro.northpole.xando;

import java.io.IOException;
import java.util.HashMap;

import org.andengine.engine.camera.ZoomCamera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.primitive.Line;
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
import ro.northpole.xando.models.AllowedMask;
import ro.northpole.xando.models.Box;
import ro.northpole.xando.models.Tile;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class Xando extends ZoomActivity {

	public static final boolean DEBUG = true;

	private static final int CAMERA_WIDTH = 720;
	private static final int CAMERA_HEIGHT = 480;

	private BitmapTextureAtlas mCardDeckTexture;
	private Box[][] tiles;
	private Box[][] miniBoardX;
	private Box[][] miniBoardO;

	private Scene mScene;

	private HashMap<Tile, ITextureRegion> mCardTotextureRegionMap;
	private NP np;
	private AllowedMask allowedMask;

	private String player1 = "kiki";
	private String player2 = "felix";

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

		newOfflineGame();
	}

	private void newOfflineGame() {
		try {
			np = new NP(false);
			np.join(player1);
			np.join(player2);
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

	private Box bigTile(int i, int j, int kind, Tile tile) {
		Box b = new Box(i, j, Const.O, mCardTotextureRegionMap.get(tile),
				getVertexBufferObjectManager(), null);
		b.setPosition(i * Tile.CARD_WIDTH * 3 + 64, j * Tile.CARD_HEIGHT * 3
				+ 64);
		b.setAlpha(0.8f);
		b.setScale(3f);
		b.setVisible(false);
		return b;
	}

	@Override
	public Scene onCreateScene() {
		mScene = new Scene();
		mScene.setOnAreaTouchTraversalFrontToBack();

		initBoardBackground();

		tiles = new Box[9][9];
		miniBoardX = new Box[3][3];
		miniBoardO = new Box[3][3];

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				Box bX = bigTile(i, j, Const.X, Tile.DEFAULT_X_3);
				mScene.attachChild(bX);
				miniBoardX[i][j] = bX;

				Box bY = bigTile(i, j, Const.O, Tile.DEFAULT_Y_3);
				mScene.attachChild(bY);
				miniBoardO[i][j] = bY;
			}
		}

		redraw();

		mScene.setBackground(new Background(1.0f, 1.0f, 1.0f));

		mScrollDetector = new SurfaceScrollDetector(this);
		mPinchZoomDetector = new PinchZoomDetector(this);

		mZoomCamera.setZoomFactor(0.80650854f);
		mZoomCamera.setCenter(429.8135f, 286.5421f);

		mScene.setOnSceneTouchListener(this);
		mScene.setTouchAreaBindingOnActionDownEnabled(true);

		allowedMask = new AllowedMask(0, 0, Tile.CARD_WIDTH * 9,
				Tile.CARD_HEIGHT * 9, this.getVertexBufferObjectManager());

		mScene.attachChild(allowedMask);

		return mScene;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.new_game:
			newOfflineGame();
			runOnUpdateThread(new Runnable() {

				@Override
				public void run() {
					resetBoard();
					allowedMask.setDefaultPositionAndSize();
				}
			});
			return true;
		case R.id.exit:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void resetBoard() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (tiles[i][j].getKind() != Const.NONE) {
					mScene.detachChild(tiles[i][j]);
					addTile(i, j, Const.NONE);
				}
			}
		}
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				miniBoardX[i][j].setVisible(false);
				miniBoardO[i][j].setVisible(false);
			}
		}
	}

	private void initBoardBackground() {
		int ch = Tile.CARD_HEIGHT;
		int cw = Tile.CARD_WIDTH;
		addLine(0, ch * 3, cw * 9, ch * 3);
		addLine(0, ch * 6, cw * 9, ch * 6);
		addLine(cw * 3, 0, cw * 3, ch * 9);
		addLine(cw * 6, 0, cw * 6, ch * 9);
	}

	private void addLine(int pX1, int pY1, int pX2, int pY2) {
		final float lineWidth = 5;
		final Line line = new Line(pX1, pY1, pX2, pY2, lineWidth,
				getVertexBufferObjectManager());
		line.setColor(0.0f, 0.0f, 0.0f);
		mScene.attachChild(line);
	}

	private void addTile(int x, int y, int kind) {
		ITextureRegion tR = mCardTotextureRegionMap.get(Tile.random(kind));
		final Box tile = new Box(x, y, kind, tR,
				getVertexBufferObjectManager(), this);
		tiles[x][y] = tile;

		mScene.attachChild(tile);
		mScene.registerTouchArea(tile);
	}

	public void refresh(int x, int y) {
		try {
			String n = np.getGame().getMoveCount() % 2 == 0 ? player1 : player2;
			np.move(n, x, y);
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
						allowedMask.update(np.getGame());
					}
				}
			}
		}

		int[][] miniBoardTiles = np.getMiniBoardTiles();
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				if (miniBoardTiles[x][y] == Const.X) {
					miniBoardX[y][x].setVisible(true);
				} else if (miniBoardTiles[x][y] == Const.O) {
					miniBoardO[y][x].setVisible(true);
				}
			}
		}
	}
}