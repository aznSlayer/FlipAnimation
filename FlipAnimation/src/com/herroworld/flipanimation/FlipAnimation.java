
package com.herroworld.flipanimation;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;

/**
 * This class extends the {@link Animation} class and provide extra APIs to
 * perform a horizontal and vertical view flip. It uses the {@link Camera} class
 * for transformations to provide a smooth 3D feel.
 */
public class FlipAnimation extends Animation {
    public static final String TAG = FlipAnimation.class.getSimpleName();
    public static boolean DEBUG = false;

    public static final long DURATION_MILLIS = 500;
    public static final int ROTATION_DEG = 90;
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;

    // Flag to determine if the top view is in fact on top
    private boolean mIsTopView;
    private float mFromDeg;
    private float mToDeg;
    private float mCenterX;
    private float mCenterY;

    // Horizontal flip by default
    private int mOrientation = HORIZONTAL;

    private final View mTopView;
    private final View mBottomView;
    private Camera mCamera;

    /**
     * Initialize the top and bottom view to be flipped.
     * 
     * @param topView The view on top.
     * @param bottomView The view on the bottom.
     */
    public FlipAnimation(View topView,
            View bottomView) {
        mTopView = topView;
        mBottomView = bottomView;
        mBottomView.setVisibility(View.GONE);

        // Animation time of 500 milliseconds by default
        setDuration(DURATION_MILLIS);
        setFillAfter(true);
    }

    /**
     * Setting the orientation of the flip.
     * 
     * @param orientation {@link FlipAnimation#HORIZONTAL} or
     *            {@link FlipAnimation#VERTICAL}
     * @return True if orientation set was successful, false otherwise.
     */
    public boolean setOrientation(int orientation) {
        if ((orientation != HORIZONTAL) || (orientation != VERTICAL)) {
            Log.e(TAG,
                    "Error setting orientation, value should only be 0 for horizontal and 1 for vertical!");
            return false;
        }

        mOrientation = orientation;

        return true;
    }

    /**
     * Flips the views.
     * 
     * @param isTopView True if the top view is on top.
     * @return True if flip succeeded, false otherwise.
     */
    public boolean flip(boolean isTopView) {
        // If previous animation isn't done, return immediately
        if ((mBottomView.getAnimation() != null && !mBottomView.getAnimation().hasEnded())
                || (mTopView.getAnimation() != null && !mTopView.getAnimation().hasEnded())) {
            if (DEBUG) {
                Log.d(TAG, "still animating");
            }

            return false;
        }

        mIsTopView = isTopView;

        // Setting to and from rotation degrees depending on which view is on
        // top
        if (mIsTopView) {
            mFromDeg = 0;
            mToDeg = ROTATION_DEG;
        } else {
            mFromDeg = 0;
            mToDeg = -ROTATION_DEG;
        }

        // Find the center of the view
        mCenterX = mTopView.getWidth() / 2.0f;
        mCenterY = mTopView.getHeight() / 2.0f;

        setInterpolator(new AccelerateInterpolator());

        setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Start a flop animation when the flip animation ends
                FlipAnimation flipAnimation = new FlipAnimation(mTopView, mBottomView);
                flipAnimation.setDuration(getDuration());
                flipAnimation.setOrientation(mOrientation);

                if (mIsTopView) {
                    mTopView.setVisibility(View.GONE);
                    mTopView.clearAnimation();
                    mBottomView.setVisibility(View.VISIBLE);
                    mBottomView.requestFocus();
                    mBottomView.bringToFront();

                    flipAnimation.flop(mIsTopView);
                } else {
                    mBottomView.setVisibility(View.GONE);
                    mBottomView.clearAnimation();
                    mTopView.setVisibility(View.VISIBLE);
                    mTopView.requestFocus();
                    mTopView.bringToFront();

                    flipAnimation.flop(mIsTopView);
                }
            }
        });

        if (mIsTopView) {
            if (DEBUG) {
                Log.i(TAG, "flip top view");
            }

            mTopView.startAnimation(this);
        } else {
            if (DEBUG) {
                Log.i(TAG, "flip bottom view");
            }

            mBottomView.startAnimation(this);
        }

        return true;
    }

    /**
     * Handles the animation of the opposite view after flip.
     * 
     * @param isTopView True if the top view is on top.
     */
    private void flop(boolean isTopView) {
        mIsTopView = isTopView;

        if (mIsTopView) {
            mFromDeg = -ROTATION_DEG;
            mToDeg = 0;
        } else {
            mFromDeg = ROTATION_DEG;
            mToDeg = 0;
        }

        // Find the center of the view
        mCenterX = mTopView.getWidth() / 2.0f;
        mCenterY = mTopView.getHeight() / 2.0f;

        setInterpolator(new DecelerateInterpolator());

        if (mIsTopView) {
            if (DEBUG) {
                Log.i(TAG, "flop bottom view");
            }

            mBottomView.startAnimation(this);
        } else {
            if (DEBUG) {
                Log.i(TAG, "flop top view");
            }

            mTopView.startAnimation(this);
        }
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        mCamera = new Camera();
    }

    /**
     * Applying the animation transformation using the {@link Camera} class.
     */
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        final Matrix matrix = t.getMatrix();
        final float degrees = mFromDeg + ((mToDeg - mFromDeg) * interpolatedTime);

        mCamera.save();

        // Flips horizontally or vertically depending on what the user set
        if (mOrientation == HORIZONTAL) {
            mCamera.rotateY(degrees);
        } else {
            mCamera.rotateX(degrees);
        }

        mCamera.getMatrix(matrix);
        mCamera.restore();

        matrix.preTranslate(-mCenterX, -mCenterY);
        matrix.postTranslate(mCenterX, mCenterY);
    }

}
