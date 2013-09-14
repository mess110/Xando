package ro.northpole.xando;

import org.andengine.engine.camera.ZoomCamera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;
import org.andengine.input.touch.detector.PinchZoomDetector;
import org.andengine.input.touch.detector.PinchZoomDetector.IPinchZoomDetectorListener;
import org.andengine.input.touch.detector.ScrollDetector;
import org.andengine.input.touch.detector.ScrollDetector.IScrollDetectorListener;
import org.andengine.input.touch.detector.SurfaceScrollDetector;
import org.andengine.ui.activity.SimpleBaseGameActivity;

public class ZoomActivity extends SimpleBaseGameActivity implements
		IOnSceneTouchListener, IScrollDetectorListener,
		IPinchZoomDetectorListener {

	protected SurfaceScrollDetector mScrollDetector;
	protected PinchZoomDetector mPinchZoomDetector;
	protected float mPinchZoomStartedCameraZoomFactor;

	protected ZoomCamera mZoomCamera;

	@Override
	public EngineOptions onCreateEngineOptions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void onCreateResources() {
		// TODO Auto-generated method stub

	}

	@Override
	protected Scene onCreateScene() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onScrollStarted(final ScrollDetector pScollDetector,
			final int pPointerID, final float pDistanceX, final float pDistanceY) {
	}

	@Override
	public void onScroll(final ScrollDetector pScollDetector,
			final int pPointerID, final float pDistanceX, final float pDistanceY) {
	}

	@Override
	public void onScrollFinished(final ScrollDetector pScollDetector,
			final int pPointerID, final float pDistanceX, final float pDistanceY) {
	}

	@Override
	public void onPinchZoomStarted(final PinchZoomDetector pPinchZoomDetector,
			final TouchEvent pTouchEvent) {
	}

	@Override
	public void onPinchZoom(final PinchZoomDetector pPinchZoomDetector,
			final TouchEvent pTouchEvent, final float pZoomFactor) {
	}

	@Override
	public void onPinchZoomFinished(final PinchZoomDetector pPinchZoomDetector,
			final TouchEvent pTouchEvent, final float pZoomFactor) {
	}

	@Override
	public boolean onSceneTouchEvent(final Scene pScene,
			final TouchEvent pSceneTouchEvent) {
		mPinchZoomDetector.onTouchEvent(pSceneTouchEvent);

		if (mPinchZoomDetector.isZooming()) {
			mScrollDetector.setEnabled(false);
		} else {
			if (pSceneTouchEvent.isActionDown()) {
				mScrollDetector.setEnabled(true);
			}
			mScrollDetector.onTouchEvent(pSceneTouchEvent);
		}

		return true;
	}
}
