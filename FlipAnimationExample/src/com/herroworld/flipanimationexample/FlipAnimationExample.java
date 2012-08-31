
package com.herroworld.flipanimationexample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.herroworld.flipanimation.FlipAnimation;

public class FlipAnimationExample extends Activity {
    public static final String TAG = FlipAnimationExample.class.getSimpleName();
    private boolean mIsTopView = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final View topView = findViewById(R.id.top_view);
        final View bottomView = findViewById(R.id.bottom_view);
        final Button topButton = (Button) findViewById(R.id.top_button);
        final Button bottomButton = (Button) findViewById(R.id.bottom_button);

        // Setting the top and bottom view for flip animation
        final FlipAnimation flipAnimation = new FlipAnimation(topView, bottomView);

        // Setting a horizontal flip; the other option is a vertical flip
        flipAnimation.setOrientation(FlipAnimation.HORIZONTAL);

        // Listening to button clicks to perform the flip
        topButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                final boolean flipSuccessful;

                if (mIsTopView) {
                    flipSuccessful = flipAnimation.flip(mIsTopView);
                } else {
                    flipSuccessful = flipAnimation.flip(mIsTopView);
                }

                // If flip was not successful, that means the animation wasn't
                // done
                if (flipSuccessful) {
                    mIsTopView = !mIsTopView;
                }
            }
        });

        bottomButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                final boolean flipSuccessful;

                if (mIsTopView) {
                    flipSuccessful = flipAnimation.flip(mIsTopView);
                } else {
                    flipSuccessful = flipAnimation.flip(mIsTopView);
                }

                if (flipSuccessful) {
                    mIsTopView = !mIsTopView;
                }
            }
        });
    }
}
